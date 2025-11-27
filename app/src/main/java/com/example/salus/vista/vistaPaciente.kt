package com.example.salus.vista

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.salus.R
import android.widget.ListView
import android.widget.Toast
import com.example.salus.contrato.contratoCitas
import com.example.salus.modelo.Cita
import com.example.salus.modelo.apiService
import com.example.salus.presentador.presentadorCitas

class vistaPaciente : AppCompatActivity(), contratoCitas.VistaCitas {

    private lateinit var presentador: presentadorCitas
    private lateinit var lista: ListView
    private lateinit var adaptador: AdaptadorCitas
    private var idPaciente: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        idPaciente = intent.getIntExtra("ID_USUARIO", 0)

        lista = findViewById(R.id.listaCitas)

        presentador = presentadorCitas(this, apiService.create())
        presentador.cargarCitas(idPaciente)

        enableEdgeToEdge()
        setContentView(R.layout.activity_vista_paciente)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun mostrarCitas(listaCitas: List<Cita>) {
        adaptador = AdaptadorCitas(this, listaCitas)
        lista.adapter = adaptador
    }

    override fun mostrarError(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        presentador.onDestroy()
        super.onDestroy()
    }
}