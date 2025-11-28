package com.example.salus.modelo

data class Cita(
    val id_cita: Int,
    val fecha: String,
    val hora: String,
    val id_paciente: Int,
    val id_medico: Int,
    val id_consultorio: Int,
    val consultorio: String
)
