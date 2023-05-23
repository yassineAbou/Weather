package com.example.weather.data.local

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(
    name = TogglePreferencesDataStore.TOGGLE_PREFERENCES
)

@Singleton
class TogglePreferencesDataStore @Inject constructor(@ApplicationContext context: Context) {

    private val togglePreferencesDataStore = context.dataStore

    val toggled: Flow<Boolean>
        get() = togglePreferencesDataStore.data.map { preferences ->
            preferences[TOGGLED] ?: false
        }

    suspend fun toggle(toggled: Boolean) {
        togglePreferencesDataStore.edit { preferences ->
            preferences[TOGGLED] = toggled
        }
    }

    companion object {

        const val TOGGLE_PREFERENCES = "toggle_preferences"
        private val TOGGLED = booleanPreferencesKey("TOGGLED")
    }
}
