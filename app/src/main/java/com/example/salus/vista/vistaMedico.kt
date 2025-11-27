package com.example.salus.vista

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.salus.R
import com.example.salus.contrato.contratoCitasMedicos
import com.example.salus.modelo.Cita
import com.example.salus.modelo.apiService
import com.example.salus.presentador.presentadorCitasMedico
import android.widget.ListView
import android.widget.Toast

class vistaMedico : AppCompatActivity(), contratoCitasMedicos.VistaCitasMedico  {
    private lateinit var lista: ListView
    private lateinit var presentador: contratoCitasMedicos.PresentadorCitasMedico

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lista = findViewById(R.id.listaCitas)
        presentador = presentadorCitasMedico(this, apiService.create())

        val idMedico = intent.getIntExtra("ID_USUARIO", 0)

        if (idMedico > 0) {
            presentador.cargarCitasMedico(idMedico)
        } else {
            Toast.makeText(this, "ID no válido", Toast.LENGTH_SHORT).show()
        }


        enableEdgeToEdge()
        setContentView(R.layout.activity_vista_medico)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
    override fun mostrarCitas(listaCitas: List<Cita>) {
        val adaptador = AdaptadorCitas(this, listaCitas)
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