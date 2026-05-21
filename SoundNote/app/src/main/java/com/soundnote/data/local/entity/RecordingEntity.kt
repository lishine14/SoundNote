package com.soundnote.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recordings")
data class RecordingEntity(
    @PrimaryKey
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
)
