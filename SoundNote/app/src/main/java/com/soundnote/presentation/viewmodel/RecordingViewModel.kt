package com.soundnote.presentation.viewmodel

import android.app.Application
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Build
import android.os.IBinder
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.soundnote.data.preferences.PreferencesManager
import com.soundnote.domain.model.*
import com.soundnote.domain.model.RecordingState.Recording as RecordingData
import com.soundnote.domain.usecase.*
import com.soundnote.service.RecordingService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class RecordingViewModel @Inject constructor(
    private val application: Application,
    private val saveRecordingUseCase: SaveRecordingUseCase,
    private val getAllRecordingsUseCase: GetAllRecordingsUseCase,
    private val preferencesManager: PreferencesManager
) : AndroidViewModel(application) {

    private var recordingService: RecordingService? = null
    private var bound = false

    private val _recordingState = MutableStateFlow<RecordingState>(RecordingState.Idle)
    val recordingState: StateFlow<RecordingState> = _recordingState.asStateFlow()

    private val _amplitude = MutableStateFlow(0)
    val amplitude: StateFlow<Int> = _amplitude.asStateFlow()

    private val _amplitudes = MutableStateFlow<List<Float>>(emptyList())
    val amplitudes: StateFlow<List<Float>> = _amplitudes.asStateFlow()

    private val _elapsedTime = MutableStateFlow(0L)
    val elapsedTime: StateFlow<Long> = _elapsedTime.asStateFlow()

    private val _currentFormat = MutableStateFlow(RecordingFormat.MP3)
    val currentFormat: StateFlow<RecordingFormat> = _currentFormat.asStateFlow()

    private val _currentQuality = MutableStateFlow(RecordingQuality.STANDARD)
    val currentQuality: StateFlow<RecordingQuality> = _currentQuality.asStateFlow()

    private val _isProfessionalMode = MutableStateFlow(false)
    val isProfessionalMode: StateFlow<Boolean> = _isProfessionalMode.asStateFlow()

    private val _saveResult = MutableSharedFlow<Boolean>()
    val saveResult: SharedFlow<Boolean> = _saveResult.asSharedFlow()

    private val _recentRecordings = MutableStateFlow<List<Recording>>(emptyList())
    val recentRecordings: StateFlow<List<Recording>> = _recentRecordings.asStateFlow()

    private var timerJob: Job? = null

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = service as RecordingService.RecordingBinder
            recordingService = binder.getService()
            bound = true
            observeServiceState()
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            recordingService = null
            bound = false
        }
    }

    init {
        bindService()
        loadRecentRecordings()
        loadPreferences()
    }

    private fun loadPreferences() {
        viewModelScope.launch {
            preferencesManager.defaultFormat.collect { format ->
                _currentFormat.value = format
            }
        }
        viewModelScope.launch {
            preferencesManager.defaultQuality.collect { quality ->
                _currentQuality.value = quality
            }
        }
    }

    private fun bindService() {
        val intent = Intent(application, RecordingService::class.java)
        application.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
    }

    private fun observeServiceState() {
        viewModelScope.launch {
            recordingService?.recordingState?.collect { state ->
                _recordingState.value = state
                when (state) {
                    is RecordingData -> startTimer()
                    is RecordingState.Paused -> timerJob?.cancel()
                    is RecordingState.Idle -> {
                        timerJob?.cancel()
                        _elapsedTime.value = 0L
                    }
                    else -> {}
                }
            }
        }
        viewModelScope.launch {
            recordingService?.amplitude?.collect { amp ->
                _amplitude.value = amp
                val normalized = (amp / 32767f).coerceIn(0f, 1f)
                val current = _amplitudes.value.toMutableList()
                current.add(normalized)
                if (current.size > 100) current.removeAt(0)
                _amplitudes.value = current
            }
        }
    }

    private fun startTimer() {
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            while (isActive) {
                _elapsedTime.value += 100
                delay(100)
            }
        }
    }

    private fun loadRecentRecordings() {
        viewModelScope.launch {
            getAllRecordingsUseCase().collect { recordings ->
                _recentRecordings.value = recordings.take(5)
            }
        }
    }

    fun startRecording() {
        val intent = Intent(application, RecordingService::class.java).apply {
            action = RecordingService.ACTION_START
            putExtra(RecordingService.EXTRA_FORMAT, _currentFormat.value.extension)
            putExtra(RecordingService.EXTRA_QUALITY, _currentQuality.value.ordinal)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            application.startForegroundService(intent)
        } else {
            application.startService(intent)
        }
    }

    fun pauseRecording() {
        recordingService?.pauseRecording()
    }

    fun resumeRecording() {
        recordingService?.resumeRecording()
    }

    fun stopRecording() {
        viewModelScope.launch {
            val recording = recordingService?.stopRecording()
            if (recording != null) {
                saveRecordingUseCase(recording)
                _saveResult.emit(true)
            }
        }
    }

    fun setFormat(format: RecordingFormat) {
        _currentFormat.value = format
    }

    fun setQuality(quality: RecordingQuality) {
        _currentQuality.value = quality
    }

    fun toggleProfessionalMode() {
        _isProfessionalMode.value = !_isProfessionalMode.value
    }

    override fun onCleared() {
        super.onCleared()
        if (bound) {
            application.unbindService(serviceConnection)
            bound = false
        }
    }
}
