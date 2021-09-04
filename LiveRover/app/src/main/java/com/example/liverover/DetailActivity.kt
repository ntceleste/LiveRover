package com.example.liverover

import android.media.Image
import android.os.Bundle
import android.util.Log
import android.view.View
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
        Log.d("Detail Activity", "launched")
    }
}