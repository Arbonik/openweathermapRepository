package com.castprogramms.openweathermap

import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {


    init {
        LocalUtils.updateConfiguration(this)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        requestPermissions(
            arrayOf(
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.INTERNET
            ), 1
        )


        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_now,
                R.id.navigation_week,
                R.id.navigation_map,
                R.id.navigation_settings
            )
        )
        //val actBar : ActionBar = ActionBar.DISPLAY_SHOW_TITLE(actionBar?.setTitle("scdvs"))

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    fun setNewTitle(title : String) {
        setTitle(title)
    }
    fun setNewTitle(title : Int) {
        setTitle(title)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
            findNavController(R.id.nav_host_fragment).navigateUp()
        return super.onOptionsItemSelected(item)
    }
}

