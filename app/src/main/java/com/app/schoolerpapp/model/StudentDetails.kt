//package com.app.schoolerpapp.model
//
//import android.os.Parcel
//import android.os.Parcelable
//import androidx.lifecycle.ViewModel
//import com.google.gson.annotations.Expose
//import com.google.gson.annotations.SerializedName
//
//class StudentDetails() :  Parcelable {
//    @SerializedName("id")
//    @Expose
//    var id: String = ""
//    @SerializedName("name")
//    @Expose
//    var studentName: String = ""
//
//    @SerializedName("transport_fees")
//    @Expose
//    var transportFees: String = ""
//
//    @SerializedName("student_session_id")
//    @Expose
//    var studentSessionId: String = ""
//
//    @SerializedName("fees_discount")
//    @Expose
//    var feesDiscount: String = ""
//
//    @SerializedName("class_id")
//    @Expose
//    var classId: String = ""
//
//    @SerializedName("class")
//    @Expose
//    var StudentClass: String = ""
//
//    @SerializedName("section_id")
//    @Expose
//    var sectionId: String = ""
//
//    @SerializedName("section")
//    @Expose
//    var section: String = ""
//
//    @SerializedName("admission_no")
//    @Expose
//    var admissionNum: String = ""
//
//    @SerializedName("roll_no")
//    @Expose
//    var rollNo: String = ""
//
//    @SerializedName("admission_date")
//    @Expose
//    var admissionDate: String = ""
//
//    @SerializedName("firstname")
//    @Expose
//    var firstName: String = ""
//
//    @SerializedName("lastname")
//    @Expose
//    var lastName: String = ""
//
//    @SerializedName("image")
//    @Expose
//    var image: String = ""
//
//    @SerializedName("mobileno")
//    @Expose
//    var mobileno: String = ""
//
//    @SerializedName("email")
//    @Expose
//    var email: String = ""
//
//    @SerializedName("note")
//    @Expose
//    var note: String = ""
//
//    @SerializedName("religion")
//    @Expose
//    var religion: String = ""
//
//    @SerializedName("cast")
//    @Expose
//    var cast: String = ""
//
//    @SerializedName("dob")
//    @Expose
//    var dob: String = ""
//
//    @SerializedName("current_address")
//    @Expose
//    var currentAddress: String = ""
//
//    @SerializedName("previous_school")
//    @Expose
//    var previousSchool: String = ""
//
//    @SerializedName("guardian_is")
//    @Expose
//    var guardianIs: String = ""
//
//    @SerializedName("parent_id")
//    @Expose
//    var parentId: String = ""
//
//    @SerializedName("permanent_address")
//    @Expose
//    var permanentAddress: String = ""
//
//    @SerializedName("category_id")
//    @Expose
//    var categoryId: String = ""
//
//    @SerializedName("adhar_no")
//    @Expose
//    var adharNo: String = ""
//
//    @SerializedName("samagra_id")
//    @Expose
//    var samagraId: String = ""
//
//    @SerializedName("bank_account_no")
//    @Expose
//    var bankAccountNo: String = ""
//
//    @SerializedName("bank_name")
//    @Expose
//    var bankName: String = ""
//
//    @SerializedName("ifsc_code")
//    @Expose
//    var ifscCode: String = ""
//
//    @SerializedName("guardian_name")
//    @Expose
//    var guardianName: String = ""
//
//    @SerializedName("father_pic")
//    @Expose
//    var fatherPic: String = ""
//
//    @SerializedName("height")
//    @Expose
//    var height: String = ""
//
//    @SerializedName("weight")
//    @Expose
//    var weight: String = ""
//
//    @SerializedName("measurement_date")
//    @Expose
//    var measurementDate: String = ""
//
//    @SerializedName("mother_pic")
//    @Expose
//    var motherPic: String = ""
//
//    @SerializedName("guardian_pic")
//    @Expose
//    var guardianPic: String = ""
//
//    @SerializedName("guardian_relation")
//    @Expose
//    var guardianRelation: String = ""
//
//    @SerializedName("guardian_phone")
//    @Expose
//    var guardianPhone: String = ""
//
//    @SerializedName("guardian_address")
//    @Expose
//    var guardianAddress: String = ""
//
//    @SerializedName("is_active")
//    @Expose
//    var isActive: String = ""
//
//    @SerializedName("created_at")
//    @Expose
//    var createdAt: String = ""
//
//    @SerializedName("updated_at")
//    @Expose
//    var updatedAt: String = ""
//
//    @SerializedName("father_name")
//    @Expose
//    var fatherName: String = ""
//
//    @SerializedName("father_phone")
//    @Expose
//    var fatherPhone: String = ""
//
//    @SerializedName("blood_group")
//    @Expose
//    var bloodGroup: String = ""
//
//    @SerializedName("school_house_id")
//    @Expose
//    var schoolHouseId: String = ""
//
//    @SerializedName("father_occupation")
//    @Expose
//    var fatherOccupation: String = ""
//
//    @SerializedName("mother_name")
//    @Expose
//    var motherName: String = ""
//
//    @SerializedName("mother_phone")
//    @Expose
//    var motherPhone: String = ""
//
//    @SerializedName("mother_occupation")
//    @Expose
//    var motherOccupation: String = ""
//
//    @SerializedName("guardian_occupation")
//    @Expose
//    var guardianOccupation: String = ""
//
//    @SerializedName("gender")
//    @Expose
//    var gender: String = ""
//
//    @SerializedName("rte")
//    @Expose
//    var rte: String = ""
//
//    @SerializedName("guardian_email")
//    @Expose
//    var guardianEmail: String = ""
//
//    constructor(parcel: Parcel) : this() {
//        id = parcel.readString().toString()
//        studentName = parcel.readString().toString()
//        transportFees = parcel.readString().toString()
//        studentSessionId = parcel.readString().toString()
//        feesDiscount = parcel.readString().toString()
//        classId = parcel.readString().toString()
//        StudentClass = parcel.readString().toString()
//        sectionId = parcel.readString().toString()
//        section = parcel.readString().toString()
//        admissionNum = parcel.readString().toString()
//        rollNo = parcel.readString().toString()
//        admissionDate = parcel.readString().toString()
//        firstName = parcel.readString().toString()
//        lastName = parcel.readString().toString()
//        image = parcel.readString().toString()
//        mobileno = parcel.readString().toString()
//        email = parcel.readString().toString()
//        note = parcel.readString().toString()
//        religion = parcel.readString().toString()
//        cast = parcel.readString().toString()
//        dob = parcel.readString().toString()
//        currentAddress = parcel.readString().toString()
//        previousSchool = parcel.readString().toString()
//        guardianIs = parcel.readString().toString()
//        parentId = parcel.readString().toString()
//        permanentAddress = parcel.readString().toString()
//        categoryId = parcel.readString().toString()
//        adharNo = parcel.readString().toString()
//        samagraId = parcel.readString().toString()
//        bankAccountNo = parcel.readString().toString()
//        bankName = parcel.readString().toString()
//        ifscCode = parcel.readString().toString()
//        guardianName = parcel.readString().toString()
//        fatherPic = parcel.readString().toString()
//        height = parcel.readString().toString()
//        weight = parcel.readString().toString()
//        measurementDate = parcel.readString().toString()
//        motherPic = parcel.readString().toString()
//        guardianPic = parcel.readString().toString()
//        guardianRelation = parcel.readString().toString()
//        guardianPhone = parcel.readString().toString()
//        guardianAddress = parcel.readString().toString()
//        isActive = parcel.readString().toString()
//        createdAt = parcel.readString().toString()
//        updatedAt = parcel.readString().toString()
//        fatherName = parcel.readString().toString()
//        fatherPhone = parcel.readString().toString()
//        bloodGroup = parcel.readString().toString()
//        schoolHouseId = parcel.readString().toString()
//        fatherOccupation = parcel.readString().toString()
//        motherName = parcel.readString().toString()
//        motherPhone = parcel.readString().toString()
//        motherOccupation = parcel.readString().toString()
//        guardianOccupation = parcel.readString().toString()
//        gender = parcel.readString().toString()
//        rte = parcel.readString().toString()
//        guardianEmail = parcel.readString().toString()
//    }
//
//    override fun writeToParcel(parcel: Parcel, flags: Int) {
//        parcel.writeString(id)
//        parcel.writeString(studentName)
//        parcel.writeString(transportFees)
//        parcel.writeString(studentSessionId)
//        parcel.writeString(feesDiscount)
//        parcel.writeString(classId)
//        parcel.writeString(StudentClass)
//        parcel.writeString(sectionId)
//        parcel.writeString(section)
//        parcel.writeString(admissionNum)
//        parcel.writeString(rollNo)
//        parcel.writeString(admissionDate)
//        parcel.writeString(firstName)
//        parcel.writeString(lastName)
//        parcel.writeString(image)
//        parcel.writeString(mobileno)
//        parcel.writeString(email)
//        parcel.writeString(note)
//        parcel.writeString(religion)
//        parcel.writeString(cast)
//        parcel.writeString(dob)
//        parcel.writeString(currentAddress)
//        parcel.writeString(previousSchool)
//        parcel.writeString(guardianIs)
//        parcel.writeString(parentId)
//        parcel.writeString(permanentAddress)
//        parcel.writeString(categoryId)
//        parcel.writeString(adharNo)
//        parcel.writeString(samagraId)
//        parcel.writeString(bankAccountNo)
//        parcel.writeString(bankName)
//        parcel.writeString(ifscCode)
//        parcel.writeString(guardianName)
//        parcel.writeString(fatherPic)
//        parcel.writeString(height)
//        parcel.writeString(weight)
//        parcel.writeString(measurementDate)
//        parcel.writeString(motherPic)
//        parcel.writeString(guardianPic)
//        parcel.writeString(guardianRelation)
//        parcel.writeString(guardianPhone)
//        parcel.writeString(guardianAddress)
//        parcel.writeString(isActive)
//        parcel.writeString(createdAt)
//        parcel.writeString(updatedAt)
//        parcel.writeString(fatherName)
//        parcel.writeString(fatherPhone)
//        parcel.writeString(bloodGroup)
//        parcel.writeString(schoolHouseId)
//        parcel.writeString(fatherOccupation)
//        parcel.writeString(motherName)
//        parcel.writeString(motherPhone)
//        parcel.writeString(motherOccupation)
//        parcel.writeString(guardianOccupation)
//        parcel.writeString(gender)
//        parcel.writeString(rte)
//        parcel.writeString(guardianEmail)
//    }
//
//    override fun describeContents(): Int {
//        return 0
//    }
//
//    companion object CREATOR : Parcelable.Creator<StudentDetails> {
//        override fun createFromParcel(parcel: Parcel): StudentDetails {
//            return StudentDetails(parcel)
//        }
//
//        override fun newArray(size: Int): Array<StudentDetails?> {
//            return arrayOfNulls(size)
//        }
//    }
//
//}