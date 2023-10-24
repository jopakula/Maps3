package com.example.maps3.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.maps3.MAIN
import com.example.maps3.R
import com.example.maps3.adapter.ProductAdapter
import com.example.maps3.databinding.FragmentFirstBinding
import com.example.maps3.retrofit.ProductApi
import com.example.maps3.retrofit.Products
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Response

class FirstFragment : Fragment() {
    private lateinit var adapter: ProductAdapter
    private lateinit var rcView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_first, container, false)

        rcView = view.findViewById(R.id.rcView)
        adapter = ProductAdapter()

        rcView.layoutManager = LinearLayoutManager(requireContext())
        rcView.adapter = adapter

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
                }
            } catch (e: Exception) {
                e.printStackTrace()
                // Обработайте ошибку
            }
        }

        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Найдите кнопку по идентификатору
        val btnNext = view.findViewById<Button>(R.id.btn_next)

        // Установите обработчик клика на кнопку
        btnNext.setOnClickListener {
            findNavController().navigate(R.id.action_firstFragment_to_secondFragment)
        }
    }
}

//class FirstFragment : Fragment() {
//
//    lateinit var binding: FragmentFirstBinding
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        binding = FragmentFirstBinding.inflate(layoutInflater, container, false)
//        return binding.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        binding.btnNext.setOnClickListener {
//            MAIN.navController.navigate(R.id.action_firstFragment_to_secondFragment)
//        }
//    }
//}