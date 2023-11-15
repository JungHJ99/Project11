package com.example.schedultalk.ui.schedule

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.schedultalk.databinding.ItemCalendarBinding

class CalendarDayAdapter(
    val calendarDays: ArrayList<CalendarDay>) :
    RecyclerView.Adapter<CalendarDayAdapter.Holder>() {

    // 화면 설정
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ItemCalendarBinding.inflate(
            LayoutInflater.from(parent.context), parent, false)
        return Holder(binding, parent.context)
    }

    // 외부에서 클릭 시 이벤트 설정
    interface OnItemClickListener {
        fun onClick(v: View, calendarDay: CalendarDay)
    }
    // setItemClickListener로 설정한 함수 실행
    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        itemClickListener = onItemClickListener
    }
    // 데이터 설정
    private lateinit var itemClickListener : OnItemClickListener

    override fun getItemCount() = calendarDays.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(calendarDays[position])
        holder.itemView.setOnClickListener {
            itemClickListener.onClick(it, calendarDays[position])
        }
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