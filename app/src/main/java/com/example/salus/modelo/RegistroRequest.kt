package com.example.salus.modelo

data class RegistroRequest(
    val curp: String,
    val nombre: String,
    val apellido_paterno: String,
    val apellido_materno: String,
    val fecha_nacimiento: String,
    val email: String,
    val usuario: String,
    val contrasena: String
)
