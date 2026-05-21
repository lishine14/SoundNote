package com.soundnote.data.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.soundnote.domain.model.RecordingFormat
import com.soundnote.domain.model.RecordingQuality
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

@Singleton
class PreferencesManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    companion object {
        private val KEY_DEFAULT_FORMAT = intPreferencesKey("default_format")
        private val KEY_DEFAULT_QUALITY = intPreferencesKey("default_quality")
    }

    val defaultFormat: Flow<RecordingFormat> = context.dataStore.data.map { preferences ->
        val ordinal = preferences[KEY_DEFAULT_FORMAT] ?: RecordingFormat.MP3.ordinal
        RecordingFormat.entries.getOrElse(ordinal) { RecordingFormat.MP3 }
    }

    val defaultQuality: Flow<RecordingQuality> = context.dataStore.data.map { preferences ->
        val ordinal = preferences[KEY_DEFAULT_QUALITY] ?: RecordingQuality.STANDARD.ordinal
        RecordingQuality.entries.getOrElse(ordinal) { RecordingQuality.STANDARD }
    }

    suspend fun setDefaultFormat(format: RecordingFormat) {
        context.dataStore.edit { preferences ->
            preferences[KEY_DEFAULT_FORMAT] = format.ordinal
        }
    }

    suspend fun setDefaultQuality(quality: RecordingQuality) {
        context.dataStore.edit { preferences ->
            preferences[KEY_DEFAULT_QUALITY] = quality.ordinal
        }
    }
}
