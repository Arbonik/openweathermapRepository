package com.castprogramms.openweathermap.ui.now

import androidx.lifecycle.LiveData
import junit.framework.Assert.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class NowViewModelTest{

    @get:Rule
    lateinit var nowViewModel: NowViewModel

    //@get:Rule
    //var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun set_(){
        nowViewModel = NowViewModel()

    }

    @Test
    @Throws(Exception::class)
    fun current_user_observer_liveData(){
        //val observer = lambdaMock<(String) -> Unit>()
        nowViewModel.currentUnit.observeForever{

        }
        assertEquals(nowViewModel.currentUnit.value, "What")
    }
    @After
    fun after_test(){

    }
}