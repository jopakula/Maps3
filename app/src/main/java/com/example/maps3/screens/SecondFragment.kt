package com.example.maps3.screens

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.example.maps3.R
import com.example.maps3.databinding.FragmentSecondBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class SecondFragment : Fragment() {

    private lateinit var googleMap: GoogleMap
    private val points = mutableListOf<LatLng>()
    private lateinit var coordinatesTextView: TextView
    private var coordinates: List<LatLng>? = null

    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_second, container, false)

        coordinatesTextView = view.findViewById(R.id.coordinatesTextView)

        // Инициализация GoogleMap при готовности фрагмента карты
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment

        mapFragment.getMapAsync { map ->
            googleMap = map
            coordinates = points
            // Обработчик нажатия на кнопку "Set Marker"
            val btnSetMarker = view.findViewById<Button>(R.id.btn_set_marker)

            btnSetMarker.setOnClickListener {
                // Проверяем, что GoogleMap инициализирован
                googleMap?.let { map ->
                    // Очищаем все существующие маркеры
                    map.clear()

                    // Проходим по списку точек и добавляем маркеры на карту
                    for (point in points) {
                        val marker = MarkerOptions().position(point).title("Custom Marker")
                        map.addMarker(marker)
                    }

                    // Обновляем текстовое поле с координатами
                    val coordinatesText = "Coordinates: ${points.joinToString(", ")}"
                    coordinatesTextView.text = coordinatesText
                }
            }
        }

        // Обработчик нажатия на кнопку "Back"
        val btnBack = view.findViewById<Button>(R.id.btn_back)

        btnBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        return view
    }

    // Добавление новых точек в список
    fun addPoints(newPoints: List<LatLng>) {
        points.addAll(newPoints)
        coordinates = points
        Log.d("SecondFragment", "Received coordinates: $coordinates")

    fun getPoints(): List<LatLng> {
        return points
    }
}
}




