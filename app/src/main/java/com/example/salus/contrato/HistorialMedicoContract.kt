package com.example.salus.contrato

import com.example.salus.modelo.HistorialMedico

interface HistorialMedicoContract {
        interface View {
            fun mostrarHistorial(historial: HistorialMedico)
            fun mostrarError(mensaje: String)
            fun mostrarCargando()
            fun ocultarCargando()
            fun mostrarMensajeVacio()
        }

        interface Presenter {
            fun cargarHistorial(idCita: Int)
            fun onDestroy()
        }
}