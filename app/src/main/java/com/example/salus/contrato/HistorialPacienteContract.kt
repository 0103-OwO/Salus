package com.example.salus.contrato

import com.example.salus.modelo.HistorialPaciente

interface HistorialPacienteContract {
    interface View {
        fun mostrarHistorial(historial: HistorialPaciente)
        fun mostrarMensajeVacio()
        fun mostrarError(mensaje: String)
        fun mostrarCargando()
        fun ocultarCargando()
    }

    interface Presenter {
        fun cargarHistorial(idCita: Int)
        fun onDestroy()
    }
}