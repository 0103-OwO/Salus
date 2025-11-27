package com.example.salus.modelo

data class loginResponse(
    val status: String,
    val tipo: String?,
    val id: Int?,
    val nombre: String?,
    val rol: Int?,
    val token: String?,
    val msg: String?
)
