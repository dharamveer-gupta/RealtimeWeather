package com.dharamveer.realtimeweather

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dharamveer.realtimeweather.api.NetworkResponse
import com.dharamveer.realtimeweather.api.RetrofitInstance
import com.dharamveer.realtimeweather.api.WeatherModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch


/**
 * @Author: Dharamveer Gupta
 * @Date: 26 June, 2024 22:18.
 * @Email: dharamveer.gupt@gmail.com
 * @Package: com.dharamveer.realtimeweather
 */
class WeatherViewModel: ViewModel() {

    private val weatherAPI = RetrofitInstance.weatherAPI
    private val _weatherResult = MutableLiveData<NetworkResponse<WeatherModel>>()
    val weatherResult: LiveData<NetworkResponse<WeatherModel>>
        get() = _weatherResult


    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, e ->
        _weatherResult.value = NetworkResponse.Error("Failed to load data!")
    }

    fun getData(city: String) {
        _weatherResult.value = NetworkResponse.Loading
        viewModelScope.launch(coroutineExceptionHandler) {
            val response = weatherAPI.getWeather(apiKey = BuildConfig.API_KEY, query = city)
            _weatherResult.value = if (response.isSuccessful) {
                response.body()?.let {
                    NetworkResponse.Success(it)
                } ?: NetworkResponse.Error("Failed to load data!")
            } else {
                NetworkResponse.Error("Failed to load data!")
            }
        }
    }
    
}