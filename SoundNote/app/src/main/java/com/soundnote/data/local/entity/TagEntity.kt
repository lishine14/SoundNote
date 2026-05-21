package com.soundnote.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tags")
data class TagEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val color: String,
    val sortOrder: Int = 0,
    val createdAt: Long
)
