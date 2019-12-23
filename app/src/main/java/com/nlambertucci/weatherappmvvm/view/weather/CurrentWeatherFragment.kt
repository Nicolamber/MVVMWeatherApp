package com.nlambertucci.weatherappmvvm.view.weather

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer

import com.nlambertucci.weatherappmvvm.R
import com.nlambertucci.weatherappmvvm.network.ApiWeatherService
import com.nlambertucci.weatherappmvvm.network.ConnectivityInterceptorImpl
import com.nlambertucci.weatherappmvvm.network.WeatherNetworkDataSourceImpl
import com.nlambertucci.weatherappmvvm.utils.GlideApp
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
            group_loading.visibility = View.GONE
            updateLocation("Mendoza")
            updateDateToToday()
            updateTemperatures(it.temperature,it.feelsLikeTemperature)
           // updateCondition(it.)
            updatePrecipitation(it.precipitationVolume)
            updateWind(it.windDirection, it.windSpeed)
            updateVisibility(it.visibilityDistance)
            updateCondition(it.temperature)

        })
    }

    private fun chooseLocalizedUnitAbbreviation(metric: String, imperial: String): String{
        return if(viewModel.isMetric) metric else imperial
    }

    private fun updateLocation(location:String){
        (activity as? AppCompatActivity)?.supportActionBar?.title = location
    }

    private fun updateDateToToday(){
        (activity as? AppCompatActivity)?.supportActionBar?.subtitle = "Hoy"
    }


    private fun updateTemperatures(temperature: Int, feelsLike:Int){
        val unitAbreviation = chooseLocalizedUnitAbbreviation("ºC", "ºF")
        textView_temperature.text = "$temperature$unitAbreviation"
        textView_feels_like_temperature.text = "Sensación térmica$feelsLike$unitAbreviation"

        if( temperature > 25){
            updateImage("https://cdn3.iconfinder.com/data/icons/disaster-and-weather-conditions/48/8-512.png")
        }else{
            updateImage("https://cdn2.iconfinder.com/data/icons/weather-colored-icons/47/weather_70-512.png")
        }
    }

    private fun updatePrecipitation(precipitationVolume: Int){
        val unitAbreviation = chooseLocalizedUnitAbbreviation("mm", "in")
        textView_precipitation.text = "Precipitaciones: $precipitationVolume $unitAbreviation"
    }

    private fun updateWind(windDirection: String, windSpeed: Int){
        val unitAbbreviation = chooseLocalizedUnitAbbreviation("Km/h","mph")
        textView_wind.text = "Viento: $windDirection, $windSpeed $unitAbbreviation"
    }

    private fun updateVisibility(visibility: Int){
        val unitAbbreviation = chooseLocalizedUnitAbbreviation("km","mi")
        textView_visibility.text = "Visibilidad: $visibility $unitAbbreviation"
    }

    private fun updateImage(url: String){
        GlideApp.with(this@CurrentWeatherFragment)
            .load(url)
            .into(imageView_condition_icon)
    }

    private fun updateCondition(temperature: Int){
        var advice = when(temperature){
            in 0..15 -> "Parece que no hace tanto calor, abrigate!"
            in 16..25 -> "la temperatura es agradable"
            in 25..60 ->"Hace calor, ten cuidado"
            else -> "La temperatura es muy baja, mejor quedate en tu casa"
        }
        textView_condition.text = advice
    }
}