package com.example.schedultalk.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.schedultalk.repository.UserRepository

class UserViewModel: ViewModel() {
    private val repository = UserRepository()
}