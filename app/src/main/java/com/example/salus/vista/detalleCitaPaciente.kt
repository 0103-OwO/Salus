package com.example.salus.vista

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.salus.R
import com.example.salus.contrato.DetalleCitaContract
import com.example.salus.presentador.DetalleCitaPresenter
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.example.salus.modelo.DetalleCita

class detalleCitaPaciente : AppCompatActivity(), DetalleCitaContract.View {

    private lateinit var presenter: DetalleCitaPresenter
    private lateinit var btnMasDetalles: Button
    private lateinit var txtEstadoCita: TextView
    private lateinit var txtFechaCita: TextView
    private lateinit var txtHoraCita: TextView
    private lateinit var txtMedicoCita: TextView
    private lateinit var txtEspecialidad: TextView
    private lateinit var txtConsultorioCita: TextView
    private lateinit var txtDescripcionCita: TextView
    private lateinit var txtNombreUsuario: TextView
    private lateinit var progressBar: ProgressBar

    private var idCita: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detalle_cita_paciente)
        inicializarVistas()

        idCita = intent.getIntExtra("ID_CITA", 0)
        val nombreUsuario = intent.getStringExtra("NOMBRE_USUARIO") ?: "Paciente"

        txtNombreUsuario.text = nombreUsuario

        btnMasDetalles.setOnClickListener {
            if (idCita > 0) {
                abrirHistorialPaciente(idCita, nombreUsuario)
            } else {
                mostrarError("No se puede ver el historial, ID de cita no válido.")
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.frmDetalleCitaPaciente)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        presenter = DetalleCitaPresenter(this)

        if (idCita > 0) {
            presenter.cargarDetalleCita(idCita)
        } else {
            mostrarError("ID de cita inválido")
        }
    }

    private fun inicializarVistas() {
        progressBar = findViewById(R.id.progressBar)
        txtEstadoCita = findViewById(R.id.txtEstadoCita)
        txtFechaCita = findViewById(R.id.txtFechaCita)
        txtHoraCita = findViewById(R.id.txtHoraCita)
        txtMedicoCita = findViewById(R.id.txtMedicoCita)
        txtEspecialidad = findViewById(R.id.txtEspecialidad)
        txtConsultorioCita = findViewById(R.id.txtConsultorioCita)
        txtDescripcionCita = findViewById(R.id.txtDescripcionCita)
        txtNombreUsuario = findViewById(R.id.txtNombreUsuario)
        btnMasDetalles = findViewById(R.id.btnMasDetalles)
    }

    override fun mostrarDetalleCita(cita: DetalleCita) {
        txtEstadoCita.text = cita.estado ?: "Pendiente"
        txtFechaCita.text = formatearFecha(cita.fecha)
        txtHoraCita.text = formatearHora(cita.hora)
        txtMedicoCita.text = cita.nombre_medico
        txtEspecialidad.text = cita.especialidad ?: "No especificado"
        txtConsultorioCita.text = cita.consultorio
        txtDescripcionCita.text = cita.descripcion ?: "Sin descripción"
    }
    private fun abrirHistorialPaciente(idCita: Int, nombreUsuario: String) {
        val intent = Intent(this, vistaHistorialPaciente::class.java).apply {
            putExtra("ID_CITA_HISTORIAL", idCita)
            putExtra("NOMBRE_USUARIO", nombreUsuario)
        }
        startActivity(intent)
    }

    override fun mostrarError(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()
    }

    override fun mostrarCargando() {
        progressBar.visibility = View.VISIBLE
    }

    override fun ocultarCargando() {
        progressBar.visibility = View.GONE
    }

    private fun formatearFecha(fecha: String): String {
        // Formato: 2024-12-05 -> 05/12/2024
        val partes = fecha.split("-")
        return if (partes.size == 3) {
            "${partes[2]}/${partes[1]}/${partes[0]}"
        } else {
            fecha
        }
    }

    private fun formatearHora(hora: String): String {
        // Formato: 10:00:00 -> 10:00 a.m.
        val partes = hora.split(":")
        if (partes.size >= 2) {
            val horas = partes[0].toIntOrNull() ?: 0
            val minutos = partes[1]
            val ampm = if (horas < 12) "a.m." else "p.m."
            val horas12 = if (horas == 0) 12 else if (horas > 12) horas - 12 else horas
            return "$horas12:$minutos $ampm"
        }
        return hora
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }
}