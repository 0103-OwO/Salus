package com.example.salus.contrato

import com.example.salus.modelo.DetalleCita

interface DetalleCitaContract {

    interface View {
    fun mostrarDetalleCita(cita: DetalleCita)
    fun mostrarError(mensaje: String)
    fun mostrarCargando()
    fun ocultarCargando()
}

    interface Presenter {
        fun cargarDetalleCita(idCita: Int)
        fun onDestroy()
    }
}