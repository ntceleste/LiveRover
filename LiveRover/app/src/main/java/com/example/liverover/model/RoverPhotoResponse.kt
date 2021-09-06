package com.example.liverover.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Represents a list of photo objects
 * @property photos a list of photo objects
 */
data class RoverPhotoResponse(
    val photos: ArrayList<Photo>
)

/**
 * Represents a Rover Photo
 * @property id the id of the photo
 * @property sol the sol date the photo was taken
 * @property camera the camera object that took the photo
 * @property img_src the url the photo can be loading in from
 * @property earth_date the earth date the photo was taken as a string
 * @property rover the rover that took the photo
 */
@Parcelize
data class Photo(
    val id: Int,
    val sol: Int,
    val camera: Camera,
    val img_src: String,
    val earth_date: String,
    val rover: Rover
) : Parcelable

/**
 * Represents a Mars Rover
 * @property id the id of the rover
 * @property name the name of the rover
 * @property landing_date the landing date of the rover as a string
 * @property launch_date the launch date of the rover as a string
 * @property status the current status of the rover
 */
@Parcelize
data class Rover(
    val id: Int,
    val name: String,
    val landing_date: String,
    val launch_date: String,
    val status: String
) : Parcelable

/**
 * Represents a Camera on a rover
 * @property id the id of the camera
 * @property name the abbreviated name of the rover
 * @property rover_id the id of the rover the camera is on
 * @property full_name the full name of the rover
 */
@Parcelize
data class Camera(
    val id: Int,
    val name: String,
    val rover_id: Int,
    val full_name: String
) : Parcelable

