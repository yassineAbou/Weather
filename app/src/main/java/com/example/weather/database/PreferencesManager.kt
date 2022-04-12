package com.example.weather.database

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map


class PreferencesManager(private val context: Context) {

    suspend fun onSelectAutoLocation(selected: Boolean){
        context.counterDataStore.edit { preferences ->
            preferences[AUTO_LOCATION] = selected
        }
    }

    // A getter for the counter value flow
    val selected : Flow<Boolean>
        get() = context.counterDataStore.data.map { preferences ->
            preferences[AUTO_LOCATION] ?: false
        }

    suspend fun readSelected (): Boolean =
        context.counterDataStore.data.map { preferences ->
            preferences[AUTO_LOCATION] ?: false
        }.first()

    companion object {
        private const val DATASTORE_NAME = "autoLocation_preferences"

        private val AUTO_LOCATION = booleanPreferencesKey("autoLocation_key");

        private val Context.counterDataStore by preferencesDataStore(
            name = DATASTORE_NAME
        )
    }

}