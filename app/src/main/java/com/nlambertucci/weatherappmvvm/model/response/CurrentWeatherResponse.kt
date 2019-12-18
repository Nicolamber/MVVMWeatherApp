package com.nlambertucci.weatherappmvvm.model.response

import com.google.gson.annotations.SerializedName
import com.nlambertucci.weatherappmvvm.model.database.entity.CurrentWeatherEntry
import com.nlambertucci.weatherappmvvm.model.database.entity.Location
import com.nlambertucci.weatherappmvvm.model.database.Request


data class CurrentWeatherResponse(
    @SerializedName("current")
    val currentWeatherEntry: CurrentWeatherEntry,
    val location: Location,
    val request: Request
)