package com.example.salus.modelo

data class HistorialMedico(
    val id_historial: Int,
    val id_cita: Int,
    val tension_arterial: String,
    val peso: String,
    val talla: String,
    val temperatura: String,
    val descripcion: String,
    val nombre_paciente: String? = null
)
