package com.application.weather.repository

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Looper
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.core.app.ActivityCompat
import androidx.core.location.component1
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.location.*


class LocationRepository(private val context: Context) {

    private val _locationLiveData = MutableLiveData<Location?>()
    val locationLiveData: LiveData<Location?>
        get() = _locationLiveData

    private var fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    fun getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        getLocationRequest()
        fusedLocationClient.lastLocation.addOnSuccessListener {
            _locationLiveData.value = it
        }
    }

    private fun startLocationRequest(
        locationRequest: LocationRequest,
        locationCallback: LocationCallback
    ) {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )
    }

    private fun getLocationRequest() {
        val locationRequest = LocationRequest.create()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 1000
        locationRequest.fastestInterval = 5000
        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(@NonNull locationResult: LocationResult) {
                _locationLiveData.value = locationResult.locations[0]
                fusedLocationClient.removeLocationUpdates(this)
            }
        }
        startLocationRequest(locationRequest, locationCallback)
    }

}
