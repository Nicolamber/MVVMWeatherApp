package com.nlambertucci.weatherappmvvm.model.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.nlambertucci.weatherappmvvm.model.database.entity.CurrentWeatherEntry
import com.nlambertucci.weatherappmvvm.model.database.entity.WeatherLocation

@Database(
    entities = [CurrentWeatherEntry::class, WeatherLocation::class],
    version = 1
)

abstract class ForecastDatabase: RoomDatabase() {

    abstract fun currentWeatherDao(): CurrentWeatherDAO
    abstract fun weatherLocationDao(): WeatherLocationDAO

    companion object{
        @Volatile private var instance: ForecastDatabase? =null
        /**
         * Para controlar que no haya dos threads a la vez se usa ese LOCK
         */
        private val LOCK = Any()

        operator fun invoke(context: Context)= instance?: synchronized(LOCK){
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
                ForecastDatabase::class.java,
                "forecast.db")
                .build()

    }
}