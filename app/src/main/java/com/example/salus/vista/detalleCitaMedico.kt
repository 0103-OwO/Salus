package com.example.salus.vista

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.salus.R
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.example.salus.contrato.DetalleCitaContract
import com.example.salus.modelo.DetalleCita
import com.example.salus.presentador.DetalleCitaPresenter

class detalleCitaMedico : AppCompatActivity(), DetalleCitaContract.View  {
    private lateinit var presenter: DetalleCitaPresenter
    private lateinit var btnMasDetalles: Button
    private lateinit var txtEstadoCita: TextView
    private lateinit var txtFechaCita: TextView
    private lateinit var txtHoraCita: TextView
    private lateinit var txtPacienteCita: TextView
    private lateinit var txtConsultorioCita: TextView
    private lateinit var txtDescripcionCita: TextView
    private lateinit var txtNombreUsuario: TextView
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detalle_cita_medico)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.frmDetalleCitaMedico)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        inicializarVistas()

        presenter = DetalleCitaPresenter(this)

        val idCita = intent.getIntExtra("ID_CITA", 0)
        val nombreUsuario = intent.getStringExtra("NOMBRE_USUARIO") ?: "Médico"

        txtNombreUsuario.text = nombreUsuario

        btnMasDetalles.setOnClickListener {
            if (idCita > 0) {
                abrirDetalleHistorial(idCita, nombreUsuario)
            } else {
                mostrarError("No se puede abrir el historial, ID de cita no válido.")
            }
        }

        if (idCita > 0) {
            presenter.cargarDetalleCita(idCita)
        } else {
            mostrarError("ID de cita inválido")
        }
    }

    private fun inicializarVistas() {
        txtEstadoCita = findViewById(R.id.txtEstadoCita)
        txtFechaCita = findViewById(R.id.txtFechaCita)
        txtHoraCita = findViewById(R.id.txtHoraCita)
        txtPacienteCita = findViewById(R.id.txtPacienteCita)
        txtConsultorioCita = findViewById(R.id.txtConsultorioCita)
        txtDescripcionCita = findViewById(R.id.txtDescripcionCita)
        txtNombreUsuario = findViewById(R.id.txtNombreUsuario)
        progressBar = ProgressBar(this).apply { visibility = View.GONE }
        btnMasDetalles = findViewById(R.id.btnMasDetalles)
    }

    private fun abrirDetalleHistorial(idCita: Int, nombreMedico: String) {
        val intent = Intent(this, vistaHistorialMedico::class.java).apply {
            putExtra("ID_CITA_HISTORIAL", idCita)
            putExtra("NOMBRE_USUARIO", nombreMedico)
        }
        startActivity(intent)
    }

    override fun mostrarDetalleCita(cita: DetalleCita) {
        txtEstadoCita.text = cita.estado ?: "Pendiente"
        txtFechaCita.text = formatearFecha(cita.fecha)
        txtHoraCita.text = formatearHora(cita.hora)
        txtPacienteCita.text = cita.nombre_paciente
        txtConsultorioCita.text = cita.consultorio
        txtDescripcionCita.text = cita.descripcion ?: "Sin descripción"
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
        val partes = fecha.split("-")
        return if (partes.size == 3) {
            "${partes[2]}/${partes[1]}/${partes[0]}"
        } else {
            fecha
        }
    }

    private fun formatearHora(hora: String): String {
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