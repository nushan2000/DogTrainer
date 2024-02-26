package com.example.myapplication

object UserDatabase {
    val users = mutableListOf<User>()

    fun addUser(user: User) {
        users.add(user)
    }

    fun getUser(email: String): User? {
        return users.find { it.email == email }
    }
}