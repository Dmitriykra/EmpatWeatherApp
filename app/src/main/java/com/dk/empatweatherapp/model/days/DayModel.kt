package com.dk.empatweatherapp.model.days

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.Serializable
import java.lang.reflect.Type

data class DayModel(
    val city: City,
    val cnt: Int,
    val cod: String,
    val list: List<DaysList>,
    val message: Int
): Serializable



