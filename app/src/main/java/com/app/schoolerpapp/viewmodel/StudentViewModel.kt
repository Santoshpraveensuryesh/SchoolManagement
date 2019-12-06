package com.app.schoolerpapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.schoolerpapp.api.data.DataResponse
import com.app.schoolerpapp.model.Student

class StudentViewModel : ViewModel() {
    private var busy: MutableLiveData<Int>? = null
    private var studentLiveData: MutableLiveData<DataResponse>? = null

    fun getBusy(): MutableLiveData<Int> {

        if (busy == null) {
            busy = MutableLiveData()
            busy!!.setValue(8)
        }

        return busy as MutableLiveData<Int>
    }

    val studentDataReq: LiveData<DataResponse>
        get() {
            if (studentLiveData == null) {
                studentLiveData = MutableLiveData()
            }

            return studentLiveData as MutableLiveData<DataResponse>
        }

    fun showStudentDetails(student: Student) {
        Log.e("sdfsfsdfsdfsdf", "Sdfsdfsd")
        studentLiveData!!.value = DataResponse()
        studentLiveData=null
    }

}