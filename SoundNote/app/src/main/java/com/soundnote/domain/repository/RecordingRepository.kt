package com.soundnote.domain.repository

import com.soundnote.domain.model.Marker
import com.soundnote.domain.model.Recording
import kotlinx.coroutines.flow.Flow

interface RecordingRepository {
    fun getAllRecordings(): Flow<List<Recording>>
    fun getFavoriteRecordings(): Flow<List<Recording>>
    suspend fun getRecordingById(id: String): Recording?
    fun searchRecordings(query: String): Flow<List<Recording>>
    fun getRecordingsByFolder(folderId: String): Flow<List<Recording>>
    suspend fun saveRecording(recording: Recording)
    suspend fun updateRecording(recording: Recording)
    suspend fun updateTranscription(recordingId: String, transcription: String?)
    suspend fun deleteRecording(id: String)
    suspend fun toggleFavorite(id: String, isFavorite: Boolean)
    suspend fun moveToFolder(recordingId: String, folderId: String?)
    suspend fun addTag(recordingId: String, tagId: String)
    suspend fun removeTag(recordingId: String, tagId: String)
    fun getTagIdsForRecording(recordingId: String): Flow<List<String>>
    fun getMarkersForRecording(recordingId: String): Flow<List<Marker>>
    suspend fun addMarker(marker: Marker)
    suspend fun deleteMarker(markerId: String)
}
