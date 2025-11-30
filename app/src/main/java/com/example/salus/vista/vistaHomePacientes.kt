package com.example.salus.vista

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.salus.R

class vistaHomePacientes : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_vista_home_pacientes)

        val nombreUsuario = intent.getStringExtra("NOMBRE_USUARIO") ?: "Paciente"
        val idPaciente = intent.getIntExtra("ID_PACIENTE", 0)

        val txtNombreUsuario: TextView = findViewById(R.id.txtNombreUsuario)
        txtNombreUsuario.text = nombreUsuario

        val btnVerCitas: Button = findViewById(R.id.btnVerCitas)
        btnVerCitas.setOnClickListener {
            val intent = Intent(this, CitasActivity::class.java)
            intent.putExtra("NOMBRE_USUARIO", nombreUsuario)
            intent.putExtra("ID_PACIENTE", idPaciente)
            startActivity(intent)}

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}