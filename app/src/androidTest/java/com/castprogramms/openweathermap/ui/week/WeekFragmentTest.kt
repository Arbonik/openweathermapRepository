package com.castprogramms.openweathermap.ui.week

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.android21buttons.fragmenttestrule.FragmentTestRule
import com.castprogramms.openweathermap.DayAdapter
import com.castprogramms.openweathermap.MainActivity
import com.castprogramms.openweathermap.R
import com.castprogramms.openweathermap.ui.now.NowFragment
import junit.framework.TestCase

import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class WeekFragmentTest {
    @get: Rule
    @JvmField
    val fragmentTestRule: FragmentTestRule<MainActivity, WeekFragment> =
        FragmentTestRule(MainActivity::class.java, WeekFragment::class.java)

    @Test
    @Throws(Exception::class)
    fun recyclerView() {
        onView(withId(R.id.recycler_view))
            .perform(actionOnItemAtPosition<DayAdapter.DayHolder>(0, click()))

    }
}