package com.example.salus.modelo

data class DetalleCita(
    val id_cita: Int,
    val fecha: String,
    val hora: String,
    val estado: String?,
    val descripcion: String?,
    val id_paciente: Int,
    val nombre_paciente: String,
    val curp_paciente: String,
    val id_medico: Int,
    val nombre_medico: String,
    val especialidad: String?,
    val id_consultorio: Int,
    val consultorio: String
)
