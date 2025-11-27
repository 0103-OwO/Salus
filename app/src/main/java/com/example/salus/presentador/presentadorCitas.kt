package com.example.salus.presentador

import com.example.salus.contrato.contratoCitas
import com.example.salus.modelo.apiService
import com.example.salus.modelo.respuestaCitas
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class presentadorCitas(
    private var vista: contratoCitas.VistaCitas?,
    private val api: apiService
) : contratoCitas.PresentadorCitas {

    override fun cargarCitas(idPaciente: Int) {
        api.obtenerCitas(idPaciente).enqueue(object : Callback<respuestaCitas> {
            override fun onResponse(
                call: Call<respuestaCitas>,
                response: Response<respuestaCitas>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    vista?.mostrarCitas(response.body()!!.citas)
                } else {
                    vista?.mostrarError("Error al obtener citas")
                }
            }

            override fun onFailure(call: Call<respuestaCitas>, t: Throwable) {
                vista?.mostrarError(t.localizedMessage ?: "Error de conexión")
            }
        })
    }

    override fun onDestroy() {
        vista = null
    }
}
