package com.castprogramms.openweathermap.ui.day

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.castprogramms.openweathermap.R
import com.castprogramms.openweathermap.database.DataRepository
import com.castprogramms.openweathermap.database.data.IconReform
import com.castprogramms.openweathermap.database.data.weather.BossForecastWeatherData
import com.castprogramms.openweathermap.database.data.weather.ForecastWeatherData
import com.castprogramms.openweathermap.databinding.FragmentNowBinding
import com.castprogramms.openweathermap.ui.week.WeekFragment
import com.castprogramms.openweathermap.ui.week.WeekViewModel

class DayFragment : Fragment() {

    private lateinit var viewModel: DayViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_now, container, false)

        viewModel =
            ViewModelProvider(this).get(DayViewModel::class.java)

        val fragmentNowBinding =  FragmentNowBinding.bind(root)
        fragmentNowBinding.metric = DataRepository.QUERY_PARAM.tempFormat.name
        viewModel.unit.observe(viewLifecycleOwner){
            fragmentNowBinding.weatherData = BossForecastWeatherData(it, requireContext())
            fragmentNowBinding.weatherIcon.setImageResource(IconReform.icon[it?.conditionIconUrl] ?: R.drawable.calendar_24)
        }

        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(DayViewModel::class.java)

    }

    override fun onResume() {
        super.onResume()
        val number : Int = arguments?.getInt(WeekFragment.ITEM_NUMBER_TAG) ?: 0
        viewModel.load(number)
    }
}