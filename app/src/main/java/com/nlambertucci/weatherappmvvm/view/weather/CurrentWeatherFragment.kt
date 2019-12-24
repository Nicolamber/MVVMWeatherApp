package com.nlambertucci.weatherappmvvm.view.weather

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer

import com.nlambertucci.weatherappmvvm.R
import com.nlambertucci.weatherappmvvm.utils.GlideApp
import com.nlambertucci.weatherappmvvm.utils.ScopedFragment
import com.nlambertucci.weatherappmvvm.viewmodel.CurrentWeatherViewModel
import com.nlambertucci.weatherappmvvm.viewmodel.CurrentWeatherViewModelFactory
import kotlinx.android.synthetic.main.current_weather_fragment.*
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance
import java.math.BigDecimal
import java.math.RoundingMode

const val METRIC ="Metric"
const val IMPERIAL ="Imperial"
const val MPH = 0.621371
const val MILLES = 1.6
const val INCH = 0.0393701
class CurrentWeatherFragment : ScopedFragment(), KodeinAware {

    override val kodein by closestKodein()
    private val viewModelFactory: CurrentWeatherViewModelFactory by instance()
    private var temperatureF: Double = 0.0
    private var feelsLikeF: Double = 0.0
    private var visibilityF: Double = 0.0
    private var precipitationF:Double = 0.0
    private var windSpeedF: Double = 0.0
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
    }

    private fun bindUI() = launch{

        val currentWeather = viewModel.weather.await()
        val weatherLocation = viewModel.weatherLocation.await()

        weatherLocation.observe(this@CurrentWeatherFragment,Observer{location ->
            if(location == null) return@Observer
            updateLocation(location.name)

        })

        currentWeather.observe(this@CurrentWeatherFragment, Observer {
            if(it == null) return@Observer
            group_loading.visibility = View.GONE
            //updateLocation("Mendoza")
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

        if(getMetric().equals(IMPERIAL)){
            temperatureF = (temperature * 1.8)+ 32
            val bigDecimal = BigDecimal(temperatureF).setScale(2,RoundingMode.HALF_EVEN)
            feelsLikeF = (feelsLike * 1.8)+32
            textView_temperature.text = "$bigDecimal $unitAbreviation"
            textView_feels_like_temperature.text = "Sensación térmica: $feelsLikeF $unitAbreviation"
        }else{
            textView_temperature.text = "$temperature$unitAbreviation"
            textView_feels_like_temperature.text = "Sensación térmica$feelsLike$unitAbreviation"
        }

        if( temperature > 25){
            updateImage("https://cdn3.iconfinder.com/data/icons/disaster-and-weather-conditions/48/8-512.png")
        }else{
            updateImage("https://cdn2.iconfinder.com/data/icons/weather-colored-icons/47/weather_70-512.png")
        }
    }

    private fun updatePrecipitation(precipitationVolume: Int) {
        val unitAbreviation = chooseLocalizedUnitAbbreviation("mm", "in")
        if (getMetric().equals(IMPERIAL)) {
            precipitationF = precipitationVolume * INCH
            textView_precipitation.text = "Precipitaciones: $precipitationF $unitAbreviation"
        } else {
            textView_precipitation.text = "Precipitaciones: $precipitationVolume $unitAbreviation"
        }
    }

    private fun updateWind(windDirection: String, windSpeed: Int){
        val unitAbbreviation = chooseLocalizedUnitAbbreviation("Km/h","mph")
        if(getMetric().equals(IMPERIAL)){
            windSpeedF = MPH * windSpeed
            textView_wind.text = "Viento: $windDirection, $windSpeedF $unitAbbreviation"
        }else{
            textView_wind.text = "Viento: $windDirection, $windSpeed $unitAbbreviation"
        }

    }

    private fun updateVisibility(visibility: Int) {
        val unitAbbreviation = chooseLocalizedUnitAbbreviation("km", "mi")
        if (getMetric().equals(IMPERIAL)) {
            visibilityF = visibility * MILLES
            textView_visibility.text = "Visibilidad: $visibilityF $unitAbbreviation"
        } else {
            textView_visibility.text = "Visibilidad: $visibility $unitAbbreviation"
        }
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

    private fun getMetric(): String{
        return if(chooseLocalizedUnitAbbreviation("Km/h","mph").equals("mph")) IMPERIAL else METRIC
    }
}