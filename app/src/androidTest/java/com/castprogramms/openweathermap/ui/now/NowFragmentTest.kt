package com.castprogramms.openweathermap.ui.now

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.android21buttons.fragmenttestrule.FragmentTestRule
import com.castprogramms.openweathermap.MainActivity
import com.castprogramms.openweathermap.R
import junit.framework.TestCase

import org.junit.Assert.*
import org.junit.Before
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NowFragmentTest{
    @Rule
    @JvmField
    val fragmentTestRule : FragmentTestRule<MainActivity, NowFragment> =
        FragmentTestRule(MainActivity::class.java, NowFragment::class.java)
    @Before
    fun set(){

    }

    @Test
    fun loading_test(){
        fragmentTestRule.launchFragment(NowFragment())
    }

    @Ignore
    @Test
    @Throws(Exception::class)
    fun viewElements(){
        onView(withId(R.id.type_weather))
            .check(matches(isDisplayed()))
    }
}