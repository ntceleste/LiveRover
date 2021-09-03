package com.example.liverover

import android.os.Bundle
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.example.liverover.controller.RoverService
import com.example.liverover.databinding.ActivityScrollingBinding
import com.example.liverover.model.RoverPhotoResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ScrollingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityScrollingBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityScrollingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(findViewById(R.id.toolbar))
        binding.toolbarLayout.title = title
        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
        getRoverPhotos()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_scrolling, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    internal fun getRoverPhotos(){
        val retrofit = Retrofit.Builder()
            .baseUrl(BaseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(RoverService::class.java)
        val call = service.getCuriosityPhotoFromEarthDate("2020-6-3", ApiKey)

        call.enqueue(object : Callback<RoverPhotoResponse> {
            override fun onResponse(call: Call<RoverPhotoResponse>, response: Response<RoverPhotoResponse>) {
                if (response.code() == 200) {
                    Log.d("response recived" , response.body().toString())
                }
            }

            override fun onFailure(call: Call<RoverPhotoResponse>, t: Throwable) {
                Log.e("response failure", t.localizedMessage.toString())
            }
        })
    }
    companion object {
        var BaseUrl = "https://api.nasa.gov/mars-photos/api/v1/rovers/"
        var ApiKey = "DEMO_KEY"
    }
}