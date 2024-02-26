package com.example.myapplication
data class Message(
    val senderId: String = "",
    val content: String = "",
    val timestamp: Long = System.currentTimeMillis()
)
