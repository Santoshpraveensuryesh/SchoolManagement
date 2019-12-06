package com.app.schoolerpapp.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class UserData() : Parcelable {
    @SerializedName("id")
    @Expose
    var id: String = ""
    @SerializedName("type")
    @Expose
    var type: String = ""
    @SerializedName("name")
    @Expose
    var userName: String = ""
    @SerializedName("contactno")
    @Expose
    var mobile: String = ""
    @SerializedName("photo")
    @Expose
    var photo: String = ""
    var otp: String = ""
    var password: String = ""
    var countryCode: String = ""
    var language: String = "English"
    var deviceId: String = ""
    var fcmToken: String = ""

    constructor(parcel: Parcel) : this() {
        id = parcel.readString().toString()
        type = parcel.readString().toString()
        userName = parcel.readString().toString()
        mobile = parcel.readString().toString()
        photo = parcel.readString().toString()
        otp = parcel.readString().toString()
        password = parcel.readString().toString()
        countryCode = parcel.readString().toString()
        language = parcel.readString().toString()
        deviceId = parcel.readString().toString()
        fcmToken = parcel.readString().toString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(type)
        parcel.writeString(userName)
        parcel.writeString(mobile)
        parcel.writeString(photo)
        parcel.writeString(otp)
        parcel.writeString(password)
        parcel.writeString(countryCode)
        parcel.writeString(language)
        parcel.writeString(deviceId)
        parcel.writeString(fcmToken)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<UserData> {
        override fun createFromParcel(parcel: Parcel): UserData {
            return UserData(parcel)
        }

        override fun newArray(size: Int): Array<UserData?> {
            return arrayOfNulls(size)
        }
    }

}