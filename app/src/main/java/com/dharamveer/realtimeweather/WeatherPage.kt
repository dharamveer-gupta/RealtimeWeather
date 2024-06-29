package com.dharamveer.realtimeweather

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.dharamveer.realtimeweather.api.NetworkResponse
import com.dharamveer.realtimeweather.api.WeatherModel


/**
 * @Author: Dharamveer Gupta
 * @Date: 26 June, 2024 22:05.
 * @Email: dharamveer.gupt@gmail.com
 * @Package: com.dharamveer.realtimeweather
 */

@Composable
fun WeatherPage(modifier: Modifier = Modifier, viewModel: WeatherViewModel? = null) {

    var city by remember {
        mutableStateOf("")
    }

    val weatherData = viewModel?.weatherResult?.observeAsState()
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            OutlinedTextField(modifier = Modifier.weight(1f), value = city, onValueChange = {
                city = it
            }, label = {
                Text(text = "Search for any location")
            })
            IconButton(onClick = {
                viewModel?.getData(city = city)
                keyboardController?.hide()
            }) {
                Icon(imageVector = Icons.Default.Search, contentDescription = "Search for any location")
            }
        }
        when (val result = weatherData?.value) {
            is NetworkResponse.Error -> {
                Text(text = result.message)
            }

            NetworkResponse.Loading -> {
                CircularProgressIndicator()
            }

            is NetworkResponse.Success -> {
                WeatherDetails(result.data)
            }

            null -> {}
        }
    }


}

@Composable
fun WeatherDetails(data: WeatherModel) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start, verticalAlignment = Alignment.Bottom
        ) {
            Icon(imageVector = Icons.Default.LocationOn, contentDescription = "Location Icon", modifier = Modifier.size(40.dp))
            Text(text = data.location.name, fontSize = 30.sp)
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = data.location.country, fontSize = 18.sp, color = Color.Gray)
        }
        
        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.Top
        ) {
            Text(text = data.current.tempC, fontSize = 64.sp, fontWeight = FontWeight.Bold)
            Text(text = "ºC", fontSize = 24.sp, fontWeight = FontWeight.Normal)
        }

        AsyncImage(modifier = Modifier.size(164.dp), model = "https:${data.current.condition.icon}".replace("64x64", "128x128"), contentDescription = "Weather Icon" )

        Text(text = data.current.condition.text, fontSize = 20.sp, textAlign = TextAlign.Center, color = Color.Gray)

        Spacer(modifier = Modifier.height(16.dp))

        Card {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    WeatherExtraInfo(key = "Humidity", value = data.current.humidity)
                    WeatherExtraInfo(key = "Wind Speed", value = "${data.current.windKph} km/h")
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    WeatherExtraInfo(key = "UV", value = data.current.uv)
                    WeatherExtraInfo(key = "Participation", value = "${data.current.precipMm} mm")
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    WeatherExtraInfo(key = "Local Time", value = data.location.localtime.split(" ")[1])
                    WeatherExtraInfo(key = "Local Date", value = data.location.localtime.split(" ")[0])
                }
            }
        }
        
    }
}

@Composable
fun WeatherExtraInfo(key: String, value: String) {
    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = value, fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Text(text = key, fontWeight = FontWeight.Bold, color = Color.Gray)
    }
}