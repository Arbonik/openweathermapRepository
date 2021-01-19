package com.castprogramms.openweathermap.ui.map

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.castprogramms.openweathermap.R
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.lang.Exception

class MapFragment : Fragment() {

    private lateinit var mapViewModel: MapViewModel
    private lateinit var mapView: MapView
    private lateinit var googleMap: GoogleMap

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mapViewModel = ViewModelProvider(this).get(MapViewModel::class.java)
        mapViewModel.updateLocationData()
        val view = inflater.inflate(R.layout.fragment_map, container, false)
        val fab = view.findViewById<FloatingActionButton>(R.id.fab)
        if (mapViewModel.getIsFirstLaunchFrag())
            mapViewModel.setIsTracking(true, requireContext())

        mapViewModel.mutableLiveDataTracking.observe(viewLifecycleOwner, {
            if (it)
                fab.setImageDrawable(resources.getDrawable(R.drawable.location_off))
            else
                fab.setImageDrawable(resources.getDrawable(R.drawable.add_location))
        })
        fab.setOnClickListener {
            mapViewModel.setIsTracking(!mapViewModel.isTracking, requireContext())
        }
        mapView = view.findViewById(R.id.mapView)
        mapView.onCreate(savedInstanceState)
        mapView.onResume()
        mapView.getMapAsync { map ->
            googleMap = map
            mapViewModel.mutableLiveLocations.observe(viewLifecycleOwner, {
                try {
                    mapViewModel.mutableLiveDataThisPosition.observe(viewLifecycleOwner, {
                        googleMap.addMarker(
                            MarkerOptions().position(
                                LatLng(
                                    it.latitude,
                                    it.longitude
                                )
                            )
                        )
                        googleMap.moveCamera(
                            CameraUpdateFactory.newCameraPosition(
                                CameraPosition(
                                    LatLng(it.latitude, it.longitude),
                                    18f, 0f, 0f
                                )
                            )
                        )
                    })
                    if (it.isEmpty())
                        googleMap.clear()
                    it.forEach {
                        googleMap.setMinZoomPreference(0.01f)
                        googleMap.moveCamera(
                            CameraUpdateFactory.newCameraPosition(
                                CameraPosition(
                                    LatLng(it.latitude, it.longitude),
                                    18f, 0f, 0f
                                )
                            )
                        )
                    }
                    googleMap.addPolyline(PolylineOptions().addAll(mapViewModel.covertToLatLngList(it)))
                } catch (e: Exception) {
                }
            })
        }
        return view
    }


}