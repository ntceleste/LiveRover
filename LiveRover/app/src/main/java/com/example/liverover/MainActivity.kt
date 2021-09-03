package com.example.liverover

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.DatePicker
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.liverover.controller.RoverService
import com.example.liverover.model.RoverPhotoResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.Calendar.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    private val roverPhotoIdList = ArrayList<String>()
    private lateinit var roverPhotoRecyclerViewAdapter: RoverPhotoRecyclerViewAdapter
    private var date_picker: Button? = null
    var cal = getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        date_picker = findViewById(R.id.rover_date_picker)
        val dateSetListener = object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(view: DatePicker, year: Int, monthOfYear: Int,
                                   dayOfMonth: Int) {
                cal.set(YEAR, year)
                cal.set(MONTH, monthOfYear)
                cal.set(DAY_OF_MONTH, dayOfMonth)
                setRoverDate()
            }
        }

        date_picker!!.setOnClickListener {
            DatePickerDialog(
                this@MainActivity,
                dateSetListener,
                cal.get(YEAR),
                cal.get(MONTH),
                cal.get(DAY_OF_MONTH)
            ).show()
        }

        val recyclerView: RecyclerView = findViewById(R.id.rvRoverPhotos)
        roverPhotoRecyclerViewAdapter = RoverPhotoRecyclerViewAdapter(roverPhotoIdList)
        val layoutManager = LinearLayoutManager(applicationContext)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = roverPhotoRecyclerViewAdapter
    }

    private fun getRoverPhotos(earthDate: String){
        val retrofit = Retrofit.Builder()
            .baseUrl(this.getString(R.string.base_url))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(RoverService::class.java)
        val call = service.getCuriosityPhotoFromEarthDate(earthDate, this.getString(R.string.api_key))

        call.enqueue(object : Callback<RoverPhotoResponse> {
            override fun onResponse(call: Call<RoverPhotoResponse>, response: Response<RoverPhotoResponse>) {
                if (response.code() == 200) {
                    Log.d("response recived" , response.body().toString())

                    roverPhotoIdList.clear()
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

    private fun setRoverDate(){
        val year = cal.get(YEAR)
        val month = cal.get(MONTH) + 1
        val day = cal.get(DAY_OF_MONTH)
        val earthDate = "$year-$month-$day"

        Log.d("date picked", earthDate)
        getRoverPhotos(earthDate)
    }
}