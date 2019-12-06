package com.app.schoolerpapp.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Fees() : Parcelable {
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

    @SerializedName("fee_groups_feetype_id")
    @Expose
    var feeGroupsFeetypeid = ""

    @SerializedName("due_date")
    @Expose
    var dueDate = ""

    @SerializedName("fee_groups_id")
    @Expose
    var feeGroupsId = ""

    @SerializedName("name")
    @Expose
    var name = ""

    @SerializedName("feetype_id")
    @Expose
    var feetypeId = ""

    @SerializedName("code")
    @Expose
    var code = ""

    @SerializedName("type")
    @Expose
    var type = ""

    @SerializedName("student_fees_deposite_id")
    @Expose
    var studentFeesDepositeId = ""

    @SerializedName("amount_detail")
    @Expose
    var amountDetailList = ArrayList<AmountDetails>()

    var status ="0"

    constructor(parcel: Parcel) : this() {
        id = parcel.readString().toString()
        isSystem = parcel.readString().toString()
        studentSessionId = parcel.readString().toString()
        feeSessionGroupId = parcel.readString().toString()
        amount = parcel.readString().toString()
        isActive = parcel.readString().toString()
        createdAt = parcel.readString().toString()
        feeGroupsFeetypeid = parcel.readString().toString()
        dueDate = parcel.readString().toString()
        feeGroupsId = parcel.readString().toString()
        name = parcel.readString().toString()
        feetypeId = parcel.readString().toString()
        code = parcel.readString().toString()
        type = parcel.readString().toString()
        studentFeesDepositeId = parcel.readString().toString()
        status = parcel.readString().toString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(isSystem)
        parcel.writeString(studentSessionId)
        parcel.writeString(feeSessionGroupId)
        parcel.writeString(amount)
        parcel.writeString(isActive)
        parcel.writeString(createdAt)
        parcel.writeString(feeGroupsFeetypeid)
        parcel.writeString(dueDate)
        parcel.writeString(feeGroupsId)
        parcel.writeString(name)
        parcel.writeString(feetypeId)
        parcel.writeString(code)
        parcel.writeString(type)
        parcel.writeString(studentFeesDepositeId)
        parcel.writeString(status)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Fees> {
        override fun createFromParcel(parcel: Parcel): Fees {
            return Fees(parcel)
        }

        override fun newArray(size: Int): Array<Fees?> {
            return arrayOfNulls(size)
        }
    }

}