package com.example.medicinetimeapplication.model

import android.os.Parcel
import android.os.Parcelable
import java.util.*

data class Patient(var id :Int?, var identity_num:Number?, var name:String?, var username:String?,
                    var mobile:Number? ,var address:String? , var birthdate:String , var gender:Gender):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        TODO("identity_num"),
        parcel.readString(),
        parcel.readString(),
        TODO("mobile"),
        parcel.readString(),
        parcel.readString().toString(),
        TODO("gender")
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeString(name)
        parcel.writeString(username)
        parcel.writeString(address)
        parcel.writeString(birthdate)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Patient> {
        override fun createFromParcel(parcel: Parcel): Patient {
            return Patient(parcel)
        }

        override fun newArray(size: Int): Array<Patient?> {
            return arrayOfNulls(size)
        }
    }
}