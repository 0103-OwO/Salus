package com.example.salus.vista

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.salus.R
import com.example.salus.contrato.contratoLogin
import com.example.salus.modelo.apiService
import com.example.salus.modelo.loginResponse
import com.example.salus.presentador.presentadorLogin
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class vistaLogin : AppCompatActivity(), contratoLogin.LoginView {
    private lateinit var presenter: contratoLogin.LoginPresenter
    private lateinit var tilUsuario: TextInputEditText
    private lateinit var tilContrasena: TextInputEditText
    private lateinit var btnEntrar: MaterialButton
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        tilUsuario = findViewById(R.id.etUsuario)
        tilContrasena = findViewById(R.id.etContrasena)
        btnEntrar = findViewById(R.id.btnEntrar)
        progressBar = ProgressBar(this).apply { visibility = View.GONE }

        presenter = presentadorLogin(this, apiService.create())


        btnEntrar.setOnClickListener {
            val usuario = tilUsuario.text.toString().trim()
            val contrasena = tilContrasena.text.toString().trim()
            if (usuario.isNotEmpty() && contrasena.isNotEmpty()) {
                presenter.login(usuario, contrasena)
            } else {
                Toast.makeText(this, "Ingrese usuario y contraseña", Toast.LENGTH_SHORT).show()
            }
        }
        val btnRegistrar: Button = findViewById(R.id.btnRegistrarse)
        btnRegistrar.setOnClickListener {
            val intent = Intent(this, vistaRegistro::class.java)
            startActivity(intent)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets

        }
    }
    override fun showProgress() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progressBar.visibility = View.GONE
    }

    override fun onLoginSuccess(response: loginResponse) {
        Toast.makeText(this, "Bienvenido ${response.nombre}", Toast.LENGTH_SHORT).show()

        guardarSesion(response)
        when(response.tipo) {
            "medico" -> {
                val intent = Intent(this, vistaHomeMedico::class.java)
                intent.putExtra("NOMBRE_USUARIO", response.nombre)
                intent.putExtra("ID_MEDICO", response.id)
                startActivity(intent)
                finish()
            }
            "paciente" -> {
                val intent = Intent(this, vistaHomePacientes::class.java)
                intent.putExtra("NOMBRE_USUARIO", response.nombre)
                intent.putExtra("ID_PACIENTE", response.id)
                startActivity(intent)
                finish()
            }
            else -> {
                Toast.makeText(this, "Tipo de usuario desconocido", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun guardarSesion(response: loginResponse) {
        val prefs = getSharedPreferences("MiApp", MODE_PRIVATE)
        prefs.edit().apply {
            putInt("id_paciente", response.id ?: 0)
            putInt("id_medico", response.id ?: 0)
            putString("nombre", response.nombre)
            putString("tipo", response.tipo)
            putString("token", response.token)
            putInt("rol", response.rol ?: 0)
            putBoolean("sesion_activa", true)
            apply()
        }
    }

    override fun onLoginError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }
}