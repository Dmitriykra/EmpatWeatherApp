package com.dk.empatweatherapp

import android.Manifest
import android.annotation.SuppressLint
import android.app.Dialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.location.LocationManagerCompat.isLocationEnabled
import androidx.lifecycle.lifecycleScope
import com.dk.empatweatherapp.constants.Constants
import com.dk.empatweatherapp.data.api.ApiService
import com.dk.empatweatherapp.data.api.RetrofitInstance
import com.dk.empatweatherapp.data.repositoriy.Repository
import com.dk.empatweatherapp.model.days.DayModel
import com.google.android.gms.location.*
import com.google.gson.Gson
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private lateinit var fusLocProCl: FusedLocationProviderClient
    private var progressDialog: Dialog? = null
    private lateinit var sharedPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPref = getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE)
        fusLocProCl = LocationServices.getFusedLocationProviderClient(this)

        if(!isLocationEnabled()){
            Toast.makeText(this, "Please, turn on your GPS", Toast.LENGTH_SHORT).show()
            //run request on GPS
            val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            startActivity(intent)
        } else {
            Dexter.withActivity(this)
                .withPermissions(
                    android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION
                ).withListener(object : MultiplePermissionsListener {
                    override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                        if(report!!.areAllPermissionsGranted()){
                            Log.d("TAG", "onPermissionsChecked: done")
                            requestLocationData()
                            Log.d("TAG", "onPermissionsChecked1: ")
                        }

                        if(report.isAnyPermissionPermanentlyDenied){
                            Toast.makeText(this@MainActivity, "You denied all permissions", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onPermissionRationaleShouldBeShown(
                        permissions: MutableList<PermissionRequest>?,
                        token: PermissionToken?
                    ) {
                        showAlertDialogForPermission()
                    }

                }).onSameThread()
                .check()
        }
    }

    @SuppressLint("SuspiciousIndentation")
    private fun requestLocationData(){

        val locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 1000)
            .setWaitForAccurateLocation(false)
            .setMinUpdateIntervalMillis(500)
            .setMaxUpdateDelayMillis(1000)
            .build()

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        )
        fusLocProCl.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper())
    }

    private val locationCallback = object  : LocationCallback(){

        override fun onLocationResult(locationResult: LocationResult) {
            Log.d("TAG", "onPermissionsChecked3: done")

            val lastLocation: Location? = locationResult.lastLocation
            val latitude = lastLocation?.latitude
            val longitude = lastLocation?.longitude

            Log.d("TAG", "latitude: $latitude and longitude $longitude")

            if (latitude != null && longitude != null) {

                //getDataFromRetro(latitude, longitude, 0)
            }
        }
    }

   /* private fun getDataFromRetro(latitude: Double, longitude: Double, cnt: Int){
        lifecycleScope.launch(Dispatchers.IO){
            val weatherDays = RetrofitInstance.api.getWeatherByDays(latitude, longitude, cnt, Constants.APP_ID)
            withContext(Dispatchers.Main){
                val listExchange = weatherDays.body()
                Log.d("TAG", "getDataFromRetro: ${listExchange}")

            }
        }
    }*/

    /*private fun getLocationWeatherDet(latitude: Double, longitude: Double) {
        if(Constants.isNetworkAvailable(this@MainActivity)){

            //Init of retrofit
            val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            //preparing the services
            val service: ApiService = retrofit
                .create(ApiService::class.java)

            //prepearing listCall
            val listCall: Call<WeatherResponse> = service.getWeather(
                latitude,
                longitude,
                1,
                Constants.APP_ID
            )

            Log.d("TAG", "getLocationWeatherDet: $listCall")

            listCall.enqueue(object: Callback<WeatherResponse> {
                override fun onResponse(
                    call: Call<WeatherResponse>,
                    response: Response<WeatherResponse>

                ) {
                    if(response.isSuccessful){

                        hideDialog()

                        val weatherList: WeatherResponse? = response.body()
                        val weatherResponseJsonString = Gson().toJson(weatherList)
                        val editor = sharedPref.edit()
                        editor.putString(Constants.RESP_DATA, weatherResponseJsonString)
                        editor.apply()
                        if (weatherList != null) {
                            //setupUi()
                        }
                        Log.i("TAG", "onResponse result: $weatherList")
                    } else {

                        hideDialog()

                        val rc = response.code()

                        Log.d("TAG", "onResponse: "+response.message())

                        when(rc){
                            400 ->{
                                Log.e("TAG", "Error 400")
                            } 404 ->{
                            Log.e("TAG", "Error 404")
                        } else ->{
                            Log.e("TAG", "Error  ${response.code()}")
                        }
                        }
                    }
                }

                override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {

                    hideDialog()

                    Log.e("TAG", "Big error $t")
                }

            })

        } else {
            Toast.makeText(this@MainActivity, "No internet is available", Toast.LENGTH_SHORT).show()

            hideDialog()
        }

    }*/

    private fun showAlertDialogForPermission(){
        AlertDialog.Builder(this@MainActivity)
            .setMessage("Turn on permission")
            .setPositiveButton("Go to settings") {_, _ ->
                try {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri = Uri.fromParts("package", packageName, null)
                    intent.data = uri
                    startActivity(intent)
                } catch (e: ActivityNotFoundException){
                    e.printStackTrace()

                }
            }.setNegativeButton("Cancel"){
                    dialog,
                    _->
                dialog.dismiss()
            }.show()
    }

    private fun isLocationEnabled():Boolean{
        //get access to the system location service
        val locationManager: LocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }

    private fun  showCustomDialog(){
        progressDialog = Dialog(this)
        progressDialog!!.show()
    }

    private fun hideDialog(){
        if(progressDialog != null){
            progressDialog!!.dismiss()
        }
    }
}