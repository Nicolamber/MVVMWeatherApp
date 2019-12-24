package com.nlambertucci.weatherappmvvm.repository

import android.content.res.Resources
import androidx.lifecycle.LiveData
import com.nlambertucci.weatherappmvvm.model.database.CurrentWeatherDAO
import com.nlambertucci.weatherappmvvm.model.database.WeatherLocationDAO
import com.nlambertucci.weatherappmvvm.model.database.entity.WeatherLocation
import com.nlambertucci.weatherappmvvm.model.database.unitlocalized.UnitSpecificCurrentWeatherEntry
import com.nlambertucci.weatherappmvvm.model.response.CurrentWeatherResponse
import com.nlambertucci.weatherappmvvm.network.WeatherNetworkDataSource
import com.nlambertucci.weatherappmvvm.utils.provider.LocationProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.ZonedDateTime

class ForecastRepositoryImpl(
    private val currentWeatherDAO: CurrentWeatherDAO,
    private val weatherNetworkDataSource: WeatherNetworkDataSource,
    private val weatherLocationDAO: WeatherLocationDAO,
    private val locationProvider: LocationProvider
) : ForecastRepository {


    init {
        weatherNetworkDataSource.downloadCurrentWeather.observeForever{ newCurrentWeather ->
            persistCurrentWeather(newCurrentWeather)
        }
    }
    override suspend fun getCurrentWeather(metric: Boolean): LiveData< out UnitSpecificCurrentWeatherEntry> {
        return withContext(Dispatchers.IO){
            initWeatherData()
            return@withContext if(metric) currentWeatherDAO.getWeatherMetric()
            else currentWeatherDAO.getWeatherImperial()
        }
    }


    private fun persistCurrentWeather(fetchedWeather: CurrentWeatherResponse){
        GlobalScope.launch (Dispatchers.IO){
            currentWeatherDAO.upsert(fetchedWeather.currentWeatherEntry)
            weatherLocationDAO.upsert(fetchedWeather.location)
        }
    }

    private suspend fun initWeatherData(){
        val lastWeatherLocation = weatherLocationDAO.getLocation().value

        if (lastWeatherLocation == null || locationProvider.hasLocationChanged(lastWeatherLocation)){
            fetchCurrentWeather()
            return
        }

        if(isFetchCurrentNeeded(lastWeatherLocation.zonedDateTime))
            fetchCurrentWeather()
    }

    private suspend fun fetchCurrentWeather(){
        weatherNetworkDataSource.fetchCurrentWeather(
            locationProvider.getPreferredLocation(),
            Resources.getSystem().configuration.locale.language
        )
    }

    private fun isFetchCurrentNeeded(lastFetchTime: ZonedDateTime): Boolean{
        val thirtyMinutesAgo = ZonedDateTime.now().minusMinutes(30)
        return lastFetchTime.isBefore(thirtyMinutesAgo)
    }

    override suspend fun getWeatherLocation(): LiveData<WeatherLocation> {
        return withContext(Dispatchers.IO){
            return@withContext weatherLocationDAO.getLocation()
        }

    }
}

