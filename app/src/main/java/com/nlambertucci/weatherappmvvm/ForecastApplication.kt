package com.nlambertucci.weatherappmvvm

import android.app.Application
import com.nlambertucci.weatherappmvvm.model.database.ForecastDatabase
import com.nlambertucci.weatherappmvvm.network.*
import com.nlambertucci.weatherappmvvm.repository.ForecastRepository
import com.nlambertucci.weatherappmvvm.repository.ForecastRepositoryImpl
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

class ForecastApplication: Application(), KodeinAware{

    override val kodein = Kodein.lazy {
        import(androidXModule(this@ForecastApplication))

        bind() from singleton { ForecastDatabase(instance()) }
        bind() from singleton { instance<ForecastDatabase>().currentWeatherDao() }
        bind<ConnectivityInterceptor>() with singleton { ConnectivityInterceptorImpl(instance()) }
        bind() from singleton { ApiWeatherService(instance()) }
        bind<WeatherNetworkDataSource>() with singleton { WeatherNetworkDataSourceImpl(instance()) }
        bind<ForecastRepository>() with singleton {ForecastRepositoryImpl(instance(),instance()) }
    }
}