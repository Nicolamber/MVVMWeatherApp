package com.nlambertucci.weatherappmvvm.model.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nlambertucci.weatherappmvvm.model.database.entity.CURRENT_WEATHER_ID
import com.nlambertucci.weatherappmvvm.model.database.entity.CurrentWeatherEntry
import com.nlambertucci.weatherappmvvm.model.database.unitlocalized.ImperialweatherEntry
import com.nlambertucci.weatherappmvvm.model.database.unitlocalized.MetricWeatherEntry


@Dao
interface CurrentWeatherDAO {
    /**
     * Como lo que guardamos es siempre la ultima llamada, se usa el REPLACE para que si hay conflicto
     * reemplace lo viejo con lo nuevo, tambien por eso el ID es siempre el mismo
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(weatherEntry: CurrentWeatherEntry)

    @Query(value = "Select * from current_weather where id = $CURRENT_WEATHER_ID")
    fun getWeatherMetric(): LiveData<MetricWeatherEntry>

    @Query(value = "Select * from current_weather where id = $CURRENT_WEATHER_ID")
    fun getWeatherImperial(): LiveData<ImperialweatherEntry>

}