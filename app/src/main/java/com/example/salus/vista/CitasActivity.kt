package com.example.salus.vista

import android.os.Bundle
import android.util.Log
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.salus.R
import com.example.salus.contrato.ContratoCita
import com.example.salus.modelo.Cita
import com.example.salus.presentador.CitaPresentador
import android.view.View
import android.widget.Toast

class CitasActivity : AppCompatActivity(), ContratoCita.View {

    private lateinit var presenter: CitaPresentador
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var tvMensajeVacio: TextView
    private lateinit var adapter: CitasAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_citas)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        supportActionBar?.title = "Mis Citas"

        Log.d("CitasActivity", "=== DATOS RECIBIDOS ===")
        Log.d("CitasActivity", "NOMBRE_USUARIO: ${intent.getStringExtra("NOMBRE_USUARIO")}")
        Log.d("CitasActivity", "ID_PACIENTE: ${intent.getIntExtra("ID_PACIENTE", -1)}")

        inicializarVistas()

        // Configurar RecyclerView
        configurarRecyclerView()

        // Inicializar presenter
        presenter = CitaPresentador(this)

        // Obtener ID del paciente desde Intent o SharedPreferences
        val idPaciente = obtenerIdPaciente()

        Log.d("CitasActivity", "ID Paciente final: $idPaciente")

        // Cargar citas
        if (idPaciente > 0) {
            Log.d("CitasActivity", " Cargando citas para paciente: $idPaciente")
            presenter.cargarCitas(idPaciente)
        } else {
            Log.e("CitasActivity", " ID de paciente inválido: $idPaciente")
            mostrarError("ID de paciente inválido. Por favor, inicia sesión nuevamente.")
        }

    }
    private fun inicializarVistas() {
        recyclerView = findViewById(R.id.recyclerViewCitas)
        progressBar = findViewById(R.id.progressBar)
        tvMensajeVacio = findViewById(R.id.tvMensajeVacio)
    }

    private fun configurarRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = CitasAdapter(emptyList())
        recyclerView.adapter = adapter
    }

    private fun obtenerIdPaciente(): Int {
        // Opción 1: Desde Intent
        val idDesdeIntent = intent.getIntExtra("ID_PACIENTE", 0)
        if (idDesdeIntent > 0) {
            Log.d("CitasActivity", " ID desde Intent: $idDesdeIntent")
            return idDesdeIntent
        }
        val prefs = getSharedPreferences("MiApp", MODE_PRIVATE)
        val idDesdePrefs = prefs.getInt("id_paciente", 0)
        Log.d("CitasActivity", "ID desde SharedPreferences: $idDesdePrefs")

        if (idDesdePrefs == 0) {
            Log.e("CitasActivity", "No se encontró ID de paciente")
        }

        return idDesdePrefs
    }

    // Implementación de CitasContract.View
    override fun mostrarCitas(citas: List<Cita>) {
        recyclerView.visibility = View.VISIBLE
        tvMensajeVacio.visibility = View.GONE
        adapter.actualizarCitas(citas)
    }

    override fun mostrarError(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()
    }

    override fun mostrarCargando() {
        progressBar.visibility = View.VISIBLE
        recyclerView.visibility = View.GONE
        tvMensajeVacio.visibility = View.GONE
    }

    override fun ocultarCargando() {
        progressBar.visibility = View.GONE
    }

    override fun mostrarMensajeVacio() {
        tvMensajeVacio.visibility = View.VISIBLE
        recyclerView.visibility = View.GONE
        tvMensajeVacio.text = "No tienes citas programadas"
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }
}