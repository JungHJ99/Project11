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
    val userId = "user1"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.setUserId(userId)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentScheduleBinding.inflate(inflater, container, false)

        // 스케줄데이터 Firbase로부터 가져오기
        viewModel.schedules.observe(viewLifecycleOwner){ schedules ->
            calendar_day_schedules = schedules

            // CalendarFragment 불러오기
            parentFragmentManager.beginTransaction().apply {
                replace(binding.calendarFragment.id, CalendarFragment.newInstance(schedules))
                commit()
            }
        }

        binding.addScheduleBtn.setOnClickListener(){
            Log.v("gg", "gg")
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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