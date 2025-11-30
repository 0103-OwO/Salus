package com.example.salus.modelo

data class RegistroResponse(
    val status: String,
    val msg: String,
    val id_paciente: Int? = null,
    val id_usuario_cliente: Int? = null
)
