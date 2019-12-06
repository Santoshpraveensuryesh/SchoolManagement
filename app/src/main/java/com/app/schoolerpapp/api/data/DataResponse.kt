package com.app.schoolerpapp.api.data

import com.app.schoolerpapp.model.*
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class DataResponse {
    @SerializedName("response")
    @Expose
    internal var response: Int = 0

    @SerializedName("message")
    @Expose
    internal var message: String = ""

    @SerializedName("parentdetails")
    @Expose
    internal var parentDetails = ArrayList<UserData>()

    @SerializedName("mychild")
    @Expose
    internal var myChilds = ArrayList<Student>()

    @SerializedName("mychilddetails")
    @Expose
    internal var myChildDetails = ArrayList<Student>()

    @SerializedName("timeline")
    @Expose
    internal var timeLineList = ArrayList<TimeLine>()

    @SerializedName("exam")
    @Expose
    internal var examList = ArrayList<Exam>()

//    @SerializedName("due_fee")
//    @Expose
//    internal var dueFeeList=ArrayList<StudentFees>()

    fun getResponse(): Int {
        return response
    }

    fun setResponse(response: Int) {
        this.response = response
    }
}