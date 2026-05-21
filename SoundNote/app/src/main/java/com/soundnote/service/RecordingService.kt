package com.soundnote.service

import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.media.MediaRecorder
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.os.PowerManager
import androidx.core.app.NotificationCompat
import com.soundnote.R
import com.soundnote.SoundNoteApplication
import com.soundnote.domain.model.Recording
import com.soundnote.domain.model.RecordingFormat
import com.soundnote.domain.model.RecordingQuality
import com.soundnote.domain.model.RecordingState
import com.soundnote.domain.model.RecordingState.Recording as RecordingData
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class RecordingService : Service() {

    private val binder = RecordingBinder()
    private var mediaRecorder: MediaRecorder? = null
    private var wakeLock: PowerManager.WakeLock? = null
    private var recordingJob: Job? = null
    private val serviceScope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    private var currentFilePath: String? = null
    private var currentFormat: RecordingFormat = RecordingFormat.MP3
    private var currentQuality: RecordingQuality = RecordingQuality.STANDARD
    private var startTime: Long = 0L
    private var pausedDuration: Long = 0L
    private var pauseStartTime: Long = 0L

    private val _recordingState = MutableStateFlow<RecordingState>(RecordingState.Idle)
    val recordingState: StateFlow<RecordingState> = _recordingState.asStateFlow()

    private val _amplitude = MutableStateFlow(0)
    val amplitude: StateFlow<Int> = _amplitude.asStateFlow()

    private val amplitudes = mutableListOf<Float>()

    inner class RecordingBinder : Binder() {
        fun getService(): RecordingService = this@RecordingService
    }

    override fun onBind(intent: Intent?): IBinder = binder

    override fun onCreate() {
        super.onCreate()
        acquireWakeLock()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            ACTION_START -> startRecording(
                format = RecordingFormat.fromExtension(intent.getStringExtra(EXTRA_FORMAT) ?: "mp3"),
                quality = RecordingQuality.fromOrdinal(intent.getIntExtra(EXTRA_QUALITY, 1))
            )
            ACTION_PAUSE -> pauseRecording()
            ACTION_RESUME -> resumeRecording()
            ACTION_STOP -> stopRecording()
        }
        return START_STICKY
    }

    private fun acquireWakeLock() {
        val powerManager = getSystemService(POWER_SERVICE) as PowerManager
        wakeLock = powerManager.newWakeLock(
            PowerManager.PARTIAL_WAKE_LOCK,
            "SoundNote::RecordingWakeLock"
        ).apply { acquire(10 * 60 * 60 * 1000L) } // 10 hours max
    }

    private fun startForegroundWithNotification() {
        val notification = createNotification("正在录音...", "点击管理")
        startForeground(NOTIFICATION_ID, notification)
    }

    fun startRecording(format: RecordingFormat, quality: RecordingQuality) {
        if (_recordingState.value is RecordingData) return

        try {
            currentFormat = format
            currentQuality = quality

            val file = createRecordingFile(format)
            currentFilePath = file.absolutePath

            mediaRecorder = createMediaRecorder().apply {
                setAudioSource(MediaRecorder.AudioSource.MIC)
                setOutputFormat(getOutputFormat(format))
                setAudioEncoder(getAudioEncoder(format))
                setAudioSamplingRate(quality.sampleRate)
                setAudioEncodingBitRate(quality.bitRate)
                setAudioChannels(quality.channels)
                setOutputFile(currentFilePath)
                prepare()
                start()
            }

            startTime = System.currentTimeMillis()
            pausedDuration = 0L
            amplitudes.clear()

            _recordingState.value = RecordingData()

            startForegroundWithNotification()
            startAmplitudeUpdates()

        } catch (e: IOException) {
            _recordingState.value = RecordingState.Error("无法启动录音: ${e.message}")
        } catch (e: IllegalStateException) {
            _recordingState.value = RecordingState.Error("录音状态异常: ${e.message}")
        }
    }

    fun pauseRecording() {
        if (_recordingState.value !is RecordingData) return
        val state = _recordingState.value as RecordingData
        pauseStartTime = System.currentTimeMillis()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mediaRecorder?.pause()
        }

        _recordingState.value = RecordingState.Paused(state.durationMs)
        recordingJob?.cancel()
    }

    fun resumeRecording() {
        if (_recordingState.value !is RecordingState.Paused) return

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mediaRecorder?.resume()
        }

        pausedDuration += System.currentTimeMillis() - pauseStartTime
        _recordingState.value = RecordingData()
        startAmplitudeUpdates()
    }

    fun stopRecording(): Recording? {
        val filePath = currentFilePath ?: return null
        val duration = calculateDuration()
        val file = File(filePath)

        try {
            mediaRecorder?.apply {
                stop()
                release()
            }
        } catch (e: Exception) {
            // Ignore stop errors
        }

        mediaRecorder = null
        recordingJob?.cancel()

        if (!file.exists() || file.length() == 0L) {
            _recordingState.value = RecordingState.Error("录音文件无效")
            return null
        }

        _recordingState.value = RecordingState.Saving

        val recording = Recording(
            id = UUID.randomUUID().toString(),
            fileName = file.nameWithoutExtension,
            filePath = filePath,
            format = currentFormat.extension,
            durationMs = duration,
            fileSizeBytes = file.length(),
            sampleRate = currentQuality.sampleRate,
            bitRate = currentQuality.bitRate,
            channels = currentQuality.channels,
            createdAt = startTime,
            updatedAt = System.currentTimeMillis()
        )

        _recordingState.value = RecordingState.Saved
        // Reset to idle after a short delay
        serviceScope.launch {
            delay(500)
            _recordingState.value = RecordingState.Idle
        }

        return recording
    }

    private fun calculateDuration(): Long {
        return when (val state = _recordingState.value) {
            is RecordingData -> System.currentTimeMillis() - startTime - pausedDuration
            is RecordingState.Paused -> state.durationMs
            else -> System.currentTimeMillis() - startTime - pausedDuration
        }
    }

    private fun createRecordingFile(format: RecordingFormat): File {
        val dateFormat = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault())
        val fileName = "Recording_${dateFormat.format(Date())}.${format.extension}"
        val recordingsDir = File(filesDir, "recordings").apply { mkdirs() }
        return File(recordingsDir, fileName)
    }

    @Suppress("DEPRECATION")
    private fun createMediaRecorder(): MediaRecorder {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            MediaRecorder(this)
        } else {
            MediaRecorder()
        }
    }

    private fun getOutputFormat(format: RecordingFormat): Int {
        return when (format) {
            RecordingFormat.MP3 -> MediaRecorder.OutputFormat.MPEG_4
            RecordingFormat.AAC -> MediaRecorder.OutputFormat.AAC_ADTS
            RecordingFormat.M4A -> MediaRecorder.OutputFormat.MPEG_4
            RecordingFormat.WAV -> MediaRecorder.OutputFormat.DEFAULT
        }
    }

    private fun getAudioEncoder(format: RecordingFormat): Int {
        return when (format) {
            RecordingFormat.MP3 -> MediaRecorder.AudioEncoder.AAC
            RecordingFormat.AAC -> MediaRecorder.AudioEncoder.AAC
            RecordingFormat.M4A -> MediaRecorder.AudioEncoder.AAC
            RecordingFormat.WAV -> MediaRecorder.AudioEncoder.DEFAULT
        }
    }

    private fun startAmplitudeUpdates() {
        recordingJob = serviceScope.launch {
            while (isActive) {
                val amp = try {
                    mediaRecorder?.maxAmplitude ?: 0
                } catch (_: Exception) { 0 }
                _amplitude.value = amp
                // Normalize amplitude to 0-1 range (max amplitude ~32767)
                val normalized = (amp / 32767f).coerceIn(0f, 1f)
                amplitudes.add(normalized)
                if (amplitudes.size > 200) amplitudes.removeAt(0)
                delay(100)
            }
        }
    }

    private fun createNotification(title: String, text: String): Notification {
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            packageManager.getLaunchIntentForPackage(packageName),
            PendingIntent.FLAG_IMMUTABLE
        )
        return NotificationCompat.Builder(this, SoundNoteApplication.RECORDING_CHANNEL_ID)
            .setContentTitle(title)
            .setContentText(text)
            .setSmallIcon(android.R.drawable.ic_btn_speak_now)
            .setContentIntent(pendingIntent)
            .setOngoing(true)
            .build()
    }

    override fun onDestroy() {
        serviceScope.cancel()
        mediaRecorder?.release()
        mediaRecorder = null
        wakeLock?.release()
        super.onDestroy()
    }

    companion object {
        const val NOTIFICATION_ID = 1
        const val ACTION_START = "com.soundnote.action.START"
        const val ACTION_PAUSE = "com.soundnote.action.PAUSE"
        const val ACTION_RESUME = "com.soundnote.action.RESUME"
        const val ACTION_STOP = "com.soundnote.action.STOP"
        const val EXTRA_FORMAT = "extra_format"
        const val EXTRA_QUALITY = "extra_quality"
    }
}
