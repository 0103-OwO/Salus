package com.example.salus.modelo

data class CitaMedico(
    val id_cita: String,
    val fecha: String,
    val hora: String,
    val motivo: String,
    val paciente: String
)
