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

    fun getUserSchedules(userId: String,
                         schedulesData: MutableLiveData<ArrayList<CalendarDaySchedule>?>) {
        usersRef.child(userId).child("schedules").addValueEventListener(
            object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // 스케줄 목록을 저장할 리스트 초기화
                val userScheduleIds = ArrayList<String>()

                // 스케줄 ID 목록 추출
                // snapshot은 선택된 user가 갖고 있는 schedules(id 형태)의 배열
                snapshot.children.forEach { child ->
                    child.key?.let { key ->
                        if (child.value == true) {
                            userScheduleIds.add(key)
                        }
                    }
                }
                // Tasks.whenAllComplete를 통한 비동기작업에 사용
                val tasks = ArrayList<Task<DataSnapshot>>()
                val userSchedules = ArrayList<CalendarDaySchedule>()

                userScheduleIds.forEach { scheduleId ->
                    // Task 객체: Firebase SDK에서 제공하는 비동기 작업을 나타내는 객체
                    val task = scheduleRef.child(scheduleId).get()
                    tasks.add(task)
                    task.addOnSuccessListener { dataSnapshot ->
                        // DataSnapshot 객체를 CalendarDaySchedule로 변환하여 저장
                        val schedule = dataSnapshot.getValue(CalendarDaySchedule::class.java)
                        if(schedule != null) {
                            userSchedules.add(schedule)
                        }
                    }
                }

                // tasks 리스트가 가리키는 Task 객체들이 모두 완료되었을 때 실행
                Tasks.whenAllComplete(tasks).addOnCompleteListener {
                    schedulesData.postValue(userSchedules)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // 데이터 가져오기를 실패했거나 취소되었을 때 콜백을 호출합니다.
            }
        })
    }

    fun postSchedule(scheduleValue: CalendarDaySchedule){
        val newScheduleRef = scheduleRef.push()
        newScheduleRef.setValue(scheduleValue)
        val newUserScheduleRef = usersRef.child(scheduleValue.createdBy.toString())
            .child("schedules")
            .child(newScheduleRef.key!!)
            .setValue(true)
    }
}