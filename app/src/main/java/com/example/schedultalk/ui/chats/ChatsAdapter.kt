package com.example.schedultalk.ui.chats

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.schedultalk.R
import com.example.schedultalk.databinding.ItemChatsListBinding

class ChatsAdapter(val chats: Array<Chat>) :
    RecyclerView.Adapter<ChatsAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ItemChatsListBinding.inflate(LayoutInflater.from(parent.context))
        return Holder(binding)
    }

    override fun getItemCount() = chats.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(chats[position])
    }

    class Holder(private val binding: ItemChatsListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(chat: Chat) {
            binding.imageView.setImageResource(
                when (chat.user) {
                    EUser.Heesu -> R.drawable.heesu
                    EUser.Seunghwan -> R.drawable.seunghwan
                    EUser.Haejin -> R.drawable.haejin
                }
            )
            binding.txtName.text = chat.name
            binding.txtContent.text = chat.content
            binding.txtTime.text = chat.time
        }
    }
}
