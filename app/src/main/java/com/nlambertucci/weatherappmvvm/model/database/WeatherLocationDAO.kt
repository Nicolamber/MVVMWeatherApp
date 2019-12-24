package com.nlambertucci.weatherappmvvm.model.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nlambertucci.weatherappmvvm.model.database.entity.WEATHER_LOCATION_ID
import com.nlambertucci.weatherappmvvm.model.database.entity.WeatherLocation


@Dao
interface WeatherLocationDAO {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(weatherLocation: WeatherLocation)

    @Query("select * from weather_location where id = $WEATHER_LOCATION_ID")
    fun getLocation(): LiveData<WeatherLocation>

}