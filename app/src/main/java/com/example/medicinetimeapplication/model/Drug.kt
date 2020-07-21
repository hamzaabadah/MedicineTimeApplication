package com.example.medicinetimeapplication.model

import android.os.Parcel
import android.os.Parcelable

data class Drug(var idDrug:Int? , var nameDrug:String?, var imgDrug:String? , var drugInfo: DrugInfo?):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString(),
        parcel.readParcelable(DrugInfo::class.java.classLoader)
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(idDrug)
        parcel.writeString(nameDrug)
        parcel.writeString(imgDrug)
        parcel.writeParcelable(drugInfo, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Drug> {
        override fun createFromParcel(parcel: Parcel): Drug {
            return Drug(parcel)
        }

        override fun newArray(size: Int): Array<Drug?> {
            return arrayOfNulls(size)
        }
    }
}