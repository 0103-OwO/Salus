package com.example.salus.vista

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.salus.R
import com.example.salus.modelo.Contacto
import com.example.salus.modelo.Imagenes

class vistaHomeMedico : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_vista_home_medico)

        val nombreUsuario = intent.getStringExtra("NOMBRE_USUARIO") ?: "Médico"
        val idMedico = intent.getIntExtra("ID_MEDICO", 0)

        val txtNombreUsuario: TextView = findViewById(R.id.txtNombreUsuario)
        txtNombreUsuario.text = nombreUsuario

        val btnVerCitas: Button = findViewById(R.id.btnVerCitas)
        btnVerCitas.setOnClickListener {
            val intent = Intent(this, CitasMedicoActivity::class.java)
            intent.putExtra("NOMBRE_USUARIO", nombreUsuario)
            intent.putExtra("ID_MEDICO", idMedico)
            startActivity(intent)}

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}