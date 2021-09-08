package com.example.liverover

import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.PickerActions
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.filters.LargeTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

import android.widget.DatePicker
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.intent.Intents
import org.hamcrest.Matchers.*


@RunWith(AndroidJUnit4::class)
@LargeTest
class MainActivityTest {

    @get:Rule
    var activityScenarioRule = activityScenarioRule<MainActivity>()

    @Test
    fun startsWithoutCrashing() {
        activityScenarioRule.scenario
    }

    @Test
    fun canSelectRoverFromSpinner() {
        onView(withId(R.id.roverNameSpinner)).perform(click())
        onData(allOf(`is`(instanceOf(String::class.java)))).atPosition(1).perform(click())
        onView(withId(R.id.roverNameSpinner)).check(matches(withSpinnerText(containsString("Perseverance"))))

    }

    @Test
    fun tappingSelectDateButtonLaunchesDatePicker() {
        //click button to reveal date picker
        onView(withId(R.id.roverDatePicker)).perform(click())
        //check DatePicker is displayed
        onView(withClassName(equalTo(DatePicker::class.java.name))).check(matches(isDisplayed()))
    }

    @Test
    fun selectInvalidDatePopulatesDefault() {
        setDate(R.id.roverDatePicker, 1997, 10,20)

        onView(withId(R.id.rvRoverPhotos)).perform(RecyclerViewActions.scrollTo<RecyclerView.ViewHolder>(
            hasDescendant(withText("123 - Saffron Nap Time Camera"))))
    }

    @Test
    fun selectValidDatePopulatesValues() {
        setDate(R.id.roverDatePicker, 2021, 8, 21)
        onView(withId(R.id.rvRoverPhotos)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                1
            )
        )
    }

    @Test
    fun tappingIdLaunchesDetailView() {
        setDate(R.id.roverDatePicker, 1997, 10,20)
        //this is a bad solution... But I wasn't sure it made sense to implement an idling resource... Would love to chat more on this.
        Thread.sleep(1000)
        Intents.init()
        onView(withId(R.id.rvRoverPhotos)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        intended(hasComponent(DetailActivity::class.java.name))
        Intents.release()
    }


    private fun setDate(datePickerLaunchViewId: Int, year: Int, monthOfYear: Int, dayOfMonth: Int) {
        onView(withId(datePickerLaunchViewId)).perform(click())
        onView(withClassName(equalTo(DatePicker::class.java.name.toString()))).perform(
            PickerActions.setDate(
                year,
                monthOfYear,
                dayOfMonth
            )
        )
        onView(withId(android.R.id.button1)).perform(click())
    }
}