package com.yassineabou.weather.data.repository

import com.yassineabou.weather.data.local.TogglePreferencesDataStore
import javax.inject.Inject

class TogglePreferencesRepository @Inject constructor(
    private val togglePreferencesDataStore: TogglePreferencesDataStore
) {
    val toggled = togglePreferencesDataStore.toggled

    suspend fun toggle(toggled: Boolean) = togglePreferencesDataStore.toggle(toggled)
}
