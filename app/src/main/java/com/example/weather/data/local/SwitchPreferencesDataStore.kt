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
    name = SwitchPreferencesDataStore.SWITCH_PREFERENCES
)

@Singleton
class SwitchPreferencesDataStore @Inject constructor(@ApplicationContext context: Context) {

    private val switchPreferencesDataStore = context.dataStore

    val isChecked: Flow<Boolean>
        get() = switchPreferencesDataStore.data.map { preferences ->
            preferences[IS_CHECKED] ?: false
        }

    suspend fun onCheckedChange(isChecked: Boolean) {
        switchPreferencesDataStore.edit { preferences ->
            preferences[IS_CHECKED] = isChecked
        }
    }

    companion object {

        const val SWITCH_PREFERENCES = "switch_preferences"
        private val IS_CHECKED = booleanPreferencesKey("IS_CHECKED")
    }
}
