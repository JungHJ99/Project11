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
    val schedules : LiveData<ArrayList<CalendarDaySchedule>?> get() = _schedules

    private val repository = ScheduleRepository()
    init{
        repository.getUserSchedules("user1", _schedules) //레포지토리를 사용하여 데이터베이스에서 값 가져옴
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