package com.example.salus.presentador

import android.util.Log
import com.example.salus.contrato.DetalleCitaContract
import com.example.salus.modelo.DetalleCitaResponse
import com.example.salus.modelo.apiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetalleCitaPresenter(private var view: DetalleCitaContract.View?) :
    DetalleCitaContract.Presenter {
    private val ApiService = apiService.Companion.create()

    override fun cargarDetalleCita(idCita: Int) {
        view?.mostrarCargando()

        Log.d("DetalleCitaPresenter", "Cargando detalle de cita: $idCita")

        ApiService.obtenerDetalleCita(idCita).enqueue(object : Callback<DetalleCitaResponse> {
            override fun onResponse(
                call: Call<DetalleCitaResponse>,
                response: Response<DetalleCitaResponse>
            ) {
                view?.ocultarCargando()

                if (response.isSuccessful) {
                    val detalle = response.body()
                    Log.d("DetalleCitaPresenter", "Respuesta: $detalle")

                    if (detalle != null && detalle.success && detalle.cita != null) {
                        view?.mostrarDetalleCita(detalle.cita)
                    } else {
                        view?.mostrarError(detalle?.error ?: "No se encontró la cita")
                    }
                } else {
                    Log.e("DetalleCitaPresenter", "Error del servidor: ${response.code()}")
                    view?.mostrarError("Error del servidor: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<DetalleCitaResponse>, t: Throwable) {
                view?.ocultarCargando()
                Log.e("DetalleCitaPresenter", "Error de conexión: ${t.message}")
                view?.mostrarError("Error de conexión: ${t.message}")
            }
        })
    }

    override fun onDestroy() {
        view = null
    }
}