package com.nlambertucci.weatherappmvvm

import android.app.Application
import android.content.Context
import com.google.android.gms.location.LocationServices
import com.jakewharton.threetenabp.AndroidThreeTen
import com.nlambertucci.weatherappmvvm.model.database.ForecastDatabase
import com.nlambertucci.weatherappmvvm.network.*
import com.nlambertucci.weatherappmvvm.utils.provider.UnitProvider
import com.nlambertucci.weatherappmvvm.utils.provider.UnitProviderImpl
import com.nlambertucci.weatherappmvvm.repository.ForecastRepository
import com.nlambertucci.weatherappmvvm.repository.ForecastRepositoryImpl
import com.nlambertucci.weatherappmvvm.utils.provider.LocationProvider
import com.nlambertucci.weatherappmvvm.utils.provider.LocationProviderImpl
import com.nlambertucci.weatherappmvvm.viewmodel.CurrentWeatherViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class ForecastApplication: Application(), KodeinAware{

    override val kodein = Kodein.lazy {
        import(androidXModule(this@ForecastApplication))

        bind() from singleton { ForecastDatabase(instance()) }
        bind() from singleton { instance<ForecastDatabase>().currentWeatherDao() }
        bind() from singleton { instance<ForecastDatabase>().weatherLocationDao() }
        bind<ConnectivityInterceptor>() with singleton { ConnectivityInterceptorImpl(instance()) }
        bind() from singleton { ApiWeatherService(instance()) }
        bind<WeatherNetworkDataSource>() with singleton { WeatherNetworkDataSourceImpl(instance()) }
        bind<LocationProvider>() with singleton { LocationProviderImpl(instance(),instance()) }
        bind<ForecastRepository>() with singleton {ForecastRepositoryImpl(instance(),instance(), instance(), instance()) }
        bind<UnitProvider>() with singleton { UnitProviderImpl(instance()) }
        bind() from provider { CurrentWeatherViewModelFactory(instance(), instance()) }
        bind() from provider { LocationServices.getFusedLocationProviderClient(instance<Context>()) }
    }

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
    }
}