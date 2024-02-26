package com.example.myapplication

import android.os.Parcel
import android.os.Parcelable

data class SupportRequest(
    val id: String?,
    val userId: String?,
    val reason: String?,
    val year: Int,
    val month: Int,
    val day: Int,
    val hour: Int,
    val minute: Int,
    var status: String?,
    var message: String? = null
) : Parcelable {
    constructor() : this(
        id = null,
        userId=null,
        reason = null,
        year = 0,
        month = 0,
        day = 0,
        hour = 0,
        minute = 0,
        status = "notaccept" // Set initial status to "notaccept"
    )

    constructor(parcel: Parcel) : this(
        id = parcel.readString(),
        userId = parcel.readString(),
        reason = parcel.readString(),
        year = parcel.readInt(),
        month = parcel.readInt(),
        day = parcel.readInt(),
        hour = parcel.readInt(),
        minute = parcel.readInt(),
        status = parcel.readString() ?: "notaccept"
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(reason)
        parcel.writeInt(year)
        parcel.writeInt(month)
        parcel.writeInt(day)
        parcel.writeInt(hour)
        parcel.writeInt(minute)
        parcel.writeString(status)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SupportRequest> {
        override fun createFromParcel(parcel: Parcel): SupportRequest {
            return SupportRequest(parcel)
        }

        override fun newArray(size: Int): Array<SupportRequest?> {
            return arrayOfNulls(size)
        }
    }
}
