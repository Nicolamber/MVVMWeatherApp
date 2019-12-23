package com.nlambertucci.weatherappmvvm.view.weather

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer

import com.nlambertucci.weatherappmvvm.R
import com.nlambertucci.weatherappmvvm.network.ApiWeatherService
import com.nlambertucci.weatherappmvvm.network.ConnectivityInterceptorImpl
import com.nlambertucci.weatherappmvvm.network.WeatherNetworkDataSourceImpl
import com.nlambertucci.weatherappmvvm.viewmodel.CurrentWeatherViewModel
import com.nlambertucci.weatherappmvvm.viewmodel.CurrentWeatherViewModelFactory
import kotlinx.android.synthetic.main.current_weather_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class CurrentWeatherFragment : ScopedFragment(), KodeinAware {

    override val kodein by closestKodein()
    private val viewModelFactory: CurrentWeatherViewModelFactory by instance()


    private lateinit var viewModel: CurrentWeatherViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.current_weather_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(CurrentWeatherViewModel::class.java)

        bindUI()

//        val apiService = ApiWeatherService(ConnectivityInterceptorImpl(this.context!!))
//        val weatherNetworkDataSource = WeatherNetworkDataSourceImpl(apiService)
//        weatherNetworkDataSource.downloadCurrentWeather.observe(this, Observer {
//            textViewtt.text = it.currentWeatherEntry.toString()
//        })
//        GlobalScope.launch(Dispatchers.Main){
//            weatherNetworkDataSource.fetchCurrentWeather("London", "en")
//            //val currentWeatherResponse = apiService.getCurrentWeather("London").await()
//            //textViewtt.text = currentWeatherResponse.currentWeatherEntry.toString()
//        }
    }

    private fun bindUI() = launch{
        val currentWeather = viewModel.weather.await()
        currentWeather.observe(this@CurrentWeatherFragment, Observer {
            if(it == null) return@Observer
            textViewtt.text = it.toString()
        })
    }

}