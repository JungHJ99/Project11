package com.example.schedultalk.ui.schedule

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.schedultalk.databinding.ItemCalendarBinding

class CalendarDayAdapter(val calendarDays: ArrayList<CalendarDay>) :
    RecyclerView.Adapter<CalendarDayAdapter.Holder>() {

        // 화면 설정
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
            val binding = ItemCalendarBinding.inflate(LayoutInflater.from(parent.context))
            return Holder(binding, parent.context)
        }

        override fun getItemCount() = calendarDays.size

        // 데이터 설정
        override fun onBindViewHolder(holder: Holder, position: Int) {
            holder.bind(calendarDays[position])
        }

        class Holder(val binding: ItemCalendarBinding, val context: Context)
            : RecyclerView.ViewHolder(binding.root) {
            fun bind(calendarDay: CalendarDay) {
                binding.date.text = calendarDay.date.toString()
                // 스케줄 recyclerView
                binding.schedules.layoutManager= LinearLayoutManager(
                    context, LinearLayoutManager.VERTICAL, false)
                binding.schedules.adapter = CalendarDayScheduleAdapter(calendarDay.scheduleList)
            }
        }
    }