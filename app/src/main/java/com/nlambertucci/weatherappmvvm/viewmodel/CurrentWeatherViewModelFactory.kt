package com.nlambertucci.weatherappmvvm.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nlambertucci.weatherappmvvm.repository.ForecastRepository


class CurrentWeatherViewModelFactory(
    private val forecastRepository: ForecastRepository
): ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CurrentWeatherViewModel(forecastRepository) as T
    }
}