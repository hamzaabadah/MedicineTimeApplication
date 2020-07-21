package com.example.medicinetimeapplication.model

import android.os.Parcel
import android.os.Parcelable

data class DrugInfo(var patientId:Int? , var drugId:Int? , var doseDrug:Int? , var numberOfUsage:Int?
                    , var notesDrug:String?):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(patientId)
        parcel.writeValue(drugId)
        parcel.writeValue(doseDrug)
        parcel.writeValue(numberOfUsage)
        parcel.writeString(notesDrug)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<DrugInfo> {
        override fun createFromParcel(parcel: Parcel): DrugInfo {
            return DrugInfo(parcel)
        }

        override fun newArray(size: Int): Array<DrugInfo?> {
            return arrayOfNulls(size)
        }
    }
}