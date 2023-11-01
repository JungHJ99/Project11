package com.example.schedultalk.repository
import com.example.schedultalk.ui.schedule.CalendarDaySchedule
import com.example.schedultalk.ui.schedule.CalendarDaySchedules
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import org.json.JSONArray

class ScheduleRepository {
    val database = Firebase.database
    val schedulesRef = database.getReference("schedules")

    fun readSchedules() : ArrayList<CalendarDaySchedule>? {
//        var schedules : ArrayList<CalendarDaySchedule>? = arrayListOf()
//        schedulesRef.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                val schedulesData = dataSnapshot.getValue(CalendarDaySchedules::class.java)
//                schedules = schedulesData?.schedules
//            }
//
//            override fun onCancelled(databaseError: DatabaseError) {
//            }
//        })
//        return schedules
        return arrayListOf(
            CalendarDaySchedule("", "", "", "", true,"", "공부"),
            CalendarDaySchedule("", "", "", "", true,"", "잠")
        )
    }
}