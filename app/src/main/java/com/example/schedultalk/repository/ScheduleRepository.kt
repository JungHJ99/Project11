package com.example.schedultalk.repository
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.schedultalk.ui.schedule.CalendarDaySchedule
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database

class ScheduleRepository {
    private val database = Firebase.database
    private val scheduleRef = database.getReference("schedules")
    private val usersRef = database.getReference("users")

    fun observeSchedules(schedulesData: MutableLiveData<ArrayList<CalendarDaySchedule>?>){
        scheduleRef.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val schedules = ArrayList<CalendarDaySchedule>()
                for(snap in snapshot.children){
                    val schedule = snap.getValue(CalendarDaySchedule::class.java)
                    schedule?.let{
                        schedules.add(it)
                    }
                }
                schedulesData.postValue(schedules)
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    fun getUserSchedules(userId: String, schedulesData: MutableLiveData<ArrayList<CalendarDaySchedule>?>) {
        Log.v("log1", "hh")
        usersRef.child(userId).child("schedules").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // 스케줄 목록을 저장할 리스트 초기화
                val userScheduleIds = ArrayList<String>()

                // 스케줄 ID 목록 추출
                snapshot.children.forEach { child ->
                    child.key?.let { key ->
                        if (child.getValue(Boolean::class.java) == true) {
                            userScheduleIds.add(key)
                        }
                    }
                }
                val tasks = ArrayList<Task<DataSnapshot>>()
                val userSchedules = ArrayList<CalendarDaySchedule>()

                userScheduleIds.forEach { scheduleId ->
                    val task = scheduleRef.child(scheduleId).get()
                    tasks.add(task)
                    task.addOnSuccessListener { dataSnapshot ->
                        val schedule = dataSnapshot.getValue(CalendarDaySchedule::class.java)
                        if(schedule != null) {
                            userSchedules.add(schedule)
                        }
                    }
                }

                // Wait for all tasks to complete
                Tasks.whenAllComplete(tasks).addOnCompleteListener {
                    // All tasks are complete, post the value
                    schedulesData.postValue(userSchedules)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // 데이터 가져오기를 실패했거나 취소되었을 때 콜백을 호출합니다.
            }
        })
    }
}