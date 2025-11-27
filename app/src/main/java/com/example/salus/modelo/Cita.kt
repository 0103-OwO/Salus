package com.example.salus.modelo

data class Cita(
    val id_cita: Int,
    val id_paciente: Int,
    val id_consultorio: Int,
    val fecha: String,
    val hora: String,
    val motivo: String,
    val consultorio: String
)

