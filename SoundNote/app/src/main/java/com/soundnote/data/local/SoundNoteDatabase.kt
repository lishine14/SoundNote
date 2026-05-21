package com.soundnote.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.soundnote.data.local.dao.FolderDao
import com.soundnote.data.local.dao.MarkerDao
import com.soundnote.data.local.dao.RecordingDao
import com.soundnote.data.local.dao.TagDao
import com.soundnote.data.local.entity.*

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("ALTER TABLE recordings ADD COLUMN transcription TEXT DEFAULT NULL")
    }
}

@Database(
    entities = [
        RecordingEntity::class,
        TagEntity::class,
        FolderEntity::class,
        RecordingTagCrossRef::class,
        MarkerEntity::class
    ],
    version = 2,
    exportSchema = false
)
abstract class SoundNoteDatabase : RoomDatabase() {
    abstract fun recordingDao(): RecordingDao
    abstract fun tagDao(): TagDao
    abstract fun folderDao(): FolderDao
    abstract fun markerDao(): MarkerDao
}
