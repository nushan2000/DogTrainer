package com.example.myapplication

import android.os.Parcel
import android.os.Parcelable

data class Task(
    val taskId: Int,
    val taskTitle: String,
    val taskModel: String,
    val taskName: String,
    val taskDetails: String,
    val taskPhotos: List<String>,
    var taskStatus: String="pending"

) : Parcelable {

    constructor() : this(0, "", "", "", "", emptyList(), "")

    constructor(parcel: Parcel) : this(
        parcel.readInt(),

        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.createStringArrayList() ?: emptyList(),
        parcel.readString() ?: "",
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(taskId)
        parcel.writeString(taskTitle)
        parcel.writeString(taskModel)
        parcel.writeString(taskName)
        parcel.writeString(taskDetails)
        parcel.writeStringList(taskPhotos)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Task> {
        override fun createFromParcel(parcel: Parcel): Task {
            return Task(parcel)
        }

        override fun newArray(size: Int): Array<Task?> {
            return arrayOfNulls(size)
        }
    }
}
