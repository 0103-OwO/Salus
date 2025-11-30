package com.example.salus.presentador

import android.util.Log
import com.example.salus.contrato.HistorialMedicoContract
import com.example.salus.modelo.HistorialResponse
import com.example.salus.modelo.apiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HistorialMedicoPresenter(private var view: HistorialMedicoContract.View?) :
    HistorialMedicoContract.Presenter {

    private val ApiService = apiService.create()

    override fun cargarHistorial(idCita: Int) {
        view?.mostrarCargando()
        Log.d("HistorialPresenter", "Cargando historial para cita ID: $idCita")

        ApiService.obtenerHistorialCita(idCita).enqueue(object : Callback<HistorialResponse> {
            override fun onResponse(
                call: Call<HistorialResponse>,
                response: Response<HistorialResponse>
            ) {
                view?.ocultarCargando()

                if (response.isSuccessful) {
                    val historialResponse = response.body()
                    Log.d("HistorialPresenter", "Respuesta: $historialResponse")

                    if (historialResponse != null && historialResponse.success) {
                        if (historialResponse.historial != null) {
                            // Éxito: Se encontró el historial
                            view?.mostrarHistorial(historialResponse.historial)
                        } else {
                            // Éxito: API responde, pero no hay historial para esa cita
                            view?.mostrarMensajeVacio()
                        }
                    } else {
                        view?.mostrarError(historialResponse?.error ?: "Error al cargar historial")
                    }
                } else {
                    view?.mostrarError("Error del servidor: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<HistorialResponse>, t: Throwable) {
                view?.ocultarCargando()
                view?.mostrarError("Error de conexión: ${t.message}")
            }
        })
    }

    override fun onDestroy() {
        view = null
    }
}