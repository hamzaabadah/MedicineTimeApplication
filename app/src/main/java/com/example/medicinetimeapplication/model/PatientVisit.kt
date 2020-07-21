package com.example.medicinetimeapplication.model

import android.os.Parcel
import android.os.Parcelable

data class PatientVisit (var patientId:Int?, var doctorId:Int?,var visitAt:String?) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(patientId)
        parcel.writeValue(doctorId)
        parcel.writeString(visitAt)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PatientVisit> {
        override fun createFromParcel(parcel: Parcel): PatientVisit {
            return PatientVisit(parcel)
        }

        override fun newArray(size: Int): Array<PatientVisit?> {
            return arrayOfNulls(size)
        }
    }
}