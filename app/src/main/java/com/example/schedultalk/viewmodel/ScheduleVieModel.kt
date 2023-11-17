package com.example.schedultalk.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.schedultalk.repository.ScheduleRepository
import com.example.schedultalk.ui.schedule.CalendarDaySchedule
import kotlinx.coroutines.launch

class ScheduleViewModel: ViewModel() {
    private val _schedules = MutableLiveData<ArrayList<CalendarDaySchedule>?>()
    private lateinit var _userId : String
    val schedules : LiveData<ArrayList<CalendarDaySchedule>?> get() = _schedules

    private val repository = ScheduleRepository()

    fun setUserId(userId: String) {
        _userId = userId
        repository.getUserSchedules(_userId, _schedules) // Update schedules with the new userId
    }
//        fun addSchedule(data: CalendarDaySchedule){
//
//        }
//
//    fun retrieveSchedules(){
//        viewModelScope.launch {
//            val rep_schedules =  repository.readSchedules()
//            _schedules.value = rep_schedules
//        }
//    }
}