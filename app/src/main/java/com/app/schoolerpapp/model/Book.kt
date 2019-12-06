package com.app.schoolerpapp.model

import android.os.Parcel
import android.os.Parcelable

class Book() : Parcelable{
    var bookTitle=""
    var publisher=""
    var author=""
    var subject=""
    var rackNumber=""
    var qty=""
    var bookPrice=""
    var postDate=""

    constructor(parcel: Parcel) : this() {
        bookTitle = parcel.readString().toString()
        publisher = parcel.readString().toString()
        author = parcel.readString().toString()
        subject = parcel.readString().toString()
        rackNumber = parcel.readString().toString()
        qty = parcel.readString().toString()
        bookPrice = parcel.readString().toString()
        postDate = parcel.readString().toString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(bookTitle)
        parcel.writeString(publisher)
        parcel.writeString(author)
        parcel.writeString(subject)
        parcel.writeString(rackNumber)
        parcel.writeString(qty)
        parcel.writeString(bookPrice)
        parcel.writeString(postDate)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Book> {
        override fun createFromParcel(parcel: Parcel): Book {
            return Book(parcel)
        }

        override fun newArray(size: Int): Array<Book?> {
            return arrayOfNulls(size)
        }
    }
}