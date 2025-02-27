package com.mmust.demeter.data.source

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

object SessionKeys {
    val USER_UID = stringPreferencesKey("user_uid")
}
class SessionManager @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    suspend fun saveSession(uid: String) {
        dataStore.edit{ prefs ->
            prefs[SessionKeys.USER_UID] = uid
        }
    }
    fun getSessionUid(): Flow<String?> {
        return dataStore.data.map { prefs ->
            prefs[SessionKeys.USER_UID]
        }
    }
}