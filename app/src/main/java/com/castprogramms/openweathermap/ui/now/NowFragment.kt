package com.castprogramms.openweathermap.ui.now

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.castprogramms.openweathermap.R
import com.castprogramms.openweathermap.database.DataRepository
import com.castprogramms.openweathermap.database.data.IconReform
import com.castprogramms.openweathermap.database.data.weather.WeatherData
import com.castprogramms.openweathermap.databinding.FragmentNowBinding
import com.castprogramms.openweathermap.tools.WeatherConverter
import java.lang.Exception

class NowFragment : Fragment() {

    private lateinit var nowViewModel: NowViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        nowViewModel =
            ViewModelProvider(this).get(NowViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_now, container, false)
        val fragmentNowBinding = FragmentNowBinding.bind(root)
        fragmentNowBinding.metric = DataRepository.QUERY_PARAM.tempFormat.name

        nowViewModel.currentUnit.observe(viewLifecycleOwner) {
            fragmentNowBinding.weatherData = it
            fragmentNowBinding.nowWind.setText(WeatherConverter.convertToWay(it?.windDirection?.toDouble() ?: 0.0))
            fragmentNowBinding.weatherIcon.setImageResource(
                IconReform.icon[it?.conditionIconUrl] ?: R.drawable.calendar_24
            )
        }
        nowViewModel.downloadData()
        return root
    }
}