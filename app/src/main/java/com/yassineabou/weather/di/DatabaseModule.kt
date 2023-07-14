package com.yassineabou.weather.di

import android.content.Context
import androidx.room.Room
import com.yassineabou.weather.data.local.LocationDao
import com.yassineabou.weather.data.local.LocationDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideListLocationsDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
        LocationDatabase::class.java,
        "list_Locations_database"
    ).createFromAsset("database/list_locations.db").build()

    @Singleton
    @Provides
    fun provideLocationDao(locationDatabase: LocationDatabase):
        LocationDao {
        return locationDatabase.getLocationDao()
    }
}
