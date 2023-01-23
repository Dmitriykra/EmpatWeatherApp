package com.dk.empatweatherapp.constants

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build

object Constants {
    const val APP_ID: String = "b193609ce3975a2cb599bde9fc539295"
    const val BASE_URL: String =  "https://api.openweathermap.org/data/"
    const val PREF_NAME: String = "weather"
    const val RESP_DATA: String = "resp_data"

    @SuppressLint("ObsoleteSdkInt")
    fun isNetworkAvailable(context: Context) : Boolean {
        val connectivityManager = context
            .getSystemService(Context.CONNECTIVITY_SERVICE)
                as ConnectivityManager

        //if version of phone up or equal 23
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){

            //if empty (network don`t exist) -> return false (?: its "if" statement)
            val network = connectivityManager.activeNetwork ?: return false

            //also check capability and return false if it null
            val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

            return when{
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            val networkInfo = connectivityManager.activeNetworkInfo
            return networkInfo != null && networkInfo.isConnectedOrConnecting
        }
    }
}