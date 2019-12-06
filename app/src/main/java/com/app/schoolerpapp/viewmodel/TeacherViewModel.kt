package com.app.schoolerpapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TeacherViewModel : ViewModel() {
    private var busy: MutableLiveData<Int>?=null

    fun getBusy(): MutableLiveData<Int> {

        if (busy == null) {
            busy = MutableLiveData()
            busy!!.setValue(8)
        }

        return busy as MutableLiveData<Int>
    }
}