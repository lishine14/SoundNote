package com.soundnote.data.local.dao

import androidx.room.*
import com.soundnote.data.local.entity.RecordingEntity
import com.soundnote.data.local.entity.RecordingTagCrossRef
import kotlinx.coroutines.flow.Flow

@Dao
interface RecordingDao {

    @Query("SELECT * FROM recordings ORDER BY createdAt DESC")
    fun getAllRecordings(): Flow<List<RecordingEntity>>

    @Query("SELECT * FROM recordings WHERE isFavorite = 1 ORDER BY createdAt DESC")
    fun getFavoriteRecordings(): Flow<List<RecordingEntity>>

    @Query("SELECT * FROM recordings WHERE id = :id")
    suspend fun getRecordingById(id: String): RecordingEntity?

    @Query("SELECT * FROM recordings WHERE fileName LIKE '%' || :query || '%' ORDER BY createdAt DESC")
    fun searchRecordings(query: String): Flow<List<RecordingEntity>>

    @Query("SELECT * FROM recordings WHERE folderId = :folderId ORDER BY createdAt DESC")
    fun getRecordingsByFolder(folderId: String): Flow<List<RecordingEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecording(recording: RecordingEntity)

    @Update
    suspend fun updateRecording(recording: RecordingEntity)

    @Delete
    suspend fun deleteRecording(recording: RecordingEntity)

    @Query("DELETE FROM recordings WHERE id = :id")
    suspend fun deleteRecordingById(id: String)

    @Query("UPDATE recordings SET isFavorite = :isFavorite WHERE id = :id")
    suspend fun updateFavorite(id: String, isFavorite: Boolean)

    @Query("UPDATE recordings SET folderId = :folderId WHERE id = :id")
    suspend fun updateFolder(id: String, folderId: String?)

    @Query("UPDATE recordings SET transcription = :transcription WHERE id = :id")
    suspend fun updateTranscription(id: String, transcription: String?)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertRecordingTag(crossRef: RecordingTagCrossRef)

    @Delete
    suspend fun deleteRecordingTag(crossRef: RecordingTagCrossRef)

    @Query("SELECT tagId FROM recording_tags WHERE recordingId = :recordingId")
    fun getTagIdsForRecording(recordingId: String): Flow<List<String>>
}
