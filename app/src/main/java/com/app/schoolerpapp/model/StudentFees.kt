package com.app.schoolerpapp.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class StudentFees() : Parcelable {
    @SerializedName("id")
    @Expose
    var id = ""

    @SerializedName("is_system")
    @Expose
    var isSystem = ""

    @SerializedName("student_session_id")
    @Expose
    var studentSessionId = ""

    @SerializedName("fee_session_group_id")
    @Expose
    var feeSessionGroupId = ""

    @SerializedName("amount")
    @Expose
    var amount = ""

    @SerializedName("is_active")
    @Expose
    var isActive = ""

    @SerializedName("created_at")
    @Expose
    var createdAt = ""

    @SerializedName("name")
    @Expose
    var name = ""

    @SerializedName("name")
    @Expose
    var feesList = ArrayList<Fees>()

    constructor(parcel: Parcel) : this() {
        id = parcel.readString().toString()
        isSystem = parcel.readString().toString()
        studentSessionId = parcel.readString().toString()
        feeSessionGroupId = parcel.readString().toString()
        amount = parcel.readString().toString()
        isActive = parcel.readString().toString()
        createdAt = parcel.readString().toString()
        name = parcel.readString().toString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(isSystem)
        parcel.writeString(studentSessionId)
        parcel.writeString(feeSessionGroupId)
        parcel.writeString(amount)
        parcel.writeString(isActive)
        parcel.writeString(createdAt)
        parcel.writeString(name)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<StudentFees> {
        override fun createFromParcel(parcel: Parcel): StudentFees {
            return StudentFees(parcel)
        }

        override fun newArray(size: Int): Array<StudentFees?> {
            return arrayOfNulls(size)
        }
    }


}