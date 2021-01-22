package com.castprogramms.openweathermap.ui.now

import androidx.lifecycle.*
import com.castprogramms.openweathermap.database.data.weather.WeatherDao
import junit.framework.Assert.assertEquals
import net.bytebuddy.implementation.InvokeDynamic.lambda
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito
import org.mockito.Mockito.mock

@RunWith(JUnit4::class)
class NowViewModelTest{

    @get:Rule
    //var rule: TestRule = InstantTaskExecutorRule()
    private val weatherDao = mock(WeatherDao::class.java)

    var nowViewModel = NowViewModel()

    @Before
    fun set_(){
        nowViewModel = NowViewModel()

    }

    @Test
    @Throws(Exception::class)
    fun current_user_observer_liveData(){
        val present = Present()
        //val observer = lambdaMock<(String) -> Unit>()
        val lifecycle = LifecycleRegistry(mock(LifecycleOwner::class.java))
        lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
        //present.observeTitleChange(lifecycle, observer)
        nowViewModel.currentUnit.observeForever{

        }
        assertEquals(nowViewModel.currentUnit.value, "What")
    }
    @After
    fun after_test(){

    }
}
class Present{
    val titleLiveData = MutableLiveData<String>()
    fun observeTitleChange(lifecycle: Lifecycle, observer: (String) -> Unit){
        titleLiveData.observe({lifecycle}){ title ->
            title?.let(observer)
        }
    }
}