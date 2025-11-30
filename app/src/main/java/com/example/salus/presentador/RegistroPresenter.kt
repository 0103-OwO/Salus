package com.example.salus.presentador

import android.util.Log
import android.util.Patterns
import com.example.salus.contrato.RegistroContract
import com.example.salus.modelo.RegistroRequest
import com.example.salus.modelo.RegistroResponse
import com.example.salus.modelo.apiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegistroPresenter(private var view: RegistroContract.View?) : RegistroContract.Presenter  {
    private val ApiService = apiService.create()

    override fun registrarPaciente(
        curp: String,
        nombre: String,
        apellidoPaterno: String,
        apellidoMaterno: String,
        fechaNacimiento: String,
        email: String,
        usuario: String,
        contrasena: String
    ) {
        if (!validarCampos(curp, nombre, apellidoPaterno, apellidoMaterno,
                fechaNacimiento, email, usuario, contrasena)) {
            return
        }

        view?.mostrarCargando()

        val request = RegistroRequest(
            curp = curp.uppercase(),
            nombre = nombre.trim(),
            apellido_paterno = apellidoPaterno.trim(),
            apellido_materno = apellidoMaterno.trim(),
            fecha_nacimiento = fechaNacimiento,
            email = email.trim().lowercase(),
            usuario = usuario.trim(),
            contrasena = contrasena
        )

        Log.d("RegistroPresenter", "Enviando registro: $request")

        ApiService.registrarPaciente(request).enqueue(object : Callback<RegistroResponse> {
            override fun onResponse(
                call: Call<RegistroResponse>,
                response: Response<RegistroResponse>
            ) {
                view?.ocultarCargando()

                if (response.isSuccessful) {
                    val body = response.body()
                    Log.d("RegistroPresenter", "Respuesta: $body")

                    if (body != null && body.status == "ok") {
                        view?.onRegistroExitoso(body)
                    } else {
                        view?.onRegistroError(body?.msg ?: "Error en el registro")
                    }
                } else {
                    Log.e("RegistroPresenter", "Error del servidor: ${response.code()}")
                    view?.onRegistroError("Error del servidor: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<RegistroResponse>, t: Throwable) {
                view?.ocultarCargando()
                Log.e("RegistroPresenter", "Error de conexión: ${t.message}")
                view?.onRegistroError("Error de conexión: ${t.message}")
            }
        })
    }

    override fun validarCampos(
        curp: String,
        nombre: String,
        apellidoPaterno: String,
        apellidoMaterno: String,
        fechaNacimiento: String,
        email: String,
        usuario: String,
        contrasena: String
    ): Boolean {
        // Validar CURP
        if (curp.isBlank()) {
            view?.mostrarErrorCampo("curp", "El CURP es obligatorio")
            return false
        }
        if (curp.length != 18) {
            view?.mostrarErrorCampo("curp", "El CURP debe tener 18 caracteres")
            return false
        }

        // Validar nombre
        if (nombre.isBlank()) {
            view?.mostrarErrorCampo("nombre", "El nombre es obligatorio")
            return false
        }

        // Validar apellido paterno
        if (apellidoPaterno.isBlank()) {
            view?.mostrarErrorCampo("apellido_paterno", "El apellido paterno es obligatorio")
            return false
        }

        // Validar apellido materno
        if (apellidoMaterno.isBlank()) {
            view?.mostrarErrorCampo("apellido_materno", "El apellido materno es obligatorio")
            return false
        }

        // Validar fecha de nacimiento
        if (fechaNacimiento.isBlank()) {
            view?.mostrarErrorCampo("fecha_nacimiento", "La fecha de nacimiento es obligatoria")
            return false
        }

        // Validar email
        if (email.isBlank()) {
            view?.mostrarErrorCampo("email", "El correo es obligatorio")
            return false
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            view?.mostrarErrorCampo("email", "El correo no es válido")
            return false
        }

        if (usuario.isBlank()) {
            view?.mostrarErrorCampo("usuario", "El nombre de usuario es obligatorio")
            return false
        }
        if (usuario.length < 4) {
            view?.mostrarErrorCampo("usuario", "El usuario debe tener al menos 4 caracteres")
            return false
        }

        if (contrasena.isBlank()) {
            view?.mostrarErrorCampo("contrasena", "La contraseña es obligatoria")
            return false
        }
        if (contrasena.length < 8) {
            view?.mostrarErrorCampo("contrasena", "La contraseña debe tener al menos 8 caracteres")
            return false
        }

        return true
    }

    override fun onDestroy() {
        view = null
    }
}