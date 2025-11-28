package com.example.salus.presentador

import com.example.salus.contrato.ContratoCita
import com.example.salus.modelo.CitaRepuesta
import com.example.salus.modelo.apiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CitaPresentador(private var view: ContratoCita.View?) : ContratoCita.Presenter {

    private val ApiService = apiService.create()

    override fun cargarCitas(idPaciente: Int) {
        view?.mostrarCargando()

        ApiService.obtenerCitas(idPaciente).enqueue(object : Callback<CitaRepuesta> {
            override fun onResponse(call: Call<CitaRepuesta>, response: Response<CitaRepuesta>) {
                view?.ocultarCargando()

                if (response.isSuccessful) {
                    val citasResponse = response.body()

                    if (citasResponse != null && citasResponse.success) {
                        if (citasResponse.citas.isEmpty()) {
                            view?.mostrarMensajeVacio()
                        } else {
                            view?.mostrarCitas(citasResponse.citas)
                        }
                    } else {
                        view?.mostrarError("No se pudieron cargar las citas")
                    }
                } else {
                    view?.mostrarError("Error del servidor: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<CitaRepuesta>, t: Throwable) {
                view?.ocultarCargando()
                view?.mostrarError("Error de conexión: ${t.message}")
            }
        })
    }

    override fun onDestroy() {
        view = null
    }
}