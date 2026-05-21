package com.soundnote.presentation.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.soundnote.data.preferences.PreferencesManager
import com.soundnote.domain.model.RecordingFormat
import com.soundnote.domain.model.RecordingQuality
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SettingsUiState(
    val defaultFormat: RecordingFormat = RecordingFormat.MP3,
    val defaultQuality: RecordingQuality = RecordingQuality.STANDARD,
    val showFormatDialog: Boolean = false,
    val showQualityDialog: Boolean = false,
    val showStorageInfo: Boolean = false
)

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val preferencesManager: PreferencesManager
) : ViewModel() {

    private val _dialogs = MutableStateFlow(DialogState())

    private data class DialogState(
        val showFormatDialog: Boolean = false,
        val showQualityDialog: Boolean = false,
        val showStorageInfo: Boolean = false
    )

    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState: StateFlow<SettingsUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            combine(
                preferencesManager.defaultFormat,
                preferencesManager.defaultQuality,
                _dialogs
            ) { format, quality, dialogs ->
                SettingsUiState(
                    defaultFormat = format,
                    defaultQuality = quality,
                    showFormatDialog = dialogs.showFormatDialog,
                    showQualityDialog = dialogs.showQualityDialog,
                    showStorageInfo = dialogs.showStorageInfo
                )
            }.collect { state ->
                _uiState.value = state
            }
        }
    }

    fun showFormatDialog() {
        _dialogs.update { it.copy(showFormatDialog = true) }
    }

    fun showQualityDialog() {
        _dialogs.update { it.copy(showQualityDialog = true) }
    }

    fun showStorageInfo() {
        _dialogs.update { it.copy(showStorageInfo = true) }
    }

    fun dismissDialogs() {
        _dialogs.update { DialogState() }
    }

    fun setDefaultFormat(format: RecordingFormat) {
        viewModelScope.launch {
            preferencesManager.setDefaultFormat(format)
            dismissDialogs()
        }
    }

    fun setDefaultQuality(quality: RecordingQuality) {
        viewModelScope.launch {
            preferencesManager.setDefaultQuality(quality)
            dismissDialogs()
        }
    }
}
