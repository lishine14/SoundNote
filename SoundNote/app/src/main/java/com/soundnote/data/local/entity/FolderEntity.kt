package com.soundnote.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "folders")
data class FolderEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val parentId: String? = null,
    val isSystem: Boolean = false,
    val sortOrder: Int = 0,
    val createdAt: Long
)
