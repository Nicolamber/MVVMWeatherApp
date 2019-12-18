package com.nlambertucci.weatherappmvvm.model.database.unitlocalized

interface UnitSpecificCurrentWeatherEntry {
    val temperature: Int
    val conditionText: String
    val conditionUrl: String
    val windSpeed: Int
    val windDirection: String
    val precipitationVolume: Int
    val feelsLikeTemperature: Int
    val visibilityDistance: Int
}