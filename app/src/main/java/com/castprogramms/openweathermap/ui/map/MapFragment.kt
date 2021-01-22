package com.castprogramms.openweathermap.ui.map

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.MainThread
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import com.castprogramms.openweathermap.R
import com.castprogramms.openweathermap.WeatherApplication
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton


class MapFragment : Fragment() {

    private val mapViewModel: MapViewModel by viewModels()
    private lateinit var mapView: MapView
    private lateinit var googleMap: GoogleMap

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_map, container, false)
        val fab = view.findViewById<FloatingActionButton>(R.id.fab)

        mapViewModel.mutableLiveDataTracking.observe(viewLifecycleOwner, {
            if (it) {
                fab.setImageDrawable(resources.getDrawable(R.drawable.location_off))
                fab.setOnClickListener {
                    mapViewModel.stopTracking()
                }
            } else {
                fab.setImageDrawable(resources.getDrawable(R.drawable.add_location))
                fab.setOnClickListener {
                    mapViewModel.startTracking()
                }
            }
        })
        mapView = view.findViewById(R.id.mapView)
        mapView.onCreate(savedInstanceState)
        mapView.onResume()
        mapView.getMapAsync { map ->
            googleMap = map
            googleMap.isBuildingsEnabled = true
            googleMap.isIndoorEnabled = true
            if (ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ){}
            if (mapViewModel.dataLiveData.value.isNullOrEmpty())
            mapViewModel.gpsTracker.fusedLocationClient.lastLocation.addOnSuccessListener {
                googleMap.addMarker(
                    MarkerOptions().position(LatLng(it.latitude, it.longitude))
                )
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(
                    CameraPosition.fromLatLngZoom(LatLng(it.latitude, it.longitude), 18f)))
            }
            mapViewModel.dataLiveData.observe(viewLifecycleOwner, {
                try {
                    if (it.isEmpty() && mapViewModel.mutableLiveDataTracking.value!!) {
                        googleMap.clear()
                        mapViewModel.gpsTracker.fusedLocationClient.lastLocation.addOnSuccessListener {
                            googleMap.addMarker(
                                MarkerOptions().position(LatLng(it.latitude, it.longitude))
                            )
                            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(
                                CameraPosition.fromLatLngZoom(LatLng(it.latitude, it.longitude), 18f)))
                        }                    }
                    googleMap.addPolyline(
                        PolylineOptions().addAll(
                            mapViewModel.covertToLatLngList(
                                it
                            )
                        )
                    )
                    googleMap.setMinZoomPreference(0.01f)
                    googleMap.animateCamera(
                        CameraUpdateFactory.newCameraPosition(
                            CameraPosition(
                                LatLng(
                                    it.last().latitude, it.last().longitude
                                ),
                                18f, 0f, 0f
                            )
                        )
                    )
                } catch (e: Exception) {
                    Log.e("Test", e.message.toString())
                }
            })
        }
        return view
    }
}