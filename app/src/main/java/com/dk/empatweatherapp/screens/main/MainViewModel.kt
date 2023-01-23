package com.dk.empatweatherapp.screens.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dk.empatweatherapp.data.repositoriy.Repository
import com.dk.empatweatherapp.model.days.DayModel
import com.dk.empatweatherapp.model.days.DaysList
import kotlinx.coroutines.launch
import retrofit2.Response

class MainViewModel: ViewModel() {
    var repo = Repository()
    val myWeatherList:  MutableLiveData<Response<DayModel>> = MutableLiveData()
    fun getWeatherData(){
        viewModelScope.launch {
            myWeatherList.value = repo.getWeatherDays(31.0, -122.0, 2)
            Log.d("TAG", "getWeatherData: ${myWeatherList.value}")
        }
    }
}