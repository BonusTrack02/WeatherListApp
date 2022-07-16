package com.example.weatherlistapp

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weatherlistapp.databinding.RecyclerItemBinding
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.Date
import kotlin.math.roundToInt

class WeatherListAdapter(val context : Context, var list : MutableList<WeatherList>) : RecyclerView.Adapter<WeatherListAdapter.VH>() {
    inner class VH(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val binding : RecyclerItemBinding = RecyclerItemBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val itemView = LayoutInflater.from(context).inflate(R.layout.recycler_item, parent, false)

        return VH(itemView)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val newList : WeatherList = makeList(list)[position]

        holder.binding.textTempMax.text = "Max: ${changeKelvinToCelsius(newList.main.temp_max).roundToInt()}°C"
        holder.binding.textTempMin.text = "Min: ${changeKelvinToCelsius(newList.main.temp_min).roundToInt()}°C"
        holder.binding.textRecyclerDate.text = newList.dt_txt
        holder.binding.textWeather.text = newList.weather[0].main

        if (newList.weather[0].main.contains("Rain")) Glide.with(context).load(R.drawable.ic_rain).into(holder.binding.imgIcon)
        if (newList.weather[0].main.contains("Cloud")) Glide.with(context).load(R.drawable.ic_cloud).into(holder.binding.imgIcon)
        if (newList.weather[0].main.contains("Clear")) Glide.with(context).load(R.drawable.ic_clear).into(holder.binding.imgIcon)
        if (newList.weather[0].main.contains("Snow")) Glide.with(context).load(R.drawable.ic_snow).into(holder.binding.imgIcon)
    }

    override fun getItemCount(): Int = makeList(list).size

    private fun makeList(list : MutableList<WeatherList>) : MutableList<WeatherList> {
        var madeList = mutableListOf<WeatherList>()
        var dateText : String = ""

        list.forEachIndexed { index, it ->
            if (dateText == "") {
                dateText = it.dt_txt.split(" ")[0]
                it.dt_txt = it.dt_txt.split(" ")[0]
                Log.i("Date", it.dt_txt)
                madeList.add(it)
            } else if (dateText == it.dt_txt.split(" ")[0]) {
                return@forEachIndexed
            } else if (dateText != it.dt_txt.split(" ")[0]) {
                dateText = it.dt_txt.split(" ")[0]
                it.dt_txt = it.dt_txt.split(" ")[0]
                madeList.add(it)
            }
        }

        return madeList
    }

    private fun changeKelvinToCelsius(Kelvin : Double) : Double = Kelvin - 273.15
}