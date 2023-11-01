package com.example.schedultalk.repository

import com.google.firebase.Firebase
import com.google.firebase.database.database

class UserRepository {
    val database = Firebase.database
    val userRef = database.getReference("user")
}