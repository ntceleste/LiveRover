package com.example.liverover

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.liverover.controller.RoverService
import com.example.liverover.model.RoverPhotoResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private val roverPhotoIdList = ArrayList<String>()
    private lateinit var roverPhotoRecyclerViewAdapter: RoverPhotoRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val recyclerView: RecyclerView = findViewById(R.id.rvRoverPhotos)
        roverPhotoRecyclerViewAdapter = RoverPhotoRecyclerViewAdapter(roverPhotoIdList)
        val layoutManager = LinearLayoutManager(applicationContext)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = roverPhotoRecyclerViewAdapter
        getRoverPhotos()
    }

    private fun getRoverPhotos(){
        val retrofit = Retrofit.Builder()
            .baseUrl(applicationContext.getString(R.string.base_url))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(RoverService::class.java)
        val call = service.getCuriosityPhotoFromEarthDate("2020-06-03", applicationContext.getString(R.string.api_key))

        call.enqueue(object : Callback<RoverPhotoResponse> {
            override fun onResponse(call: Call<RoverPhotoResponse>, response: Response<RoverPhotoResponse>) {
                if (response.code() == 200) {
                    Log.d("response recived" , response.body().toString())

                    response.body()?.photos?.forEach {
                        roverPhotoIdList.add(it.id.toString())
                    }
                    roverPhotoRecyclerViewAdapter.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<RoverPhotoResponse>, t: Throwable) {
                Log.e("response failure", t.localizedMessage.toString())
            }
        })
    }
}