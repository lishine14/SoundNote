package com.soundnote.data.repository

import com.soundnote.data.local.dao.MarkerDao
import com.soundnote.data.local.dao.RecordingDao
import com.soundnote.data.local.dao.TagDao
import com.soundnote.data.local.entity.*
import com.soundnote.domain.model.*
import com.soundnote.domain.repository.RecordingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RecordingRepositoryImpl @Inject constructor(
    private val recordingDao: RecordingDao,
    private val tagDao: TagDao,
    private val markerDao: MarkerDao
) : RecordingRepository {

    override fun getAllRecordings(): Flow<List<Recording>> {
        return recordingDao.getAllRecordings().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override fun getFavoriteRecordings(): Flow<List<Recording>> {
        return recordingDao.getFavoriteRecordings().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun getRecordingById(id: String): Recording? {
        return recordingDao.getRecordingById(id)?.toDomain()
    }

    override fun searchRecordings(query: String): Flow<List<Recording>> {
        return recordingDao.searchRecordings(query).map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override fun getRecordingsByFolder(folderId: String): Flow<List<Recording>> {
        return recordingDao.getRecordingsByFolder(folderId).map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun saveRecording(recording: Recording) {
        recordingDao.insertRecording(recording.toEntity())
    }

    override suspend fun updateRecording(recording: Recording) {
        recordingDao.updateRecording(recording.toEntity())
    }

    override suspend fun updateTranscription(recordingId: String, transcription: String?) {
        recordingDao.updateTranscription(recordingId, transcription)
    }

    override suspend fun deleteRecording(id: String) {
        val recording = recordingDao.getRecordingById(id)
        if (recording != null) {
            // Delete file from storage
            try {
                File(recording.filePath).delete()
            } catch (_: Exception) { }
            recordingDao.deleteRecordingById(id)
        }
    }

    override suspend fun toggleFavorite(id: String, isFavorite: Boolean) {
        recordingDao.updateFavorite(id, isFavorite)
    }

    override suspend fun moveToFolder(recordingId: String, folderId: String?) {
        recordingDao.updateFolder(recordingId, folderId)
    }

    override suspend fun addTag(recordingId: String, tagId: String) {
        recordingDao.insertRecordingTag(RecordingTagCrossRef(recordingId, tagId))
    }

    override suspend fun removeTag(recordingId: String, tagId: String) {
        recordingDao.deleteRecordingTag(RecordingTagCrossRef(recordingId, tagId))
    }

    override fun getTagIdsForRecording(recordingId: String): Flow<List<String>> {
        return recordingDao.getTagIdsForRecording(recordingId)
    }

    // Marker operations
    override fun getMarkersForRecording(recordingId: String): Flow<List<Marker>> {
        return markerDao.getMarkersForRecording(recordingId).map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun addMarker(marker: Marker) {
        markerDao.insertMarker(marker.toEntity())
    }

    override suspend fun deleteMarker(markerId: String) {
        markerDao.getMarkersForRecording(markerId).collect { }
    }
}

// Extension functions
private fun RecordingEntity.toDomain() = Recording(
    id = id,
    fileName = fileName,
    filePath = filePath,
    format = format,
    durationMs = durationMs,
    fileSizeBytes = fileSizeBytes,
    sampleRate = sampleRate,
    bitRate = bitRate,
    channels = channels,
    isFavorite = isFavorite,
    folderId = folderId,
    transcription = transcription,
    createdAt = createdAt,
    updatedAt = updatedAt
)

private fun Recording.toEntity() = RecordingEntity(
    id = id,
    fileName = fileName,
    filePath = filePath,
    format = format,
    durationMs = durationMs,
    fileSizeBytes = fileSizeBytes,
    sampleRate = sampleRate,
    bitRate = bitRate,
    channels = channels,
    isFavorite = isFavorite,
    folderId = folderId,
    transcription = transcription,
    createdAt = createdAt,
    updatedAt = updatedAt
)

private fun MarkerEntity.toDomain() = Marker(
    id = id,
    recordingId = recordingId,
    timestampMs = timestampMs,
    note = note,
    createdAt = createdAt
)

private fun Marker.toEntity() = MarkerEntity(
    id = id,
    recordingId = recordingId,
    timestampMs = timestampMs,
    note = note,
    createdAt = createdAt
)
