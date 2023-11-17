package com.example.schedultalk.ui.schedule

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.MutableLiveData
import com.example.schedultalk.databinding.FragmentCalendarBinding
import com.example.schedultalk.databinding.FragmentScheduleBinding
import com.example.schedultalk.viewmodel.ScheduleViewModel

class ScheduleFragment : Fragment() {
    private val viewModel: ScheduleViewModel by activityViewModels()
    private var calendar_day_schedules : ArrayList<CalendarDaySchedule>? = arrayListOf()
    private val userId = "user1"
    private var binding: FragmentScheduleBinding? = null
    private var is_posting_page_opened = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.setUserId(userId)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentScheduleBinding.inflate(inflater, container, false)

        // 스케줄데이터 Firbase로부터 가져오기
        viewModel.schedules.observe(viewLifecycleOwner){ schedules ->
            calendar_day_schedules = schedules

            // CalendarFragment 불러오기
            parentFragmentManager.beginTransaction().apply {
                binding?.calendarFragment?.id?.let {
                    replace(it, CalendarFragment.newInstance(schedules)) }
                commit()
            }
        }

        // 스케줄 추가하기
        binding?.addBtn?.setOnClickListener {
            val postedSchedule = CalendarDaySchedule(
                                    binding?.editTitle?.text.toString(),
                                    binding?.editStartDate?.text.toString(),
                                    binding?.editEndDate?.text.toString(),
                                    binding?.editPlace?.text.toString(),
                                    binding?.editMemo?.text.toString(),
                                    binding?.editColor?.text.toString(),
                //Boolean 스마트캐스트 방법
                                    binding?.editPrivate!!.isChecked,
                                    userId
            )
            postedSchedule.let {
                viewModel.postSchedule(it)
            }
        }

        binding?.postingPage?.visibility = View.GONE
        binding?.addScheduleBtn?.setOnClickListener(){
            Log.v("gg", is_posting_page_opened.toString())
            if(is_posting_page_opened){
                binding?.postingPage?.visibility = View.GONE
                is_posting_page_opened = false
            } else {
                binding?.postingPage?.visibility = View.VISIBLE
                is_posting_page_opened = true
            }
//            is_posting_page_opened != is_posting_page_opened
        }
        return binding?.root
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