package com.dharamveer.realtimeweather.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


/**
 * @Author: Dharamveer Gupta
 * @Date: 26 June, 2024 22:44.
 * @Email: dharamveer.gupt@gmail.com
 * @Package: com.dharamveer.realtimeweather.api
 */
object RetrofitInstance {

    private const val BASE_URL = "https://api.weatherapi.com"
    private fun getInstance(): Retrofit = Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build()

    val weatherAPI: WeatherAPI = getInstance().create(WeatherAPI::class.java)
}