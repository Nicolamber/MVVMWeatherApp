package com.nlambertucci.weatherappmvvm.utils.provider

import com.nlambertucci.weatherappmvvm.model.database.entity.WeatherLocation

interface LocationProvider {

    suspend fun hasLocationChanged(lastWeatherLocation: WeatherLocation): Boolean
    suspend fun getPreferredLocation():String
}