package com.example.salus.presentador

import com.example.salus.contrato.HistorialPacienteContract
import com.example.salus.modelo.HistorialPaciente
import android.os.Handler
import android.os.Looper

class HistorialPacientePresenter(private var view: HistorialPacienteContract.View?) :
    HistorialPacienteContract.Presenter {

    private val handler = Handler(Looper.getMainLooper())

    override fun cargarHistorial(idCita: Int) {
        view?.mostrarCargando()

        Thread {
            try {
                Thread.sleep(1500)

                val historial: HistorialPaciente? = if (idCita == 1) {
                    HistorialPaciente(
                        tension_arterial = "120 / 80 mmHg",
                        peso = "75 kg",
                        talla = "1.75 m",
                        temperatura = "36.8 °C",
                        descripcion = "El paciente refiere no tener molestias y muestra mejoría."
                    )
                } else {
                    null
                }

                handler.post {
                    view?.ocultarCargando()
                    if (historial != null) {
                        view?.mostrarHistorial(historial)
                    } else {
                        view?.mostrarMensajeVacio()
                    }
                }
            } catch (e: Exception) {
                handler.post {
                    view?.ocultarCargando()
                    view?.mostrarError("Error de red o procesamiento.")
                }
            }
        }.start()
    }

    override fun onDestroy() {
        view = null
    }
}