package com.example.schedultalk.ui.friends

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.schedultalk.databinding.FragmentFrendsListBinding
import java.util.Locale

class FrendsListFragment : Fragment() {
    lateinit var binding : FragmentFrendsListBinding

    val friendsList = arrayOf(
        Friend("안승환", "ansh10041@gmail.com"),
        Friend("안희수", "1@gmail.com"),
        Friend("정해진", "2@gmail.com")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentFrendsListBinding.inflate(layoutInflater)

        val recyclerView = binding.friendsRv
        val searchView = binding.searchView

        recyclerView.layoutManager = LinearLayoutManager(context)
        binding.friendsRv.adapter = FriendsAdapter(friendsList)


    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
         //val binding = FragmentFrendsListBinding.inflate(inflater, container, false)

        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(me: String) =
            FrendsListFragment().apply {
                arguments = Bundle().apply {
                    putString("me", me)
                }
            }
    }
}