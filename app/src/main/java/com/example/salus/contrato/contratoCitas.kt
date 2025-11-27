package com.example.salus.contrato

import com.example.salus.modelo.Cita

interface contratoCitas {
    interface VistaCitas {
        fun mostrarCitas(lista: List<Cita>)
        fun mostrarError(mensaje: String)
    }

    interface PresentadorCitas {
        fun cargarCitas(idPaciente: Int)
        fun onDestroy()
    }
}
