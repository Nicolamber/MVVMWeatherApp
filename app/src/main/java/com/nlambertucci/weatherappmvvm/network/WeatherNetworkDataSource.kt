package com.nlambertucci.weatherappmvvm.network

import androidx.lifecycle.LiveData
import com.nlambertucci.weatherappmvvm.model.response.CurrentWeatherResponse

interface WeatherNetworkDataSource {

    val downloadCurrentWeather: LiveData<CurrentWeatherResponse>

    suspend fun fetchCurrentWeather(
        location: String,
        languajeCode: String
    )
}