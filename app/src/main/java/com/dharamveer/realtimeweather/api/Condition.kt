package com.dharamveer.realtimeweather.api


import com.google.gson.annotations.SerializedName

data class Condition(
    @SerializedName("code")
    val code: String,
    @SerializedName("icon")
    val icon: String,
    @SerializedName("text")
    val text: String
)