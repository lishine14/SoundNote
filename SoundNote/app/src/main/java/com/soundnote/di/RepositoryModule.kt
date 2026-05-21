package com.soundnote.di

import com.soundnote.data.repository.RecordingRepositoryImpl
import com.soundnote.data.repository.TagRepositoryImpl
import com.soundnote.domain.repository.RecordingRepository
import com.soundnote.domain.repository.TagRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindRecordingRepository(
        impl: RecordingRepositoryImpl
    ): RecordingRepository

    @Binds
    @Singleton
    abstract fun bindTagRepository(
        impl: TagRepositoryImpl
    ): TagRepository
}
