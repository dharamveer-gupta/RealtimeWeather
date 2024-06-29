package com.dharamveer.realtimeweather.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


/**
 * @Author: Dharamveer Gupta
 * @Date: 26 June, 2024 22:58.
 * @Email: dharamveer.gupt@gmail.com
 * @Package: com.dharamveer.realtimeweather.api
 */
interface WeatherAPI {

    // ?key=7a159ec41ff3477284a172148242606&q=London&aqi=no

    @GET("/v1/current.json")
    suspend fun getWeather(
        @Query("key") apiKey: String,
        @Query("q") query: String,
        @Query("aqi") showAQI: String = "no"
    ) : Response<WeatherModel>


}