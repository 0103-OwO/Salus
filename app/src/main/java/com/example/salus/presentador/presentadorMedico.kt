package com.example.salus.presentador

import com.example.salus.contrato.contratoCitasMedicos
import com.example.salus.modelo.apiService
import com.example.salus.modelo.respuestaCitas
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class presentadorCitasMedico(
    private var vista: contratoCitasMedicos.VistaCitasMedico?,
    private val api: apiService
) : contratoCitasMedicos.PresentadorCitasMedico {

    override fun cargarCitasMedico(idMedico: Int) {
        api.cargarCitasMedico(idMedico).enqueue(object : Callback<respuestaCitas> {
            override fun onResponse(call: Call<respuestaCitas>, response: Response<respuestaCitas>) {
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
