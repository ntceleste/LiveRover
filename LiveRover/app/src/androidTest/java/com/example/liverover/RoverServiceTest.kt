package com.example.liverover

import com.example.liverover.controller.RoverService
import org.junit.Test


class RoverServiceTest {

    @Test
    fun canGetRoverPhotos(){
        val roverService = RoverService.create("https://api.nasa.gov/mars-photos/api/v1/rovers/")
        val response = roverService.getCuriosityPhotoFromEarthDate("curiosity", "2020-10-20", "DEMO_KEY").execute()
        //I allow a 429 in this test because the DEMO_KEY gets rate limited. I assume that if we get a rate_limit response, it was a valid response
        assert(response.code() == 200 || response.code() == 429)
    }
}