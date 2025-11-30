package com.example.salus.vista

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.salus.R
import com.example.salus.contrato.HistorialMedicoContract
import com.example.salus.presentador.HistorialMedicoPresenter
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.example.salus.modelo.HistorialMedico

class vistaHistorialMedico : AppCompatActivity(), HistorialMedicoContract.View {
    private lateinit var presenter: HistorialMedicoPresenter
    private var idCita: Int = -1

    // Vistas del layout
    private lateinit var frmDetalleHistorialMedico: View
    private lateinit var progressBar: ProgressBar
    private lateinit var txtPacienteCita: TextView
    private lateinit var txtTArterialCita: TextView
    private lateinit var txtPesoCita: TextView
    private lateinit var txtTallaCita: TextView
    private lateinit var txtTemperaturaCita: TextView
    private lateinit var txtDescripcionCita: TextView
    private lateinit var txtNombreUsuario: TextView //
    private lateinit var btnRegresar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_vista_historial_medico)

        inicializarVistas()

        idCita = intent.getIntExtra("ID_CITA_HISTORIAL", -1)
        val nombreUsuario = intent.getStringExtra("NOMBRE_USUARIO") ?: "Médico"
        txtNombreUsuario.text = nombreUsuario

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.vista_historial_medico)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        btnRegresar.setOnClickListener {
            finish()
        }

        presenter = HistorialMedicoPresenter(this)

        if (idCita > 0) {
            presenter.cargarHistorial(idCita)
        } else {
            mostrarError("Error: ID de cita no disponible.")
        }
    }
    private fun inicializarVistas() {
        progressBar = findViewById(R.id.progressBar)
        frmDetalleHistorialMedico = findViewById(R.id.frmDetalleHistorialMedico)

        txtPacienteCita = findViewById(R.id.txtPacienteCita)
        txtTArterialCita = findViewById(R.id.txtTArterialCita)
        txtPesoCita = findViewById(R.id.txtPesoCita)
        txtTallaCita = findViewById(R.id.txtTallaCita)
        txtTemperaturaCita = findViewById(R.id.txtTemperaturaCita)
        txtDescripcionCita = findViewById(R.id.txtDescripcionCita)

        txtNombreUsuario = findViewById(R.id.txtNombreUsuario)
        btnRegresar = findViewById(R.id.btnRegresar)
    }

    override fun mostrarHistorial(historial: HistorialMedico) {
        frmDetalleHistorialMedico.visibility = View.VISIBLE
        txtPacienteCita.text = historial.nombre_paciente ?: "Desconocido"
        txtTArterialCita.text = historial.tension_arterial
        txtPesoCita.text = historial.peso
        txtTallaCita.text = historial.talla
        txtTemperaturaCita.text = historial.temperatura
        txtDescripcionCita.text = historial.descripcion
    }

    override fun mostrarMensajeVacio() {
        frmDetalleHistorialMedico.visibility = View.GONE
        Toast.makeText(this, "No hay historial disponible para esta cita.", Toast.LENGTH_LONG).show()
    }

    override fun mostrarError(mensaje: String) {
        ocultarCargando()
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()
    }

    override fun mostrarCargando() {
        progressBar.visibility = View.VISIBLE
        frmDetalleHistorialMedico.visibility = View.GONE
    }

    override fun ocultarCargando() {
        progressBar.visibility = View.GONE
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }
}