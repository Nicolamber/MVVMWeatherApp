package com.nlambertucci.weatherappmvvm.viewmodel

import androidx.lifecycle.ViewModel
import com.nlambertucci.weatherappmvvm.utils.provider.UnitProvider
import com.nlambertucci.weatherappmvvm.repository.ForecastRepository
import com.nlambertucci.weatherappmvvm.utils.UnitSystem
import com.nlambertucci.weatherappmvvm.utils.lazyDeferred

class CurrentWeatherViewModel (
    private val forecastRepository: ForecastRepository,
    private val unitProvider: UnitProvider
) : ViewModel() {

    private val unitSystem = unitProvider.getUnitSystem()

    val isMetric:Boolean
        get() = unitSystem == UnitSystem.METRIC


    val weather by lazyDeferred {
        forecastRepository.getCurrentWeather(isMetric)
    }

    val weatherLocation by lazyDeferred{
        forecastRepository.getWeatherLocation()
    }
}

