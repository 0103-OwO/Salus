package com.example.salus.vista

import android.content.Intent
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
import com.example.salus.contrato.CitasMedicoContract
import com.example.salus.modelo.CitaMedico
import com.example.salus.presentador.CitasMedicoPresenter
import android.view.View
import android.widget.ImageView
import android.widget.Toast

class CitasMedicoActivity : AppCompatActivity(), CitasMedicoContract.View {

    private lateinit var presenter: CitasMedicoPresenter
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var tvMensajeVacio: TextView
    private lateinit var adapter: CitasMedicoAdapter
    private lateinit var txtNombreUsuario: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_citas_medico)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        supportActionBar?.title = "Mis Citas - Médico"

        // DEBUG: Ver lo que llega en el Intent
        Log.d("CitasMedicoActivity", "=== DATOS RECIBIDOS ===")
        Log.d("CitasMedicoActivity", "NOMBRE_USUARIO: ${intent.getStringExtra("NOMBRE_USUARIO")}")
        Log.d("CitasMedicoActivity", "ID_MEDICO: ${intent.getIntExtra("ID_MEDICO", -1)}")

        inicializarVistas()
        val btnRegresar = findViewById<ImageView>(R.id.btnRegresarr)
        btnRegresar.setOnClickListener {
            finish()
        }
        val nombreUsuario = intent.getStringExtra("NOMBRE_USUARIO") ?: "Invitado"

        txtNombreUsuario.text = nombreUsuario

        configurarRecyclerView()

        presenter = CitasMedicoPresenter(this)

        val idMedico = obtenerIdMedico()

        Log.d("CitasMedicoActivity", "ID Médico final: $idMedico")

        if (idMedico > 0) {
            Log.d("CitasMedicoActivity", "Cargando citas para médico: $idMedico")
            presenter.cargarCitas(idMedico)
        } else {
            Log.e("CitasMedicoActivity", "ID de médico inválido: $idMedico")
            mostrarError("ID de médico inválido. Por favor, inicia sesión nuevamente.")
        }
    }
    private fun inicializarVistas() {
        recyclerView = findViewById(R.id.recyclerViewCitasMedico)
        progressBar = findViewById(R.id.progressBar)
        tvMensajeVacio = findViewById(R.id.tvMensajeVacio)
        txtNombreUsuario = findViewById(R.id.txtNombreUsuario)
    }

    private fun configurarRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = CitasMedicoAdapter(emptyList(),{ cita -> abrirDetalleCita(cita.id_cita) })
        recyclerView.adapter = adapter
    }
    private fun abrirDetalleCita(idCita: Int) {
        val intent = Intent(this, detalleCitaMedico::class.java).apply {
            putExtra("ID_CITA", idCita)


            val nombreUsuario = intent.getStringExtra("NOMBRE_USUARIO")
            putExtra("NOMBRE_USUARIO", nombreUsuario)
        }
        startActivity(intent)
    }
    private fun obtenerIdMedico(): Int {

        val idDesdeIntent = intent.getIntExtra("ID_MEDICO", 0)
        if (idDesdeIntent > 0) {
            Log.d("CitasMedicoActivity", "ID desde Intent: $idDesdeIntent")
            return idDesdeIntent
        }


        val prefs = getSharedPreferences("MiApp", MODE_PRIVATE)
        val idDesdePrefs = prefs.getInt("id_medico", 0)
        Log.d("CitasMedicoActivity", "ID desde SharedPreferences: $idDesdePrefs")

        if (idDesdePrefs == 0) {
            Log.e("CitasMedicoActivity", "No se encontró ID de médico")
        }

        return idDesdePrefs
    }


    override fun mostrarCitas(citas: List<CitaMedico>) {
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