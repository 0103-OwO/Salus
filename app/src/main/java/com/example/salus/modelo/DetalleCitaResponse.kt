package com.example.salus.modelo

data class DetalleCitaResponse(
    val success: Boolean,
    val cita: DetalleCita?,
    val error: String? = null
)
