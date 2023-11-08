package com.example.schedultalk.ui.schedule

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.schedultalk.databinding.FragmentCalendarBinding
import com.example.schedultalk.viewmodel.ScheduleViewModel
import java.sql.Date
import java.time.LocalDate

class CalendarFragment : Fragment() {

    var binding: FragmentCalendarBinding? = null
    var calendar_day_schedules : ArrayList<CalendarDaySchedule>? = arrayListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCalendarBinding.inflate(inflater)
        // 스케줄 가져오기
        calendar_day_schedules = arguments?.getParcelableArrayList("calendar_day_schedules")
        Log.v("log3", calendar_day_schedules.toString())

        // 선택한 달의 날짜 리스트
        var calendar_days = ArrayList<CalendarDay>()

        // 날짜 가져오는 함수
        @RequiresApi(Build.VERSION_CODES.O)
        fun getDays (selected_date: LocalDate) {
            calendar_days.clear()
            // 첫번째 날짜
            val first_date = selected_date.withDayOfMonth(1)
            // 첫번째 날짜 요일
            val first_date_day = first_date.dayOfWeek.value % 7
            // 마지막 날짜
            val last_date = selected_date.lengthOfMonth()
            Log.v("log4", calendar_day_schedules.toString())

            // calendar_days에 날짜 넣어주기
            for(i in 1..41){
                if(i <= first_date_day || i > last_date + first_date_day) {
                    calendar_days.add(CalendarDay("", arrayListOf<CalendarDaySchedule>()))
                } else {
                    calendar_days.add(
                        CalendarDay(
                            (i - first_date_day).toString(),
                            calendar_day_schedules ?: arrayListOf<CalendarDaySchedule>()))
                }
            }
        }

        // 선택한 날짜
        lateinit var selected_date: LocalDate

        // 월 변경
        fun setMonthView(selected_date: LocalDate) {
            binding?.textYear?.text = "${selected_date.year.toString()}년"
            binding?.textMonth?.text = "${selected_date.monthValue}월"

            getDays(selected_date)
            binding?.calendar?.layoutManager = GridLayoutManager(this.context, 7)
            binding?.calendar?.adapter = CalendarDayAdapter(calendar_days)
        }

        // 선택한 날짜를 현재로 설정
        selected_date = LocalDate.now()

        // 화면 설정
        setMonthView(selected_date)

        // 이전달 버튼 이벤트
        binding?.btnLastMonth?.setOnClickListener {
            selected_date = selected_date.minusMonths(1)
            setMonthView(selected_date)
        }

        // 다음달 버튼 이벤트
        binding?.btnNextMonth?.setOnClickListener {
            selected_date = selected_date.plusMonths(1)
            setMonthView(selected_date)
        }

        return binding?.root
    }

    companion object {
        @JvmStatic
        fun newInstance(calendar_day_schedules: ArrayList<CalendarDaySchedule>?) =
            CalendarFragment().apply {
                arguments = Bundle().apply {
                    putParcelableArrayList("calendar_day_schedules", calendar_day_schedules)
                }
            }
    }
}