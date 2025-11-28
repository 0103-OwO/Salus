package com.example.salus.modelo

data class CitaRepuesta(
    val success: Boolean,
    val citas: List<Cita>,
    val total: Int
)
