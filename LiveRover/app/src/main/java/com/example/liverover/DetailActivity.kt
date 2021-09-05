package com.example.liverover

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.liverover.model.Photo

class DetailActivity : AppCompatActivity()  {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        val photoData = intent.getParcelableExtra<Photo>("photo_data")

        val media = photoData?.img_src

        if (media !== null) {
            Glide.with(this)
                .load(media)
                .into(findViewById(R.id.imageview))
        } else {
        }

        if (photoData != null) {
            setPhotoDetailText(photoData)
        }

        Log.d("Detail Activity", "launched")
    }

    private fun setRoverPhoto(){

    }

    private fun setDefaultPhoto(){

    }

    private fun setPhotoDetailText(photo: Photo){
        findViewById<TextView>(R.id.photoEarthDateText).text = photo.earth_date
        findViewById<TextView>(R.id.photoSolDateText).text = photo.sol.toString()
        findViewById<TextView>(R.id.roverNameText).text = photo.rover.name
        findViewById<TextView>(R.id.roverStatusText).text = photo.rover.status
        findViewById<TextView>(R.id.roverLaunchText).text = photo.rover.launch_date
        findViewById<TextView>(R.id.roverLandText).text = photo.rover.landing_date
        findViewById<TextView>(R.id.photoCameraNameText).text = photo.camera.full_name
    }
}