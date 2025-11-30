package com.example.salus.modelo
import java.io.Serializable
data class Contacto(
    val id_contacto: Int,
    val direccion: String,
    val correo: String,
    val telefono: String
): Serializable
