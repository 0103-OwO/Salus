package com.example.salus.contrato

import com.example.salus.modelo.RegistroResponse

interface RegistroContract {

    interface View {
        fun mostrarCargando()
        fun ocultarCargando()
        fun onRegistroExitoso(response: RegistroResponse)
        fun onRegistroError(mensaje: String)
        fun mostrarErrorCampo(campo: String, mensaje: String)
    }

    interface Presenter {
        fun registrarPaciente(
            curp: String,
            nombre: String,
            apellidoPaterno: String,
            apellidoMaterno: String,
            fechaNacimiento: String,
            email: String,
            usuario: String,
            contrasena: String
        )
        fun validarCampos(
            curp: String,
            nombre: String,
            apellidoPaterno: String,
            apellidoMaterno: String,
            fechaNacimiento: String,
            email: String,
            usuario: String,
            contrasena: String
        ): Boolean
        fun onDestroy()
    }
}