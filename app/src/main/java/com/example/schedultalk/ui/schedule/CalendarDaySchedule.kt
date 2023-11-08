package com.example.schedultalk.ui.schedule
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CalendarDaySchedule(
    var color: String? = null,
    var endDate: String? = null,
    var memo: String? = null,
    var place: String? = null,
    var private: Boolean = false,
    var startDate: String? = null,
    var title: String? = null,
    var createdBy: String? = null
    ): Parcelable{}

data class CalendarDaySchedules(
    var schedules: ArrayList<CalendarDaySchedule>? = null
)