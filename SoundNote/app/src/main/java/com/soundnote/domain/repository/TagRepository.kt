package com.soundnote.domain.repository

import com.soundnote.domain.model.Tag
import kotlinx.coroutines.flow.Flow

interface TagRepository {
    fun getAllTags(): Flow<List<Tag>>
    suspend fun getTagById(id: String): Tag?
    fun getTagsByIds(ids: List<String>): Flow<List<Tag>>
    suspend fun createTag(tag: Tag)
    suspend fun updateTag(tag: Tag)
    suspend fun deleteTag(id: String)
}
