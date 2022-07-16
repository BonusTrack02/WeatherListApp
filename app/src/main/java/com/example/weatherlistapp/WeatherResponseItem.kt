package com.example.weatherlistapp

data class WeatherResponseItem(var cod : String, var message : Int, var cnt : Int, var list : MutableList<WeatherList>)

data class WeatherList(var main : MainData, var weather : MutableList<WeatherData>, var dt_txt : String)

data class MainData(var temp_min : Double, var temp_max : Double)

data class WeatherData(var main : String, var description: String)