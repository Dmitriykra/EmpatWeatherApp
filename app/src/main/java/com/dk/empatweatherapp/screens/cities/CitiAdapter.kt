package com.dk.empatweatherapp.screens.cities

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dk.empatweatherapp.databinding.CitiCardBinding
import com.dk.empatweatherapp.model.days.DayModel

class CitiAdapter: RecyclerView.Adapter<CitiAdapter.CitiViewHolder>() {
    private val citiList = emptyList<DayModel>()

    inner class CitiViewHolder(private val binding: CitiCardBinding)
        :RecyclerView.ViewHolder(binding.root){
            @SuppressLint("SuspiciousIndentation")
            fun bindItem(dayModel: DayModel){
                //TODO сохранить список городов и вівести его тут
                binding.citiNameTv.text = dayModel.city.name
                //получаю последние данніе за єтот день в конкретном гроде
                    binding.tempTv.text = dayModel.list[0].main.temp.toString()
                    for(i in dayModel.list[0].weather.indices)
                    binding.cityScyType.text = dayModel.list[0].weather[i].description
            }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CitiAdapter.CitiViewHolder {
        return CitiViewHolder(CitiCardBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun getItemCount(): Int {
        return citiList.size
    }

    override fun onBindViewHolder(holder: CitiAdapter.CitiViewHolder, position: Int) {
        val item = citiList[position]
        holder.bindItem(item)
    }
}