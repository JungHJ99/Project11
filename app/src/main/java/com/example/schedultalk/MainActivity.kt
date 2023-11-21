package com.example.schedultalk

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.schedultalk.databinding.ActivityMainBinding
import com.example.schedultalk.ui.friends.FrendsListFragment
import com.example.schedultalk.ui.chats.ChatsListFragment
import com.example.schedultalk.ui.schedule.ScheduleFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.schedultalk.ui.friends.FriendsAdapter


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //binding.friends_rv.layoutManager = LinearLayoutManager(this)
        //binding.friends_rv.adapter = FriendsAdapter(friends)


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