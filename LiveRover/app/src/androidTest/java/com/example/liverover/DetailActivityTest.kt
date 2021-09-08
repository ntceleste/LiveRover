package com.example.liverover


import android.content.Intent
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import com.example.liverover.model.Camera
import com.example.liverover.model.Photo
import com.example.liverover.model.Rover
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import androidx.test.espresso.Espresso.*
import androidx.test.espresso.matcher.ViewMatchers.*


@RunWith(AndroidJUnit4::class)
@LargeTest
class DetailActivityTest {
    private var roverId = 0
    private var roverName = "testRoverName"
    private var landingDate = "2020-1-6"
    private var launchDate = "2019-10-8"
    private var status = "testing"
    private var rover: Rover = Rover(roverId, roverName, landingDate, launchDate, status)

    private var cameraId = 1
    private var cameraName = "testCameraName"
    private var cameraFullName = "testCameraFullName"
    private var camera: Camera = Camera(cameraId, cameraName, roverId, cameraFullName)

    private var photoId = 123
    private var photoSol = 42
    private var imgSrc = "test"
    private var earthDate = "2021-8-25"
    private var photo: Photo = Photo(photoId, photoSol, camera, imgSrc, earthDate, rover)

    private var intent: Intent =
        Intent(InstrumentationRegistry.getInstrumentation().targetContext, DetailActivity::class.java)

    init {
        intent.putExtra("photo_data", photo)
    }

    @get:Rule
    var activityScenarioRule = activityScenarioRule<DetailActivity>(intent)

    @Test
    fun startsWithoutCrashing() {
        activityScenarioRule.scenario
    }

    @Test
    fun setsPhotoData() {
        onView(withId(R.id.photoEarthDateText)).check(matches(withText(earthDate)))
        onView(withId(R.id.photoSolDateText)).check(matches(withText(photoSol.toString())))
        onView(withId(R.id.roverNameText)).check(matches(withText(roverName)))
        onView(withId(R.id.roverStatusText)).check(matches(withText(status)))
        onView(withId(R.id.roverLaunchText)).check(matches(withText(launchDate)))
        onView(withId(R.id.roverLandText)).check(matches(withText(landingDate)))
        onView(withId(R.id.photoCameraNameText)).check(matches(withText(cameraFullName)))
    }
}