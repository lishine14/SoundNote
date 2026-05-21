package com.soundnote.di

import android.content.Context
import androidx.room.Room
import com.soundnote.data.local.MIGRATION_1_2
import com.soundnote.data.local.SoundNoteDatabase
import com.soundnote.data.local.dao.FolderDao
import com.soundnote.data.local.dao.MarkerDao
import com.soundnote.data.local.dao.RecordingDao
import com.soundnote.data.local.dao.TagDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): SoundNoteDatabase {
        return Room.databaseBuilder(
            context,
            SoundNoteDatabase::class.java,
            "soundnote_database"
        )
        .addMigrations(MIGRATION_1_2)
        .build()
    }

    @Provides
    fun provideRecordingDao(database: SoundNoteDatabase): RecordingDao = database.recordingDao()

    @Provides
    fun provideTagDao(database: SoundNoteDatabase): TagDao = database.tagDao()

    @Provides
    fun provideFolderDao(database: SoundNoteDatabase): FolderDao = database.folderDao()

    @Provides
    fun provideMarkerDao(database: SoundNoteDatabase): MarkerDao = database.markerDao()
}
