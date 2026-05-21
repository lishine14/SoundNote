package com.soundnote.domain.usecase

import com.soundnote.domain.model.Recording
import com.soundnote.domain.repository.RecordingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllRecordingsUseCase @Inject constructor(
    private val repository: RecordingRepository
) {
    operator fun invoke(): Flow<List<Recording>> = repository.getAllRecordings()
}

class GetFavoriteRecordingsUseCase @Inject constructor(
    private val repository: RecordingRepository
) {
    operator fun invoke(): Flow<List<Recording>> = repository.getFavoriteRecordings()
}

class SearchRecordingsUseCase @Inject constructor(
    private val repository: RecordingRepository
) {
    operator fun invoke(query: String): Flow<List<Recording>> = repository.searchRecordings(query)
}

class SaveRecordingUseCase @Inject constructor(
    private val repository: RecordingRepository
) {
    suspend operator fun invoke(recording: Recording) = repository.saveRecording(recording)
}

class DeleteRecordingUseCase @Inject constructor(
    private val repository: RecordingRepository
) {
    suspend operator fun invoke(id: String) = repository.deleteRecording(id)
}

class ToggleFavoriteUseCase @Inject constructor(
    private val repository: RecordingRepository
) {
    suspend operator fun invoke(id: String, isFavorite: Boolean) = repository.toggleFavorite(id, isFavorite)
}
