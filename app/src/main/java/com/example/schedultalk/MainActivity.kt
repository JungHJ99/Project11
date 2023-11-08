package com.example.schedultalk

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.schedultalk.databinding.ActivityMainBinding
import com.example.schedultalk.ui.friends.FrendsListFragment
import com.example.schedultalk.ui.chats.ChatsListFragment
import com.example.schedultalk.ui.schedule.CalendarDay
import com.example.schedultalk.ui.schedule.CalendarDaySchedule
import com.example.schedultalk.ui.schedule.ScheduleFragment
import com.example.schedultalk.viewmodel.ScheduleViewModel
import androidx.fragment.app.activityViewModels


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Fragment를 바꾸는 함수
        fun replacFragment(frag: Fragment) {
            supportFragmentManager.beginTransaction().let {
                it.replace(binding.frmFragment.id, frag)
                it.commit()
            }
        }

        // 초기 화면 설정
        replacFragment(FrendsListFragment.newInstance(""))

        // 하단의 탭 입력을 받는 setOnItemSelectedListener 함수를 통해 세 개의 fragment를 알맞게 보여준다.
        val bottomNavMenu = binding.bottomNavMenu

        bottomNavMenu.run {
            setOnItemSelectedListener { item ->
                when (item.itemId) {
                    // R.id 는 아이디를 객체로 받아오는 접두어이다. when 비교 구문에서 사용
                    R.id.nav_friends -> {
                        replacFragment(FrendsListFragment.newInstance(""))
                    }
                    R.id.nav_chats -> {
                        replacFragment(ChatsListFragment.newInstance(""))
                    }
                    R.id.nav_schedule -> {
                        replacFragment(ScheduleFragment.newInstance(""))
                    }
                }
                true
            }
        }
    }
}