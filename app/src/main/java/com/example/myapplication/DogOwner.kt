package com.example.myapplication
data class DogOwner(
    val name: String,
    val email: String,
    val phoneNumber:String,
    val dogName:String,
    val dogBreed:String,
    val dogAge:Int,
    var detailsCompleted: Boolean = false
)
