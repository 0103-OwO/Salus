package com.example.salus.modelo

data class respuestasCitasMedico(
    val success: Boolean,
    val citas: List<CitaMedico>,
    val total: Int,
    val error: String? = null   )
