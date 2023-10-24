package com.example.maps3.retrofit

import retrofit2.http.GET

interface ProductApi {
    @GET("points")
    suspend fun getAllProducts(): Products
}