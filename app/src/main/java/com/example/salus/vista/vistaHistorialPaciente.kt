package com.example.salus.vista

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.salus.R
import com.example.salus.contrato.HistorialPacienteContract
import com.example.salus.presentador.HistorialPacientePresenter
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.example.salus.modelo.HistorialPaciente

class vistaHistorialPaciente : AppCompatActivity(), HistorialPacienteContract.View {
    private lateinit var presenter: HistorialPacientePresenter
    private var idCita: Int = -1
    private lateinit var frmDetalleHistorialPaciente: View
    private lateinit var progressBar: ProgressBar
    private lateinit var txtTArterial: TextView
    private lateinit var txtPeso: TextView
    private lateinit var txtTalla: TextView
    private lateinit var txtTemperatura: TextView
    private lateinit var txtDescripcion: TextView
    private lateinit var txtNombreUsuario: TextView
    private lateinit var btnRegresar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_vista_historial_paciente)

        inicializarVistas()
        val regresar = findViewById<ImageView>(R.id.Regresarr)
        regresar.setOnClickListener {
            finish()
        }
        val btnRegresar = findViewById<Button>(R.id.btnRegresar)
        btnRegresar.setOnClickListener {
            finish()
        }
        idCita = intent.getIntExtra("ID_CITA_HISTORIAL", -1)
        val nombreUsuario = intent.getStringExtra("NOMBRE_USUARIO") ?: "Paciente"
        txtNombreUsuario.text = nombreUsuario

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.vista_historial_medico)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        btnRegresar.setOnClickListener {
            finish()
        }

        presenter = HistorialPacientePresenter(this)

        if (idCita > 0) {
            presenter.cargarHistorial(idCita)
        } else {
            mostrarError("Error: ID de cita no disponible.")
        }
    }

    private fun inicializarVistas() {

        progressBar = findViewById(R.id.progressBar)

        frmDetalleHistorialPaciente = findViewById(R.id.frmDetalleHistorialPaciente)

        txtTArterial = findViewById(R.id.txtTArterial)
        txtPeso = findViewById(R.id.txtPeso)
        txtTalla = findViewById(R.id.txtTalla)
        txtTemperatura = findViewById(R.id.txtTemperatura)
        txtDescripcion = findViewById(R.id.txtDescripcion)

        txtNombreUsuario = findViewById(R.id.txtNombreUsuario)
        btnRegresar = findViewById(R.id.btnRegresarr)
    }

    override fun mostrarHistorial(historial: HistorialPaciente) {
        ocultarCargando()
        frmDetalleHistorialPaciente.visibility = View.VISIBLE
        txtTArterial.text = historial.tension_arterial
        txtPeso.text = historial.peso
        txtTalla.text = historial.talla
        txtTemperatura.text = historial.temperatura
        txtDescripcion.text = historial.descripcion
    }

    override fun mostrarMensajeVacio() {
        ocultarCargando()
        frmDetalleHistorialPaciente.visibility = View.GONE
        Toast.makeText(this, "No hay historial disponible para esta cita.", Toast.LENGTH_LONG).show()
    }

    override fun mostrarError(mensaje: String) {
        ocultarCargando()
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()
    }

    override fun mostrarCargando() {
        progressBar.visibility = View.VISIBLE
        frmDetalleHistorialPaciente.visibility = View.GONE
    }

    override fun ocultarCargando() {
        progressBar.visibility = View.GONE
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }
}