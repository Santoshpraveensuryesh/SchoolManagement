package com.app.schoolerpapp.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ExamResult {
    @SerializedName("exam_schedule_id")
    @Expose
    var examScheduleId = ""

    @SerializedName("exam_id")
    @Expose
    var examId = ""

    @SerializedName("full_marks")
    @Expose
    var fullMark = ""

    @SerializedName("passing_marks")
    @Expose
    var passMark = ""

    @SerializedName("exam_name")
    @Expose
    var subject = ""

    @SerializedName("exam_type")
    @Expose
    var examType = ""

    @SerializedName("attendence")
    @Expose
    var attendence = ""

    @SerializedName("get_marks")
    @Expose
    var getMarks = ""

    var result=""

}