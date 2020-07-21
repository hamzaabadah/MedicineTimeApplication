package com.example.medicinetimeapplication.model

import android.os.Parcel
import android.os.Parcelable

data class NextVisitModel (var doctor: Doctor? , var patientVisit: PatientVisit?,var clinic: Clinic?):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readParcelable(Doctor::class.java.classLoader),
        parcel.readParcelable(PatientVisit::class.java.classLoader),
        parcel.readParcelable(Clinic::class.java.classLoader)
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(doctor, flags)
        parcel.writeParcelable(patientVisit, flags)
        parcel.writeParcelable(clinic, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<NextVisitModel> {
        override fun createFromParcel(parcel: Parcel): NextVisitModel {
            return NextVisitModel(parcel)
        }

        override fun newArray(size: Int): Array<NextVisitModel?> {
            return arrayOfNulls(size)
        }
    }
}