package com.example.schedultalk.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.schedultalk.repository.ScheduleRepository
import com.example.schedultalk.ui.schedule.CalendarDaySchedule
import kotlinx.coroutines.launch

class ScheduleViewModel: ViewModel() {
    private val repository = ScheduleRepository()

    private val _schedules = MutableLiveData<ArrayList<CalendarDaySchedule>?>()
    val schedules : LiveData<ArrayList<CalendarDaySchedule>?> get() = _schedules

    fun retrieveSchedules(){
        viewModelScope.launch {
            val rep_schedules =  repository.readSchedules()
            _schedules.value = rep_schedules
        }
    }
}