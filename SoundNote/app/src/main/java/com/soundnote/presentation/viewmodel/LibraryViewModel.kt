package com.soundnote.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.soundnote.domain.model.Recording
import com.soundnote.domain.model.Tag
import com.soundnote.domain.repository.RecordingRepository
import com.soundnote.domain.repository.TagRepository
import com.soundnote.domain.usecase.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LibraryViewModel @Inject constructor(
    private val getAllRecordingsUseCase: GetAllRecordingsUseCase,
    private val searchRecordingsUseCase: SearchRecordingsUseCase,
    private val deleteRecordingUseCase: DeleteRecordingUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase,
    private val tagRepository: TagRepository
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _selectedTab = MutableStateFlow(0)
    val selectedTab: StateFlow<Int> = _selectedTab.asStateFlow()

    val recordings: StateFlow<List<Recording>> = _searchQuery
        .debounce(300)
        .flatMapLatest { query ->
            if (query.isBlank()) {
                getAllRecordingsUseCase()
            } else {
                searchRecordingsUseCase(query)
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val favoriteRecordings: StateFlow<List<Recording>> = getAllRecordingsUseCase()
        .map { list -> list.filter { it.isFavorite } }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val allTags: StateFlow<List<Tag>> = tagRepository.getAllTags()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _selectedIds = MutableStateFlow<Set<String>>(emptySet())
    val selectedIds: StateFlow<Set<String>> = _selectedIds.asStateFlow()

    val isSelectionMode: StateFlow<Boolean> = _selectedIds
        .map { it.isNotEmpty() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    private val _deleteResult = MutableSharedFlow<Boolean>()
    val deleteResult: SharedFlow<Boolean> = _deleteResult.asSharedFlow()

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun setSelectedTab(tab: Int) {
        _selectedTab.value = tab
        _selectedIds.value = emptySet()
    }

    fun toggleSelection(id: String) {
        val current = _selectedIds.value.toMutableSet()
        if (id in current) current.remove(id) else current.add(id)
        _selectedIds.value = current
    }

    fun clearSelection() {
        _selectedIds.value = emptySet()
    }

    fun selectAll(ids: List<String>) {
        _selectedIds.value = ids.toSet()
    }

    fun deleteRecording(id: String) {
        viewModelScope.launch {
            deleteRecordingUseCase(id)
            _deleteResult.emit(true)
        }
    }

    fun deleteSelected() {
        viewModelScope.launch {
            _selectedIds.value.forEach { id ->
                deleteRecordingUseCase(id)
            }
            _selectedIds.value = emptySet()
            _deleteResult.emit(true)
        }
    }

    fun toggleFavorite(id: String, isFavorite: Boolean) {
        viewModelScope.launch {
            toggleFavoriteUseCase(id, isFavorite)
        }
    }
}
