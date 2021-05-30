package com.example.ft_hangouts.model

data class MessageModel(
    val id: String,
    val address: String,
    val message: String,
    val date: String,
    val received: Boolean
)