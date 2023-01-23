package com.dk.empatweatherapp.data.api

import com.dk.empatweatherapp.model.days.DayModel
import com.dk.empatweatherapp.model.days.DaysList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET ("2.5/forecast")
    suspend fun getWeatherByDays(
        @Query("lat")
        lat: Double,

        @Query("lon")
        lon: Double,

        @Query("cnt")
        cnt: Int,

        @Query("appid")
        appid: String?
    ): Response<DayModel>
}