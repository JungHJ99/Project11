package com.example.schedultalk.ui.schedule

import java.time.LocalDate

data class CalendarDaySchedule(
    var color: String? = null,
    var endDate: String? = null,
    var memo: String? = null,
    var place: String? = null,
    var private: Boolean = false,
    var startDate: String? = null,
    var title: String? = null,
    var userId: Int = 0
    )

data class CalendarDaySchedules(
    var schedules: ArrayList<CalendarDaySchedule>? = null
)