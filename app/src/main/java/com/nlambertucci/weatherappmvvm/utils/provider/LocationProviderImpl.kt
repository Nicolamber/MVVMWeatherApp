package com.nlambertucci.weatherappmvvm.utils.provider

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.nlambertucci.weatherappmvvm.model.database.entity.WeatherLocation
import com.nlambertucci.weatherappmvvm.utils.LocationPermissionNotGrantedException
import com.nlambertucci.weatherappmvvm.utils.asDeferred
import kotlinx.coroutines.Deferred

const val USE_DEVICE_LOCATION = "USE_DEVICE_LOCATION"
const val CUSTOM_LOCATION ="CUSTOM_LOCATION"

class LocationProviderImpl (
    context: Context,
    private val fusedLocationProviderClient: FusedLocationProviderClient
    ):PreferenceProvider(context), LocationProvider {

    private val appContext = context.applicationContext

    override suspend fun hasLocationChanged(lastWeatherLocation: WeatherLocation): Boolean {

        val deviceLocationChange = try{
            hasDeviceLocationChange(lastWeatherLocation)
        }catch (e: LocationPermissionNotGrantedException){
            e.printStackTrace()
            false
        }

        return deviceLocationChange || hasCustomLocationChanged(lastWeatherLocation)
    }

    override suspend fun getPreferredLocation(): String {
        if (isUsingDeviceLocation()){
            try {
                val deviceLocation = getLastDeviceLocation().await()
                    ?: return "${getCustomLocationName()}"
                return "${deviceLocation.latitude},${deviceLocation.longitude}"
            }catch (e: LocationPermissionNotGrantedException){
                return "${getCustomLocationName()}"
            }
        }else{
            return "${getCustomLocationName()}"
        }
    }

    private suspend fun hasDeviceLocationChange(lastWeatherLocation: WeatherLocation): Boolean{
        if(!isUsingDeviceLocation())
            return false

        val deviceLocation = getLastDeviceLocation().await()
            ?: return false

        //comparin doubles cannot be done with "=="
        val comparisonThreshold = 0.03
        return Math.abs(deviceLocation.latitude - lastWeatherLocation.lat) > comparisonThreshold &&
                Math.abs(deviceLocation.longitude - lastWeatherLocation.lon) > comparisonThreshold
    }

    private fun hasCustomLocationChanged(lastWeatherLocation: WeatherLocation): Boolean{
        val customLocationName = getCustomLocationName()
        return customLocationName != lastWeatherLocation.name
    }

    private fun getCustomLocationName(): String?{
        return preferences.getString(CUSTOM_LOCATION, null)
    }

    private fun isUsingDeviceLocation(): Boolean{
        return preferences.getBoolean(USE_DEVICE_LOCATION,true)
    }

    private fun getLastDeviceLocation(): Deferred<Location?>{
        return if(hasLocationPermission())
            fusedLocationProviderClient.lastLocation.asDeferred()
        else
            throw LocationPermissionNotGrantedException()


    }

    private fun hasLocationPermission():Boolean{
        return ContextCompat.checkSelfPermission(appContext,
            Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }



}