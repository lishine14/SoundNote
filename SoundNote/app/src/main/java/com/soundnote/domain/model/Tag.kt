package com.soundnote.domain.model

data class Tag(
    val id: String,
    val name: String,
    val color: String,
    val sortOrder: Int = 0,
    val createdAt: Long
)
