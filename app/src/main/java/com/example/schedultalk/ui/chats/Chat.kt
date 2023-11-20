package com.example.schedultalk.ui.chats

enum class EUser{
    Heesu,
    Seunghwan,
    Haejin
}

data class Chat (val user: EUser,
                 val name: String,
                 val content: String,
                 val time: String)