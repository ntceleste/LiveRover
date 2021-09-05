package com.example.liverover

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.DatePicker
import android.widget.Spinner
import androidx.appcompat.widget.AppCompatSpinner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.liverover.controller.RoverService
import com.example.liverover.model.Photo
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
    private val roverPhotoIdList = ArrayList<Photo>()
    private lateinit var roverPhotoRecyclerViewAdapter: RoverPhotoRecyclerViewAdapter
    private var datePicker: Button? = null
    private var roverName: String = ""
    private var earthDate: String = ""
    var cal: Calendar = getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupRoverSpinner()
        setupDatePicker()
        setupRecyclerView()
    }

    private fun getRoverPhotos(){
        val retrofit = Retrofit.Builder()
            .baseUrl(this.getString(R.string.base_url))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(RoverService::class.java)
        val call = service.getCuriosityPhotoFromEarthDate(roverName, earthDate, this.getString(R.string.api_key))

        call.enqueue(object : Callback<RoverPhotoResponse> {
            override fun onResponse(call: Call<RoverPhotoResponse>, response: Response<RoverPhotoResponse>) {
                if (response.code() == 200) {
                    Log.d("response recived" , response.body().toString())

                    roverPhotoIdList.clear()
                    response.body()?.photos?.forEach {
                        roverPhotoIdList.add(it)
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
        earthDate = "$year-$month-$day"

        Log.d("MainActivity", "From Calendar: $roverName is set $earthDate is set")
        getRoverPhotos()
    }

    private fun setupDatePicker(){
        datePicker = findViewById(R.id.rover_date_picker)
        val dateSetListener =
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                cal.set(YEAR, year)
                cal.set(MONTH, monthOfYear)
                cal.set(DAY_OF_MONTH, dayOfMonth)
                setRoverDate()
            }

        datePicker!!.setOnClickListener {
            DatePickerDialog(
                this@MainActivity,
                dateSetListener,
                cal.get(YEAR),
                cal.get(MONTH),
                cal.get(DAY_OF_MONTH)
            ).show()
        }
    }

    private fun setupRoverSpinner(){
        var roverSpinner = findViewById<Spinner>(R.id.roverNameSpinner)
        roverSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
                if (parent != null) {
                    roverName = parent.getItemAtPosition(pos).toString()
                    Log.d("MainActivity", "From Spinner: $roverName is set $earthDate is set")
                    getRoverPhotos()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }

    }

    private fun setupRecyclerView(){
        val recyclerView: RecyclerView = findViewById(R.id.rvRoverPhotos)
        roverPhotoRecyclerViewAdapter = RoverPhotoRecyclerViewAdapter(roverPhotoIdList)
        val layoutManager = LinearLayoutManager(applicationContext)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = roverPhotoRecyclerViewAdapter
    }
}