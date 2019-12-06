package com.app.schoolerpapp.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class TimeLine() : Parcelable {
    @SerializedName("id")
    @Expose
    var id: String = ""

    @SerializedName("student_id")
    @Expose
    var studentId: String = ""

    @SerializedName("title")
    @Expose
    var title: String = ""

    @SerializedName("timeline_date")
    @Expose
    var timelineDate: String = ""

    @SerializedName("description")
    @Expose
    var description: String = ""

    @SerializedName("document")
    @Expose
    var document: String = ""

    @SerializedName("status")
    @Expose
    var status: String = ""

    @SerializedName("date")
    @Expose
    var date: String = ""

    constructor(parcel: Parcel) : this() {
        id = parcel.readString().toString()
        studentId = parcel.readString().toString()
        title = parcel.readString().toString()
        timelineDate = parcel.readString().toString()
        description = parcel.readString().toString()
        document = parcel.readString().toString()
        status = parcel.readString().toString()
        date = parcel.readString().toString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(studentId)
        parcel.writeString(title)
        parcel.writeString(timelineDate)
        parcel.writeString(description)
        parcel.writeString(document)
        parcel.writeString(status)
        parcel.writeString(date)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TimeLine> {
        override fun createFromParcel(parcel: Parcel): TimeLine {
            return TimeLine(parcel)
        }

        override fun newArray(size: Int): Array<TimeLine?> {
            return arrayOfNulls(size)
        }
    }
}