package com.soundnote.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "markers",
    foreignKeys = [
        ForeignKey(
            entity = RecordingEntity::class,
            parentColumns = ["id"],
            childColumns = ["recordingId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("recordingId")]
)
data class MarkerEntity(
    @PrimaryKey
    val id: String,
    val recordingId: String,
    val timestampMs: Long,
    val note: String?,
    val createdAt: Long
)
