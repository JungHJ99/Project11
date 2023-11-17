package com.example.schedultalk.ui.schedule

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.schedultalk.databinding.ItemCalendarBinding
import com.example.schedultalk.databinding.ItemTimeScheduleBinding
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class CalendarTimeScheduleAdapter(private val calendarDaySchedules: ArrayList<CalendarDaySchedule>) :
    RecyclerView.Adapter<CalendarTimeScheduleAdapter.Holder>() {

        // 화면 설정
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
            val binding = ItemTimeScheduleBinding.inflate(
                LayoutInflater.from(parent.context), parent, false)
            return Holder(binding, parent.context)
        }

        override fun getItemCount() = calendarDaySchedules.size

        // 데이터 설정
        @RequiresApi(Build.VERSION_CODES.O)
        override fun onBindViewHolder(holder: Holder, position: Int) {
            holder.bind(calendarDaySchedules[position])
        }

        class Holder(private val binding: ItemTimeScheduleBinding, val context: Context)
            : RecyclerView.ViewHolder(binding.root) {
            @RequiresApi(Build.VERSION_CODES.O)
            fun bind(calendarDaySchedule: CalendarDaySchedule) {
                binding.scheduleTitle.text = calendarDaySchedule.title
                val color = Color.parseColor(calendarDaySchedule.color)
                binding.scheduleCard.setCardBackgroundColor(color)

                // firebase에 저장된 string 날짜 LocalDateTime 객체로 format 하기
                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
                val startDateTime = LocalDateTime.parse(calendarDaySchedule.startDate, formatter)
                val endDateTime = LocalDateTime.parse(calendarDaySchedule.endDate, formatter)
                // 분 앞에 0 넣어주는 함수
                fun zeroAdder(minute: Int): String{
                    var zminute = ""
                    if(minute < 10){
                        zminute = "0$minute"
                    } else {
                        zminute = minute.toString()
                    }
                    return zminute
                }
                binding.scheduleStartDate.text =
                    "${startDateTime.monthValue}월 ${startDateTime.dayOfMonth}일 " +
                            "${startDateTime.hour}:${zeroAdder(startDateTime.minute)} ~ "
                binding.scheduleEndDate.text =
                    "${endDateTime.monthValue}월 ${endDateTime.dayOfMonth}일 " +
                            "${endDateTime.hour}:${zeroAdder(endDateTime.minute)}"

                binding.schedulePlace.text = calendarDaySchedule.place
                binding.scheduleMemo.text = "* ${calendarDaySchedule.memo}"
            }
        }
    }