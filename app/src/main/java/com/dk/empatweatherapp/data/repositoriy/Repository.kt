package com.dk.empatweatherapp.data.repositoriy

import com.dk.empatweatherapp.MainActivity
import com.dk.empatweatherapp.constants.Constants
import com.dk.empatweatherapp.data.api.RetrofitInstance
import com.dk.empatweatherapp.model.days.DayModel
import com.dk.empatweatherapp.model.days.DaysList
import retrofit2.Response

class Repository() {

    suspend fun getWeatherDays(latitude: Double, longitude: Double, cnt: Int): Response<DayModel>{
        return RetrofitInstance.api
            .getWeatherByDays(
                latitude,
                longitude,
                cnt,
                Constants.APP_ID)
    }
}