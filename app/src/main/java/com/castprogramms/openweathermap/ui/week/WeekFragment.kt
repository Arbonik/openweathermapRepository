package com.castprogramms.openweathermap.ui.week

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.castprogramms.openweathermap.DayAdapter
import com.castprogramms.openweathermap.R
import com.castprogramms.openweathermap.database.data.weather.ForecastWeatherData


class WeekFragment : Fragment() {

    private lateinit var weekViewModel: WeekViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        weekViewModel =
            ViewModelProvider(this).get(WeekViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_week, container, false)

        val recyclerView = root.findViewById<RecyclerView>(R.id.recycler_view).apply {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            adapter = DayAdapter(listOf(), this@WeekFragment)
        }
        weekViewModel.days.observe(viewLifecycleOwner){
            (recyclerView.adapter as DayAdapter).apply {
                day = (it as List<ForecastWeatherData>).reversed()
                notifyDataSetChanged()
            }
        }
        weekViewModel.downloadData()

        requireActivity()
            .onBackPressedDispatcher
            .addCallback(this.viewLifecycleOwner, object : OnBackPressedCallback(true){
                override fun handleOnBackPressed() {
                    if (isEnabled){
                        isEnabled = false
                        requireActivity().onBackPressed()
                    }
                }
            })

        return root
    }

    companion object {
        const val ITEM_NUMBER_TAG = "itemNumberTag"
    }
}