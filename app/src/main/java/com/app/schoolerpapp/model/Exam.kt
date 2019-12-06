package com.app.schoolerpapp.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Exam() : Parcelable {
    @SerializedName("exam_name")
    @Expose
    var examType = ""

    @SerializedName("exam_result")
    @Expose
    var examResult = ArrayList<ExamResult>()

    var result = ""
    var grandTotal = ""
    var percentage = ""
    var grade = ""

    constructor(parcel: Parcel) : this() {
        examType = parcel.readString().toString()
        result = parcel.readString().toString()
        grandTotal = parcel.readString().toString()
        percentage = parcel.readString().toString()
        grade = parcel.readString().toString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(examType)
        parcel.writeString(result)
        parcel.writeString(grandTotal)
        parcel.writeString(percentage)
        parcel.writeString(grade)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Exam> {
        override fun createFromParcel(parcel: Parcel): Exam {
            return Exam(parcel)
        }

        override fun newArray(size: Int): Array<Exam?> {
            return arrayOfNulls(size)
        }
    }

}