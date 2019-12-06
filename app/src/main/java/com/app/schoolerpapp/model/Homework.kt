package com.app.schoolerpapp.model

import android.os.Parcel
import android.os.Parcelable

class Homework() : Parcelable {
    var studentClass =""
    var section =""
    var subject =""
    var homeworkDate =""
    var submissionDate =""
    var evaluationDate =""
    var examDate =""
    var status =""

    constructor(parcel: Parcel) : this() {
        studentClass = parcel.readString().toString()
        section = parcel.readString().toString()
        subject = parcel.readString().toString()
        homeworkDate = parcel.readString().toString()
        submissionDate = parcel.readString().toString()
        evaluationDate = parcel.readString().toString()
        examDate = parcel.readString().toString()
        status = parcel.readString().toString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(studentClass)
        parcel.writeString(section)
        parcel.writeString(subject)
        parcel.writeString(homeworkDate)
        parcel.writeString(submissionDate)
        parcel.writeString(evaluationDate)
        parcel.writeString(examDate)
        parcel.writeString(status)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Homework> {
        override fun createFromParcel(parcel: Parcel): Homework {
            return Homework(parcel)
        }

        override fun newArray(size: Int): Array<Homework?> {
            return arrayOfNulls(size)
        }
    }

}