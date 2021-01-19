package com.castprogramms.openweathermap

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {
//    @Rule
//    val activityTestRule : ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java)

    @Test
    fun clickButNavMenuWeek(){
        onView(withId(R.id.navigation_week))
            .perform(click())
            .check(matches(isDisplayed()))
    }
}