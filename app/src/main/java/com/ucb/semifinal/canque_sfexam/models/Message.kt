package com.ucb.semifinal.canque_sfexam.models

data class Message(
    val id: String,
    val senderId: Int,
    var text: String,
    val timestamp: Long,
    var status: Int,
)
