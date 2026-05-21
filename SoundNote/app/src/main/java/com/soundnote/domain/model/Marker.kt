package com.soundnote.domain.model

data class Marker(
    val id: String,
    val recordingId: String,
    val timestampMs: Long,
    val note: String?,
    val createdAt: Long
) {
    val formattedTimestamp: String
        get() {
            val totalSeconds = timestampMs / 1000
            val minutes = totalSeconds / 60
            val seconds = totalSeconds % 60
            return String.format("%02d:%02d", minutes, seconds)
        }
}
