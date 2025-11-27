package com.example.salus.contrato
import com.example.salus.modelo.Cita

interface contratoCitasMedicos {

    interface VistaCitasMedico {
        fun mostrarCitas(lista: List<Cita>)
        fun mostrarError(mensaje: String)
    }

    interface PresentadorCitasMedico {
        fun cargarCitasMedico(idMedico: Int)
        fun onDestroy()
    }

}