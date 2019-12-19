package com.nlambertucci.weatherappmvvm.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.nlambertucci.weatherappmvvm.model.response.CurrentWeatherResponse
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


const val API_KEY= "c077d2f8cf43e572a58cef5ce65a12d0"

//Api call example: http://api.weatherstack.com/current?access_key=c077d2f8cf43e572a58cef5ce65a12d0&query=New%20York

interface ApiWeatherService {

    @GET("current?")
    fun getCurrentWeather(
        @Query("query") location: String,
        @Query("lang") languageCode: String ="en"
        /**
         * Deferred comes from coroutines and help to wait until the server send a response
         */
    ):Deferred<CurrentWeatherResponse>

    companion object{
        operator fun invoke(
            connectivityInterceptor: ConnectivityInterceptor
        ): ApiWeatherService {
            val requestInterceptor = Interceptor{chain ->
                val url = chain.request()
                    .url()
                    .newBuilder()
                    .addQueryParameter("access_key",
                        API_KEY
                    ).build()

                val request = chain.request()
                    .newBuilder()
                    .url(url)
                    .build()

                return@Interceptor chain.proceed(request)

            }
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(requestInterceptor)
                .addInterceptor(connectivityInterceptor)
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("http://api.weatherstack.com/")
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiWeatherService::class.java)
        }
    }
}