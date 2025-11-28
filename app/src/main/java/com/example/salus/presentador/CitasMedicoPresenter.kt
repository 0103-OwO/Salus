package com.example.salus.presentador

import com.example.salus.contrato.CitasMedicoContract
import android.util.Log
import com.example.salus.modelo.apiService
import com.example.salus.modelo.respuestasCitasMedico
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
class CitasMedicoPresenter(private var view: CitasMedicoContract.View?) :
    CitasMedicoContract.Presenter {

    private val ApiService = apiService.create()

    override fun cargarCitas(idMedico: Int) {
        view?.mostrarCargando()

        Log.d("CitasMedicoPresenter", "Cargando citas para médico: $idMedico")

        ApiService.obtenerCitaMedico(idMedico).enqueue(object : Callback<respuestasCitasMedico> {
            override fun onResponse(
                call: Call<respuestasCitasMedico>,
                response: Response<respuestasCitasMedico>
            ) {
                view?.ocultarCargando()

                if (response.isSuccessful) {
                    val citasResponse = response.body()
                    Log.d("CitasMedicoPresenter", "Respuesta exitosa: $citasResponse")

                    if (citasResponse != null && citasResponse.success) {
                        if (citasResponse.citas.isEmpty()) {
                            view?.mostrarMensajeVacio()
                        } else {
                            view?.mostrarCitas(citasResponse.citas)
                        }
                    } else {
                        view?.mostrarError(citasResponse?.error ?: "No se pudieron cargar las citas")
                    }
                } else {
                    Log.e("CitasMedicoPresenter", "Error del servidor: ${response.code()}")
                    view?.mostrarError("Error del servidor: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<respuestasCitasMedico>, t: Throwable) {
                view?.ocultarCargando()
                Log.e("CitasMedicoPresenter", "Error de conexión: ${t.message}")
                view?.mostrarError("Error de conexión: ${t.message}")
            }
        })
    }

    override fun onDestroy() {
        view = null
    }
}
