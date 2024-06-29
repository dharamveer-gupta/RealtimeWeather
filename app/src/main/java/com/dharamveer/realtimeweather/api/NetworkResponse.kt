package com.dharamveer.realtimeweather.api


/**
 * @Author: Dharamveer Gupta
 * @Date: 28 June, 2024 22:36.
 * @Email: dharamveer.gupt@gmail.com
 * @Package: com.dharamveer.realtimeweather.api
 */

// here T refers to WeatherModel
sealed class NetworkResponse<out T> {
    data class Success<out T>(val data: T): NetworkResponse<T>()
    data class Error(val message: String): NetworkResponse<Nothing>()
    data object Loading: NetworkResponse<Nothing>()
}