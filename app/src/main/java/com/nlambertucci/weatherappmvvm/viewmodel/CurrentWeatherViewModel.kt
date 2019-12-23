package com.nlambertucci.weatherappmvvm.viewmodel

import androidx.lifecycle.ViewModel
import com.nlambertucci.weatherappmvvm.repository.ForecastRepository
import com.nlambertucci.weatherappmvvm.utils.UnitSystem
import com.nlambertucci.weatherappmvvm.utils.lazyDeferred

class CurrentWeatherViewModel (
    private val forecastRepository: ForecastRepository
) : ViewModel() {

    private val unitSystem = UnitSystem.METRIC //get from settings in a future

    val isMetric: Boolean
        get() = unitSystem == UnitSystem.METRIC

    val weather by lazyDeferred {
        forecastRepository.getCurrentWeather(isMetric)
    }
}

