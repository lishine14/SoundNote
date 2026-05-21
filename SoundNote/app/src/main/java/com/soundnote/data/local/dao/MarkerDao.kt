package com.soundnote.data.local.dao

import androidx.room.*
import com.soundnote.data.local.entity.MarkerEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MarkerDao {

    @Query("SELECT * FROM markers WHERE recordingId = :recordingId ORDER BY timestampMs ASC")
    fun getMarkersForRecording(recordingId: String): Flow<List<MarkerEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMarker(marker: MarkerEntity)

    @Delete
    suspend fun deleteMarker(marker: MarkerEntity)

    @Query("DELETE FROM markers WHERE recordingId = :recordingId")
    suspend fun deleteMarkersForRecording(recordingId: String)
}
