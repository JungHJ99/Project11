package com.example.schedultalk.ui.schedule

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.schedultalk.databinding.ItemCalendarBinding
import com.example.schedultalk.databinding.ItemScheduleBinding

class CalendarDayScheduleAdapter(
//    val schedules: ArrayList<CalendarDaySchedule>,
    val calendarDay: CalendarDay,
    val onItemClickListener: CalendarDayAdapter.OnItemClickListener) :
    RecyclerView.Adapter<CalendarDayScheduleAdapter.Holder>() {

    // 화면 설정
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ItemScheduleBinding.inflate(
            LayoutInflater.from(parent.context), parent, false)
        return Holder(binding, parent.context)
    }

    override fun getItemCount() = calendarDay.scheduleList.size

    // 데이터 설정
    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(calendarDay.scheduleList[position])
        holder.itemView.setOnClickListener {
            onItemClickListener.onClick(it, calendarDay)
        }
    }

    class Holder(val binding: ItemScheduleBinding, val context: Context)
        : RecyclerView.ViewHolder(binding.root) {
        fun bind(calendarDaySchedule: CalendarDaySchedule) {
            binding.scheduleTitle.text = calendarDaySchedule.title
            val color = Color.parseColor(calendarDaySchedule.color)
            binding.scheduleChip.background = ColorDrawable(color)
        }
    }
}