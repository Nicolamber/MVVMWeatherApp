package com.nlambertucci.weatherappmvvm.repository

import androidx.lifecycle.LiveData
import com.nlambertucci.weatherappmvvm.model.database.unitlocalized.UnitSpecificCurrentWeatherEntry

interface ForecastRepository {

    suspend fun getCurrentWeather(metric:Boolean): LiveData<out UnitSpecificCurrentWeatherEntry>
}