package com.mmust.demeter.data.repository

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.mmust.demeter.domain.repository.PreferencesRepository
import com.mmust.demeter.util.Constants.USER_PREFERENCES_NAME
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.util.prefs.Preferences

class PreferencesRepositoryImpl(
    private val context: Context
): PreferencesRepository {

    val Context.dataStore by preferencesDataStore(
        name = USER_PREFERENCES_NAME
    )

    override suspend fun saveOnboardingStatus(isComplete: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[booleanPreferencesKey(USER_PREFERENCES_NAME)] = isComplete
        }
    }

    override suspend fun getOnboardingStatus(): Boolean {
        val preferences = context.dataStore.data.first()
    return preferences[booleanPreferencesKey(USER_PREFERENCES_NAME)] ?: false
    }
}