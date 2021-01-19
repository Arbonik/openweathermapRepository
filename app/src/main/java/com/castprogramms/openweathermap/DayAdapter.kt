package com.castprogramms.openweathermap

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.castprogramms.openweathermap.database.DataRepository
import com.castprogramms.openweathermap.database.data.IconReform
import com.castprogramms.openweathermap.database.data.weather.DayParameter
import com.castprogramms.openweathermap.database.data.weather.ForecastWeatherData
import com.castprogramms.openweathermap.database.data.weather.WeatherData
import com.castprogramms.openweathermap.databinding.ItemDayBinding
import com.castprogramms.openweathermap.ui.week.WeekFragment

class DayAdapter(var day: List<ForecastWeatherData>, var fragment: Fragment) : RecyclerView.Adapter<DayAdapter.DayHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        DayHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_day,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: DayHolder, position: Int) {
        holder.recyclerviewDayBinding.metric = DataRepository.QUERY_PARAM.tempFormat.name

        holder.recyclerviewDayBinding.dayParam = day[position]
        holder.recyclerviewDayBinding.card.setOnClickListener {
            var bundle = Bundle()
            bundle.putInt(WeekFragment.ITEM_NUMBER_TAG, position)
            fragment.findNavController().navigate(R.id.action_navigation_week_to_dayFragment, bundle)
        }
        holder.recyclerviewDayBinding.weatherIcon.setImageResource(IconReform.icon[day[position].conditionIconUrl] ?: R.drawable._02n)
    }

    override fun getItemCount(): Int {
        return day.size
    }

    inner class DayHolder(val recyclerviewDayBinding: ItemDayBinding): RecyclerView.ViewHolder(recyclerviewDayBinding.root)

}
