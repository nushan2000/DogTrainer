package com.example.myapplication

open class User(
    val id: String? = null,
    var name: String = "",
    var email: String = "",
    var password: String = "",
    val role:String=""

) {
    open fun login() {

    }
}
