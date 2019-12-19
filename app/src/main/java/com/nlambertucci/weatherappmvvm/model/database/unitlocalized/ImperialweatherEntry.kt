package com.nlambertucci.weatherappmvvm.model.database.unitlocalized

import androidx.room.ColumnInfo

data class ImperialweatherEntry(
    @ColumnInfo(name = "temperature")
    override val temperature: Int,
    @ColumnInfo(name = "windSpeed")
    override val windSpeed: Int,
    @ColumnInfo(name = "windDir")
    override val windDirection: String,
    @ColumnInfo(name= "precip")
    override val precipitationVolume: Int,
    @ColumnInfo(name = "feelslike")
    override val feelsLikeTemperature: Int,
    @ColumnInfo(name = "visibility")
    override val visibilityDistance: Int
): UnitSpecificCurrentWeatherEntry