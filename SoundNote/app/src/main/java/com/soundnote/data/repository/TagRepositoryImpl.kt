package com.soundnote.data.repository

import com.soundnote.data.local.dao.TagDao
import com.soundnote.data.local.entity.TagEntity
import com.soundnote.domain.model.Tag
import com.soundnote.domain.repository.TagRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TagRepositoryImpl @Inject constructor(
    private val tagDao: TagDao
) : TagRepository {

    override fun getAllTags(): Flow<List<Tag>> {
        return tagDao.getAllTags().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun getTagById(id: String): Tag? {
        return tagDao.getTagById(id)?.toDomain()
    }

    override fun getTagsByIds(ids: List<String>): Flow<List<Tag>> {
        return tagDao.getTagsByIds(ids).map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun createTag(tag: Tag) {
        tagDao.insertTag(tag.toEntity())
    }

    override suspend fun updateTag(tag: Tag) {
        tagDao.updateTag(tag.toEntity())
    }

    override suspend fun deleteTag(id: String) {
        tagDao.getTagById(id)?.let { tagDao.deleteTag(it) }
    }
}

private fun TagEntity.toDomain() = Tag(
    id = id,
    name = name,
    color = color,
    sortOrder = sortOrder,
    createdAt = createdAt
)

private fun Tag.toEntity() = TagEntity(
    id = id,
    name = name,
    color = color,
    sortOrder = sortOrder,
    createdAt = createdAt
)
