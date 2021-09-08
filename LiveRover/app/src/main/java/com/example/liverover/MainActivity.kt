package com.example.liverover

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.Spinner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.liverover.controller.RoverService
import com.example.liverover.model.Camera
import com.example.liverover.model.Photo
import com.example.liverover.model.Rover
import com.example.liverover.model.RoverPhotoResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import java.util.Calendar.*
import kotlin.collections.ArrayList

/**
 * Activity handler for main view
 */
class MainActivity : AppCompatActivity() {
    private val roverPhotoIdList = ArrayList<Photo>()
    private lateinit var roverPhotoRecyclerViewAdapter: RoverPhotoRecyclerViewAdapter
    private var datePicker: Button? = null
    private var roverName: String = ""
    private var earthDate: String = ""
    private var cal: Calendar = getInstance()

    /**
     * set view elements on main view
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupRoverSpinner()
        setupDatePicker()
        setupRecyclerView()
    }

    /**
     * makes a request to NASA API, sets recycler view to the photo list from the response
     */
    private fun getRoverPhotos(){
        val roverService = RoverService.create(this.getString(R.string.base_url))
        val call = roverService.getCuriosityPhotoFromEarthDate(roverName, earthDate, this.getString(R.string.api_key))

        call.enqueue(object : Callback<RoverPhotoResponse> {
            //if request succeeds, send photo list to recyclerview
            override fun onResponse(call: Call<RoverPhotoResponse>, response: Response<RoverPhotoResponse>) {
                if (response.code() == 200) {
                    if(!response.body()?.photos.isNullOrEmpty()){
                        response.body()?.photos?.let { updateRoverList(it) }
                    } else {
                        //if no photos found for a date, set to default photo
                        updateRoverList(arrayListOf(getDefaultPhotoObject()))
                    }
                } else{
                    updateRoverList(arrayListOf(getDefaultPhotoObject()))
                }
            }

            //sets photo object to default object if request fails
            override fun onFailure(call: Call<RoverPhotoResponse>, t: Throwable) {
                updateRoverList(arrayListOf(getDefaultPhotoObject()))
            }
        })
    }

    /**
     * sets the earthDate value based on current calendar selection
     */
    private fun setRoverDate(){
        val year = cal.get(YEAR)
        val month = cal.get(MONTH) + 1
        val day = cal.get(DAY_OF_MONTH)
        earthDate = "$year-$month-$day"
        getRoverPhotos()
    }

    /**
     * handles setup of helper functions for date picker
     */
    private fun setupDatePicker(){
        datePicker = findViewById(R.id.roverDatePicker)
        val dateSetListener =
            //set date for rover lookup
            DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                cal.set(YEAR, year)
                cal.set(MONTH, monthOfYear)
                cal.set(DAY_OF_MONTH, dayOfMonth)
                setRoverDate()
            }

        //sets onClickListener for the date picker
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

    /**
     * creates rover name spinner handlers for selections
     */
    private fun setupRoverSpinner(){
        val roverSpinner = findViewById<Spinner>(R.id.roverNameSpinner)
        roverSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            // handler for when an item is selected
            override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
                roverName = parent.getItemAtPosition(pos).toString()
                getRoverPhotos()
            }

            //handler if no item is selected
            override fun onNothingSelected(parent: AdapterView<*>) {
                roverName = parent.getItemAtPosition(0).toString()
            }

        }

    }

    /**
     * creates recyclerview on main activity
     */
    private fun setupRecyclerView(){
        val recyclerView: RecyclerView = findViewById(R.id.rvRoverPhotos)
        roverPhotoRecyclerViewAdapter = RoverPhotoRecyclerViewAdapter(roverPhotoIdList)
        val layoutManager = LinearLayoutManager(applicationContext)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = roverPhotoRecyclerViewAdapter
    }

    /**
     * updates recyclerview with new list of photos
     * @param photos an ArrayList of Photos to be added
     */
    private fun updateRoverList(photos: ArrayList<Photo>) {
        roverPhotoIdList.clear()
        photos.forEach {
            roverPhotoIdList.add(it)
        }
        roverPhotoRecyclerViewAdapter.notifyDataSetChanged()
    }

    /**
     * gets a photo object with hardcoded default values
     * @return Photo with SaffronCam info
     */
    private fun getDefaultPhotoObject() : Photo{
        val camera = Camera(0, "SaffronCam", 0, "Saffron Nap Time Camera")
        val rover = Rover(0, "Saffron", "2021-1-6", "2018-10-8", "napping")
        return Photo(123, 0, camera, "", "2021-8-25", rover)
    }
}