package com.nlambertucci.weatherappmvvm.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nlambertucci.weatherappmvvm.model.response.CurrentWeatherResponse

class WeatherNetworkDataSourceImpl (
    private val apiWeatherService: ApiWeatherService
): WeatherNetworkDataSource {

    private val _downloadedCurrentWeather = MutableLiveData<CurrentWeatherResponse>()

    override val downloadCurrentWeather: LiveData<CurrentWeatherResponse>
        get() = _downloadedCurrentWeather

    override suspend fun fetchCurrentWeather(location: String, languajeCode: String) {
       try {
           val fetchCurrentWeather = apiWeatherService
               .getCurrentWeather(location,languajeCode)
               .await()
           _downloadedCurrentWeather.postValue(fetchCurrentWeather)
       }catch ( e: NoConnectivityException ){
           Log.e("Connectivity", "No internet Conection",e)
       }
    }
}