package com.soundnote.domain.model

enum class RecordingFormat(
    val extension: String,
    val mimeType: String,
    val displayName: String
) {
    MP3("mp3", "audio/mpeg", "MP3"),
    AAC("aac", "audio/aac", "AAC"),
    M4A("m4a", "audio/mp4", "M4A"),
    WAV("wav", "audio/wav", "WAV");

    companion object {
        fun fromExtension(ext: String): RecordingFormat {
            return entries.find { it.extension.equals(ext, ignoreCase = true) } ?: MP3
        }
    }
}

enum class RecordingQuality(
    val displayName: String,
    val sampleRate: Int,
    val bitRate: Int,
    val channels: Int
) {
    LOW("低品质", 22050, 64000, 1),
    STANDARD("标准品质", 44100, 128000, 1),
    HIGH("高品质", 44100, 192000, 2),
    ULTRA("超高品质", 48000, 320000, 2);

    companion object {
        fun fromOrdinal(ordinal: Int): RecordingQuality {
            return entries.getOrElse(ordinal) { STANDARD }
        }
    }
}
