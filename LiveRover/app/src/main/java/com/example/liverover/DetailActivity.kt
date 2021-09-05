package com.example.liverover

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.liverover.model.Photo
import java.io.File

class DetailActivity : AppCompatActivity()  {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        val photoData = intent.getParcelableExtra<Photo>("photo_data")

        val media = photoData?.img_src

        if (!media.isNullOrEmpty()) {
            Glide.with(this)
                .load(media)
                .into(findViewById(R.id.imageview))
        } else {
            Log.d("DetailActivity", "loading in saffron image")
        }

        if (photoData != null) {
            setPhotoDetailText(
                photoData.earth_date,
                photoData.sol.toString(),
                photoData.rover.name,
                photoData.rover.status,
                photoData.rover.launch_date,
                photoData.rover.landing_date,
                photoData.camera.full_name
            )
        } else {
            setPhotoDetailText(
                "sorry",
                "I",
                "must've",
                "loaded",
                "this",
                "in",
                "wrong :(")
        }

        Log.d("Detail Activity", "launched")
    }

    private fun setPhotoDetailText(
        earthDate: String,
        solDate: String,
        roverName: String,
        roverStatus: String,
        roverLaunch: String,
        roverLand: String,
        roverCamera: String
    ){
        findViewById<TextView>(R.id.photoEarthDateText).text = earthDate
        findViewById<TextView>(R.id.photoSolDateText).text = solDate
        findViewById<TextView>(R.id.roverNameText).text = roverName
        findViewById<TextView>(R.id.roverStatusText).text = roverStatus
        findViewById<TextView>(R.id.roverLaunchText).text = roverLaunch
        findViewById<TextView>(R.id.roverLandText).text = roverLand
        findViewById<TextView>(R.id.photoCameraNameText).text = roverCamera
    }
}