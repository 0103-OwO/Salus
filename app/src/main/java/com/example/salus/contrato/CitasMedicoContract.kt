package com.example.salus.contrato

import com.example.salus.modelo.CitaMedico

interface CitasMedicoContract {
    interface View {
        fun mostrarCitas(citas: List<CitaMedico>)
        fun mostrarError(mensaje: String)
        fun mostrarCargando()
        fun ocultarCargando()
        fun mostrarMensajeVacio()
    }

    interface Presenter {
        fun cargarCitas(idMedico: Int)
        fun onDestroy()
    }
}