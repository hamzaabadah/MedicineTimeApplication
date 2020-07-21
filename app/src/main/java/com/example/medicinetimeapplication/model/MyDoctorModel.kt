package com.example.medicinetimeapplication.model

import android.os.Parcel
import android.os.Parcelable

data class MyDoctorModel(var idDoctor:Int?, var doctorName:String?,var idClinic:Int?,var clinicName:String?):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(idDoctor)
        parcel.writeString(doctorName)
        parcel.writeValue(idClinic)
        parcel.writeString(clinicName)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MyDoctorModel> {
        override fun createFromParcel(parcel: Parcel): MyDoctorModel {
            return MyDoctorModel(parcel)
        }

        override fun newArray(size: Int): Array<MyDoctorModel?> {
            return arrayOfNulls(size)
        }
    }
}