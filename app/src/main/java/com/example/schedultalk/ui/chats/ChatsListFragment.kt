package com.example.schedultalk.ui.chats

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.schedultalk.databinding.FragmentChatsListBinding
import com.example.schedultalk.ui.schedule.ScheduleFragment

class ChatsListFragment : Fragment() {

    val chats = arrayOf(
        Chat(EUser.Heesu, "안희수", "안녕하세요!", "4분 전"),
        Chat(EUser.Seunghwan, "안승환", "뭐해?", "11월 6일"),
        Chat(EUser.Haejin, "정해진", "회의 어디서 할까요?", "11월 6일"),
    )
    lateinit var binding: FragmentChatsListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this.binding = FragmentChatsListBinding.inflate(inflater, container, false)
        val view = binding.root

        // RecyclerView ID에 'binding' 객체 사용
        this.binding.recChats.layoutManager = LinearLayoutManager(requireContext())
        this.binding.recChats.adapter = ChatsAdapter(chats)

        Log.v("tt", "tt")

        return view
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
