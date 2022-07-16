package com.example.weatherlistapp

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitService {
    @GET("data/2.5/forecast")
    fun getWeatherJson(@Query("q") cityName : String, @Query("appid") apiKey : String) : Call<WeatherResponseItem>

    @GET("data/2.5/forecast")
    fun getWeatherToString(@Query("q") cityName : String, @Query("appid") apiKey : String) : Call<String>
}