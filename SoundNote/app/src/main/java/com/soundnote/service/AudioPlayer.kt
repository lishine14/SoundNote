package com.soundnote.service

import android.content.Context
import android.media.MediaPlayer
import android.media.PlaybackParams
import android.os.Build
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AudioPlayer @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private var mediaPlayer: MediaPlayer? = null
    private var playbackJob: Job? = null
    private val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    private val _playbackState = MutableStateFlow<PlaybackState>(PlaybackState.Idle)
    val playbackState: StateFlow<PlaybackState> = _playbackState.asStateFlow()

    private val _currentPosition = MutableStateFlow(0L)
    val currentPosition: StateFlow<Long> = _currentPosition.asStateFlow()

    private val _duration = MutableStateFlow(0L)
    val duration: StateFlow<Long> = _duration.asStateFlow()

    private val _playbackSpeed = MutableStateFlow(1f)
    val playbackSpeed: StateFlow<Float> = _playbackSpeed.asStateFlow()

    private val _speedSupported = MutableStateFlow(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
    val speedSupported: StateFlow<Boolean> = _speedSupported.asStateFlow()

    private var currentFilePath: String? = null
    private var pendingSpeed: Float? = null

    fun play(filePath: String) {
        if (currentFilePath == filePath && _playbackState.value == PlaybackState.Paused) {
            resume()
            return
        }

        stop()
        currentFilePath = filePath

        try {
            mediaPlayer = MediaPlayer().apply {
                setDataSource(filePath)
                prepare()
                // 在 start() 前设置倍率（如果支持）
                pendingSpeed?.let { speed ->
                    applySpeed(speed)
                    pendingSpeed = null
                }
                start()
                setOnCompletionListener {
                    _playbackState.value = PlaybackState.Idle
                    _currentPosition.value = 0L
                }
            }
            _duration.value = mediaPlayer?.duration?.toLong() ?: 0L
            _playbackState.value = PlaybackState.Playing
            startPositionUpdates()
        } catch (e: Exception) {
            _playbackState.value = PlaybackState.Error(e.message ?: "播放失败")
        }
    }

    fun pause() {
        mediaPlayer?.pause()
        playbackJob?.cancel()
        _playbackState.value = PlaybackState.Paused
    }

    fun resume() {
        mediaPlayer?.start()
        _playbackState.value = PlaybackState.Playing
        startPositionUpdates()
    }

    fun stop() {
        playbackJob?.cancel()
        mediaPlayer?.apply {
            try {
                if (isPlaying) stop()
                release()
            } catch (_: Exception) { }
        }
        mediaPlayer = null
        currentFilePath = null
        pendingSpeed = null
        _playbackState.value = PlaybackState.Idle
        _currentPosition.value = 0L
    }

    fun seekTo(positionMs: Long) {
        mediaPlayer?.seekTo(positionMs.toInt())
        _currentPosition.value = positionMs
    }

    fun seekForward(ms: Long = 15000) {
        val newPos = (_currentPosition.value + ms).coerceAtMost(_duration.value)
        seekTo(newPos)
    }

    fun seekBackward(ms: Long = 15000) {
        val newPos = (_currentPosition.value - ms).coerceAtLeast(0L)
        seekTo(newPos)
    }

    fun setSpeed(speed: Float) {
        _playbackSpeed.value = speed
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mediaPlayer?.let { player ->
                applySpeed(speed)
            } ?: run {
                // 如果 MediaPlayer 还未创建，保存倍率待应用
                pendingSpeed = speed
            }
        }
    }

    private fun applySpeed(speed: Float) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            try {
                mediaPlayer?.let { player ->
                    val params = PlaybackParams()
                    params.speed = speed
                    player.playbackParams = params
                }
            } catch (e: Exception) {
                // 某些设备可能不支持变速播放
                _speedSupported.value = false
            }
        }
    }

    private fun startPositionUpdates() {
        playbackJob?.cancel()
        playbackJob = scope.launch {
            while (isActive) {
                _currentPosition.value = mediaPlayer?.currentPosition?.toLong() ?: 0L
                delay(200)
            }
        }
    }

    fun release() {
        stop()
        scope.cancel()
    }
}

sealed class PlaybackState {
    data object Idle : PlaybackState()
    data object Playing : PlaybackState()
    data object Paused : PlaybackState()
    data class Error(val message: String) : PlaybackState()
}
