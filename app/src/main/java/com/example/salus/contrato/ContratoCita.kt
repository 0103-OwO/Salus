package com.example.salus.contrato

import com.example.salus.modelo.Cita

interface ContratoCita {

    interface View {
        fun mostrarCitas(citas: List<Cita>)
        fun mostrarError(mensaje: String)
        fun mostrarCargando()
        fun ocultarCargando()
        fun mostrarMensajeVacio()
    }

    interface Presenter {
        fun cargarCitas(idPaciente: Int)
        fun onDestroy()
    }
}