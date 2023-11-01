package com.example.schedultalk.ui.schedule

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.schedultalk.databinding.FragmentScheduleBinding

class ScheduleFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentScheduleBinding.inflate(inflater, container, false)
        parentFragmentManager.beginTransaction().let {
            it.replace(binding.calendarFragment.id, CalendarFragment.newInstance(""))
            it.commit()
        }
        return binding.root
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