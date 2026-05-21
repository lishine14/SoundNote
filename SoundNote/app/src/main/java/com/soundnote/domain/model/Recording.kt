package com.soundnote.domain.model

data class Recording(
    val id: String,
    val fileName: String,
    val filePath: String,
    val format: String,
    val durationMs: Long,
    val fileSizeBytes: Long,
    val sampleRate: Int = 44100,
    val bitRate: Int = 192000,
    val channels: Int = 1,
    val isFavorite: Boolean = false,
    val folderId: String? = null,
    val transcription: String? = null,
    val createdAt: Long,
    val updatedAt: Long
) {
    val formattedDuration: String
        get() {
            val totalSeconds = durationMs / 1000
            val hours = totalSeconds / 3600
            val minutes = (totalSeconds % 3600) / 60
            val seconds = totalSeconds % 60
            return if (hours > 0) {
                String.format("%02d:%02d:%02d", hours, minutes, seconds)
            } else {
                String.format("%02d:%02d", minutes, seconds)
            }
        }

    val formattedFileSize: String
        get() {
            return when {
                fileSizeBytes >= 1024 * 1024 -> String.format("%.1fMB", fileSizeBytes / (1024.0 * 1024.0))
                fileSizeBytes >= 1024 -> String.format("%.1fKB", fileSizeBytes / 1024.0)
                else -> "${fileSizeBytes}B"
            }
        }
}
