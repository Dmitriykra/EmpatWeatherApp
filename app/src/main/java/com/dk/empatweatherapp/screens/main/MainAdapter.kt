package com.dk.empatweatherapp.screens.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dk.empatweatherapp.databinding.WeatherCardBinding
import com.dk.empatweatherapp.model.days.*

class MainAdapter: RecyclerView.Adapter<MainAdapter.MainViewHolder>() {

    var listDaysStart = emptyList<DayModel>()

    inner class MainViewHolder(private val itemBinding: WeatherCardBinding)
        :RecyclerView.ViewHolder(itemBinding.root){
            fun bindItem(dayModel: DayModel){
                for(a in dayModel.list.indices){
                    for(i in dayModel.list[a].weather.indices) {
                        itemBinding.dayOfWeek.text = dayModel.list[a].dt_txt
                        itemBinding.weatherDescription.text = dayModel.list[a].weather[i].description
                        itemBinding.tempNumb.text = dayModel.list[a].main.temp.toString()
                        itemBinding.airHumidityValTv.text = dayModel.list[a].main.humidity.toString()
                        itemBinding.speedWindValTv.text = dayModel.list[a].wind.speed.toString()
                    }
                }
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(WeatherCardBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun getItemCount(): Int {
        return listDaysStart.size

    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val item = listDaysStart[position]
        holder.bindItem(item)
    }

    fun setList(list: DayModel){
        listDaysStart = listOf(list)
        notifyDataSetChanged()
    }
}