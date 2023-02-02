package com.example.weather.data.repository

import com.example.weather.data.local.SwitchPreferencesDataStore
import javax.inject.Inject

class SwitchPreferencesRepository @Inject constructor(
    private val switchPreferencesDataStore: SwitchPreferencesDataStore
) {
    val isChecked = switchPreferencesDataStore.isChecked

    suspend fun onCheckedChange(isChecked: Boolean) =
        switchPreferencesDataStore.onCheckedChange(isChecked)
}
