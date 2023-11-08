package com.example.schedultalk.ui.schedule

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.schedultalk.databinding.FragmentScheduleBinding
import com.example.schedultalk.viewmodel.ScheduleViewModel

class ScheduleFragment : Fragment() {
    val viewModel: ScheduleViewModel by activityViewModels()
    var calendar_day_schedules : ArrayList<CalendarDaySchedule>? = arrayListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentScheduleBinding.inflate(inflater, container, false)

        // 스케줄데이터 Firbase로부터 가져오기
        viewModel.schedules.observe(viewLifecycleOwner){ schedules ->
            calendar_day_schedules = schedules
            Log.v("log2", calendar_day_schedules.toString())

            // CalendarFragment 불러오기
            loadCalendarFragment(calendar_day_schedules, binding)
            Log.v("log5", calendar_day_schedules.toString())
        }
        return binding.root
    }

    private fun loadCalendarFragment(schedules: ArrayList<CalendarDaySchedule>?, binding: FragmentScheduleBinding) {
        // 이제 이 메서드는 스케줄 데이터가 준비되었을 때만 호출됩니다.
        if (isAdded) { // Fragment가 여전히 액티비티에 추가되어 있는지 확인
            parentFragmentManager.beginTransaction().apply {
                replace(binding.calendarFragment.id, CalendarFragment.newInstance(schedules))
                commit()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.v("log6", calendar_day_schedules.toString())
    }

    companion object {
        @JvmStatic
        fun newInstance(me: String) =
            ScheduleFragment().apply {
                arguments = Bundle().apply {
                    putString("me", me)
                }
            }
    }
}