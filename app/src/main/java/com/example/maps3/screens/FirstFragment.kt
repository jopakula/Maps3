package com.example.maps3.screens

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.maps3.R
import com.example.maps3.adapter.ProductAdapter
import com.example.maps3.retrofit.ProductApi
import com.example.maps3.retrofit.Products
import com.google.android.gms.maps.model.LatLng
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FirstFragment : Fragment() {
    private lateinit var adapter: ProductAdapter
    private lateinit var rcView: RecyclerView
    private lateinit var textViewCoordinates: TextView
    private val coordinatesList = mutableListOf<LatLng>()
    private var products: Products? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.fragment_first, container, false)

        rcView = view.findViewById(R.id.rcView)
        adapter = ProductAdapter()
        rcView.layoutManager = LinearLayoutManager(requireContext())
        rcView.adapter = adapter

        textViewCoordinates = view.findViewById(R.id.textViewCoordinates) // Найдите TextView

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("http://89.108.99.89:3337")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val ProductApi = retrofit.create(ProductApi::class.java)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val productList: Products = ProductApi.getAllProducts()
                requireActivity().runOnUiThread {
                    adapter.submitList(productList.points)
                    // Создайте список координат и передайте его в SecondFragment
                    val coordinates = productList.points.map {
                        LatLng(
                            it.latitude.toDouble(),
                            it.longitude.toDouble(),
                        )
                    }

                    val secondFragment = parentFragmentManager.findFragmentByTag("secondFragment") as SecondFragment?
                    secondFragment?.addPoints(coordinates)

                    Log.d("FirstFragment", "Coordinates created: $coordinates")
                    val newSecondFragment = SecondFragment()
                    newSecondFragment.addPoints(coordinates)

                    // Преобразуйте список координат в текст и установите его в TextView
                    val coordinatesText = coordinates.joinToString("\n")
                    textViewCoordinates.text = coordinatesText

                    products = productList
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnNext = view.findViewById<Button>(R.id.btn_next)

        btnNext.setOnClickListener {
            products?.let { p ->
                findNavController().navigate(
                    FirstFragmentDirections.actionFirstFragmentToSecondFragment(
                        Gson().toJson(p, Products::class.java),
                    ),
                )
            }
        }
    }
}
