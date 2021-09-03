package com.example.liverover.controller

import com.example.liverover.model.RoverPhotoResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RoverService {
    @GET("curiosity/photos")
    fun getCuriosityPhotoFromEarthDate(@Query("earth_date") earthDate: String, @Query("api_key") apiKey: String): Call<RoverPhotoResponse>
}