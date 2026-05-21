package com.soundnote.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.soundnote.domain.model.Marker
import com.soundnote.domain.model.Recording
import com.soundnote.domain.model.Tag
import com.soundnote.domain.repository.RecordingRepository
import com.soundnote.domain.repository.TagRepository
import com.soundnote.service.AudioPlayer
import com.soundnote.service.PlaybackState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayerViewModel @Inject constructor(
    private val audioPlayer: AudioPlayer,
    private val recordingRepository: RecordingRepository,
    private val tagRepository: TagRepository
) : ViewModel() {

    val playbackState: StateFlow<PlaybackState> = audioPlayer.playbackState
    val currentPosition: StateFlow<Long> = audioPlayer.currentPosition
    val duration: StateFlow<Long> = audioPlayer.duration
    val playbackSpeed: StateFlow<Float> = audioPlayer.playbackSpeed
    val speedSupported: StateFlow<Boolean> = audioPlayer.speedSupported

    private val _recording = MutableStateFlow<Recording?>(null)
    val recording: StateFlow<Recording?> = _recording.asStateFlow()

    private val _markers = MutableStateFlow<List<Marker>>(emptyList())
    val markers: StateFlow<List<Marker>> = _markers.asStateFlow()

    private val _recordingTags = MutableStateFlow<List<Tag>>(emptyList())
    val recordingTags: StateFlow<List<Tag>> = _recordingTags.asStateFlow()

    private var currentRecordingId: String? = null

    fun loadRecording(recordingId: String) {
        currentRecordingId = recordingId
        viewModelScope.launch {
            val rec = recordingRepository.getRecordingById(recordingId)
            _recording.value = rec
            rec?.let {
                audioPlayer.play(it.filePath)
                recordingRepository.getMarkersForRecording(recordingId).collect { markers ->
                    _markers.value = markers
                }
            }
        }
        viewModelScope.launch {
            recordingRepository.getTagIdsForRecording(recordingId).collect { tagIds ->
                if (tagIds.isNotEmpty()) {
                    tagRepository.getTagsByIds(tagIds).collect { tags ->
                        _recordingTags.value = tags
                    }
                } else {
                    _recordingTags.value = emptyList()
                }
            }
        }
    }

    fun play() {
        _recording.value?.let { audioPlayer.play(it.filePath) }
    }

    fun pause() {
        audioPlayer.pause()
    }

    fun seekTo(positionMs: Long) {
        audioPlayer.seekTo(positionMs)
    }

    fun seekForward() {
        audioPlayer.seekForward()
    }

    fun seekBackward() {
        audioPlayer.seekBackward()
    }

    fun setSpeed(speed: Float) {
        audioPlayer.setSpeed(speed)
    }

    fun toggleFavorite() {
        viewModelScope.launch {
            _recording.value?.let { rec ->
                recordingRepository.toggleFavorite(rec.id, !rec.isFavorite)
                _recording.value = rec.copy(isFavorite = !rec.isFavorite)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        audioPlayer.stop()
    }
}
