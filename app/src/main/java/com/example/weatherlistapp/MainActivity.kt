package com.example.weatherlistapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.weatherlistapp.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    val apiKey = "0bc6dabc19a8f77c255642ced447133f"

    val binding : ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        requestWeather("Seoul")
        requestWeather("London")
        requestWeather("Chicago")
    }

    private fun requestWeather(cityName : String) {
        val retrofit = RetrofitHelper.getInstance("https://api.openweathermap.org")
        retrofit.create(RetrofitService::class.java)
            .getWeatherJson(cityName, apiKey)
            .enqueue(object : Callback<WeatherResponseItem> {
                override fun onResponse(
                    call: Call<WeatherResponseItem>,
                    response: Response<WeatherResponseItem>
                ) {
                    var weatherResponse : WeatherResponseItem? = response.body()

                    if (weatherResponse == null) Toast.makeText(
                        this@MainActivity,
                        "데이터가 준비되지 않았습니다.",
                        Toast.LENGTH_SHORT
                    ).show()

                    when (cityName) {
                        "Seoul" -> binding.recyclerSeoul.adapter = WeatherListAdapter(this@MainActivity, weatherResponse!!.list)
                        "London" -> binding.recyclerLondon.adapter = WeatherListAdapter(this@MainActivity, weatherResponse!!.list)
                        "Chicago" -> binding.recyclerChicago.adapter = WeatherListAdapter(this@MainActivity, weatherResponse!!.list)
                    }

                    //JSON형식으로 제대로 파싱되는지 먼저 확인
                    //AlertDialog.Builder(this@MainActivity).setMessage(weatherResponse?.list!!.size.toString()).show()
                }

                override fun onFailure(call: Call<WeatherResponseItem>, t: Throwable) {
                    Log.i("recycler error", "${t.message}")
                }
            })

        //String 형식으로 데이터가 수신되는지 확인
//            .getWeatherToString(cityName, apiKey)
//            .enqueue(object : Callback<String> {
//                override fun onResponse(call: Call<String>, response: Response<String>) {
//                    var s = response.body()
//                    AlertDialog.Builder(this@MainActivity).setMessage(s.toString()).create().show()
//                }
//
//                override fun onFailure(call: Call<String>, t: Throwable) {
//                    Toast.makeText(this@MainActivity, "서버 오류가 있습니다. 다시 뒤에 시도해주세요.", Toast.LENGTH_SHORT).show()
//                }
//            })
    }
}