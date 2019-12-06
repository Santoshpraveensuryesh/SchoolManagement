package com.app.schoolerpapp.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class AmountDetails {
    @SerializedName("amount")
    @Expose
    var amount = ""

    @SerializedName("date")
    @Expose
    var date = ""

    @SerializedName("amount_discount")
    @Expose
    var amount_discount = ""

    @SerializedName("amount_fine")
    @Expose
    var amount_fine = ""

    @SerializedName("description")
    @Expose
    var description = ""

    @SerializedName("payment_mode")
    @Expose
    var payment_mode = ""

    @SerializedName("inv_no")
    @Expose
    var inv_no = ""

}