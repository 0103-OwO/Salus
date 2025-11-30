package com.example.salus.modelo

data class HistorialResponse(
    val success: Boolean,
    val historial: HistorialMedico?,
    val error: String? = null
)
