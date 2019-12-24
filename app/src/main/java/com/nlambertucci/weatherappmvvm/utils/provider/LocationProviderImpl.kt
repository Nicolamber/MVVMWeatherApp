package com.nlambertucci.weatherappmvvm.utils.provider

import com.nlambertucci.weatherappmvvm.model.database.entity.WeatherLocation

class LocationProviderImpl : LocationProvider {
    override suspend fun hasLocationChanged(lastWeatherLocation: WeatherLocation): Boolean {
        return true
    }

    override suspend fun getPreferredLocation(): String {
        return "Mendoza"
    }
}