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
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.schedultalk.databinding.FragmentCalendarBinding
import com.example.schedultalk.viewmodel.ScheduleViewModel
import java.sql.Date
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class CalendarFragment : Fragment() {

    private var binding: FragmentCalendarBinding? = null
    private var calendarDaySchedules : ArrayList<CalendarDaySchedule>? = arrayListOf()
    private var isDayView = false
    // 선택한 날짜
    private lateinit var selectedDate: LocalDate
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
        calendarDaySchedules = arguments?.getParcelableArrayList("calendar_day_schedules")

        // 선택한 날짜를 현재로 설정
        selectedDate = LocalDate.now()

        binding?.textMonth?.setOnClickListener{
            setMonthView(selectedDate)
        }

        // 화면 설정
        if (!isDayView) {
            setMonthView(selectedDate)
        } else {
            setDayView(selectedDate)
        }

        // 이전달 버튼 이벤트
        binding?.btnLastMonth?.setOnClickListener {
            if(!isDayView){
                selectedDate = selectedDate.minusMonths(1)
                setMonthView(selectedDate)
            } else {
                selectedDate = selectedDate.minusDays(1)
                setDayView(selectedDate)
            }

        }

        // 다음달 버튼 이벤트
        binding?.btnNextMonth?.setOnClickListener {
            if(!isDayView) {
                selectedDate = selectedDate.plusMonths(1)
                setMonthView(selectedDate)
            } else {
                selectedDate = selectedDate.plusDays(1)
                setDayView(selectedDate)
            }
        }

        return binding?.root
    }

    // 날짜별 스케줄 가져오는 메서드
    @RequiresApi(Build.VERSION_CODES.O)
    private fun getDaySchedules(selectedDate: LocalDate): ArrayList<CalendarDaySchedule>{
        var dayScheduleList = listOf<CalendarDaySchedule>()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
        dayScheduleList = calendarDaySchedules?.filter {
            val startDateTime = LocalDateTime.parse(it.startDate, formatter)
            val startDate = startDateTime.toLocalDate()
            val endDateTime = LocalDateTime.parse(it.endDate, formatter)
            val endDate = endDateTime.toLocalDate()
            !(selectedDate.isBefore(startDate) || selectedDate.isAfter(endDate))
        } ?: listOf()
        dayScheduleList = dayScheduleList.sortedBy { it.startDate }
        return ArrayList(dayScheduleList)
    }

    // 날짜 가져오는 메서드
    @RequiresApi(Build.VERSION_CODES.O)
    private fun getDays (selectedDate: LocalDate): ArrayList<CalendarDay> {
        var calendarDays = ArrayList<CalendarDay>()
        // 첫번째 날짜
        val firstDate = selectedDate.withDayOfMonth(1)
        // 첫번째 날짜 요일
        val firstDateDay = firstDate.dayOfWeek.value % 7
        // 마지막 날짜
        val lastDate = selectedDate.lengthOfMonth()

        // calendarDays에 날짜 넣어주기
        for(i in 1..lastDate + firstDateDay){
            if(i <= firstDateDay || i > lastDate + firstDateDay) {
                calendarDays.add(CalendarDay("", arrayListOf<CalendarDaySchedule>()))
            } else {
                val date = i - firstDateDay
                // 각 날짜 to LocalDate
                val targetDate = LocalDate.of(
                    selectedDate.year,
                    selectedDate.monthValue,
                    date)
                val dayScheduleArraylist = getDaySchedules(targetDate)
                calendarDays.add(
                    CalendarDay(
                        date.toString(),
                        dayScheduleArraylist))
            }
        }
        return calendarDays
    }

    // 월 변경
    @RequiresApi(Build.VERSION_CODES.O)
    private fun setMonthView(currentDate: LocalDate) {
        isDayView = false
        binding?.textYear?.text = "${currentDate.year.toString()}년"
        binding?.textMonth?.text = "${currentDate.monthValue}월"
        binding?.weekList?.visibility = View.VISIBLE

        val dayAdapter = CalendarDayAdapter(getDays(currentDate))
        binding?.calendar?.layoutManager = GridLayoutManager(this.context, 7)
        binding?.calendar?.adapter = dayAdapter

        dayAdapter.setItemClickListener(object: CalendarDayAdapter.OnItemClickListener{
            override fun onClick(v: View, calendarDay: CalendarDay) {
                if(calendarDay.date != "") {
                    // 클릭 시 이벤트 작성
                    selectedDate = LocalDate.of(
                        currentDate.year,
                        currentDate.monthValue,
                        calendarDay.date.toInt())
                    setDayView(selectedDate)
                }
            }
        })
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setDayView(currentDate: LocalDate) {
        isDayView = true
        binding?.textYear?.text = "${currentDate.year.toString()}년"
        binding?.textMonth?.text = "${currentDate.monthValue}월 ${currentDate.dayOfMonth}일"
        binding?.weekList?.visibility = View.INVISIBLE

        binding?.calendar?.layoutManager = LinearLayoutManager(this.context)
        binding?.calendar?.adapter = CalendarTimeScheduleAdapter(getDaySchedules(currentDate))
    }

    companion object {
        @JvmStatic
        fun newInstance(calendarDaySchedules: ArrayList<CalendarDaySchedule>?) =
            CalendarFragment().apply {
                arguments = Bundle().apply {
                    putParcelableArrayList("calendar_day_schedules", calendarDaySchedules)
                }
            }
    }
}