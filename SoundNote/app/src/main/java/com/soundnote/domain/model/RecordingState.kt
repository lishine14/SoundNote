package com.soundnote.domain.model

sealed class RecordingState {
    data object Idle : RecordingState()
    data class Recording(
        val durationMs: Long = 0L,
        val amplitudes: List<Float> = emptyList()
    ) : RecordingState()
    data class Paused(val durationMs: Long) : RecordingState()
    data object Saving : RecordingState()
    data object Saved : RecordingState()
    data class Error(val message: String) : RecordingState()
}
