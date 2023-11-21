package com.example.schedultalk.ui.friends

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.schedultalk.databinding.ListFriendBinding

class FriendsAdapter(val friends: Array<Friend>): RecyclerView.Adapter<FriendsAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ListFriendBinding.inflate(LayoutInflater.from(parent.context))
        return Holder(binding)
    }

    override fun getItemCount() = friends.size


    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(friends[position])
    }

    class Holder(private val binding: ListFriendBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(friend: Friend) {
            binding.txtName.text = friend.name
        }
    }
}