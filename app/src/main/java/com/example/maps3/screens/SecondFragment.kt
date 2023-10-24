package com.example.maps3.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.maps3.MAIN
import com.example.maps3.R
import com.example.maps3.databinding.FragmentSecondBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class SecondFragment : Fragment(), OnMapReadyCallback {
    private lateinit var binding: FragmentSecondBinding
    private lateinit var googleMap: GoogleMap

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSecondBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_secondFragment_to_firstFragment)
        }
    }

    override fun onMapReady(gMap: GoogleMap) {
        googleMap = gMap
        val point = LatLng(48.7140885880836, 44.52821185507374)
        googleMap.addMarker(MarkerOptions().position(point).title("TEST"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(point, 15f))
        googleMap.isIndoorEnabled = true
        googleMap.isBuildingsEnabled = true
    }
}
