package com.example.salus.modelo

data class CitaMedico(
    val id_cita: Int,
    val fecha: String,
    val hora: String,
    val id_paciente: Int,
    val id_medico: Int,
    val id_consultorio: Int,
    val nombre_paciente: String,
    val curp_paciente: String,
    val consultorio: String
)
