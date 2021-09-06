package com.example.liverover.controller

import com.example.liverover.model.RoverPhotoResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * an interface for making requests to NASA's Mars rover photo service
 */
interface RoverService {

    /**
     * makes a request to the NASA API to load in new rover photos
     * @param roverName name of the rover to get photos from
     * @param earthDate the date you want photos from (yyyy-mm-dd format)
     * @param apiKey the api key registered with NASA's API
     * @return
     */
    @GET("{roverName}/photos")
    fun getCuriosityPhotoFromEarthDate(@Path(value="roverName") roverName: String, @Query("earth_date") earthDate: String, @Query("api_key") apiKey: String): Call<RoverPhotoResponse>
}