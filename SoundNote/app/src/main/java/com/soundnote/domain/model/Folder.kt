package com.soundnote.domain.model

data class Folder(
    val id: String,
    val name: String,
    val parentId: String? = null,
    val isSystem: Boolean = false,
    val sortOrder: Int = 0,
    val createdAt: Long
)
