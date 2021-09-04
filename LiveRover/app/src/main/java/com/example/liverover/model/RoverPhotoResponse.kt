package com.example.liverover.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class RoverPhotoResponse(
    val photos: ArrayList<Photo>
)

@Parcelize
data class Photo(
    val id: Int,
    val sol: Int,
    val camera: Camera,
    val img_src: String,
    val earth_date: String,
    val rover: Rover
) : Parcelable

@Parcelize
data class Rover(
    val id: Int,
    val name: String,
    val landing_date: String,
    val launch_date: String,
    val status: String
) : Parcelable

@Parcelize
data class Camera(
    val id: Int,
    val name: String,
    val rover_id: Int,
    val full_name: String
) : Parcelable

