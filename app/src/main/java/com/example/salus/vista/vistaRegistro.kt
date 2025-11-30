package com.example.salus.vista

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.salus.R
import com.example.salus.contrato.RegistroContract
import com.example.salus.modelo.RegistroResponse
import com.example.salus.presentador.RegistroPresenter
import java.util.Calendar

class vistaRegistro : AppCompatActivity(), RegistroContract.View  {
    private lateinit var presenter: RegistroPresenter
    private lateinit var edtCurp: EditText
    private lateinit var edtNombre: EditText
    private lateinit var edtAPaterno: EditText
    private lateinit var edtAMaterno: EditText
    private lateinit var edtFNacimiento: EditText
    private lateinit var edtCorreo: EditText
    private lateinit var edtUName: EditText
    private lateinit var edtContrasena: EditText
    private lateinit var btnGuardar: Button
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_vista_registro)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.registro)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        inicializarVistas()
        configurarDatePicker()

        presenter = RegistroPresenter(this)

        btnGuardar.setOnClickListener {
            registrar()
        }
    }
    private fun inicializarVistas() {
        edtCurp = findViewById(R.id.edtCurp)
        edtNombre = findViewById(R.id.edtNombre)
        edtAPaterno = findViewById(R.id.edtAPaterno)
        edtAMaterno = findViewById(R.id.edtAMaterno)
        edtFNacimiento = findViewById(R.id.edtFNacimiento)
        edtCorreo = findViewById(R.id.edtCorreo)
        edtUName = findViewById(R.id.edtUName)
        edtContrasena = findViewById(R.id.edtContraseña)
        btnGuardar = findViewById(R.id.btnGuardar)

        // Crear ProgressBar programáticamente si no existe en el XML
        progressBar = ProgressBar(this).apply {
            visibility = View.GONE
        }
    }

    private fun configurarDatePicker() {
        edtFNacimiento.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                this,
                { _, selectedYear, selectedMonth, selectedDay ->
                    // Formato: YYYY-MM-DD
                    val fecha = String.format(
                        "%04d-%02d-%02d",
                        selectedYear,
                        selectedMonth + 1,
                        selectedDay
                    )
                    edtFNacimiento.setText(fecha)
                },
                year,
                month,
                day
            )

            // Establecer fecha máxima (hoy)
            datePickerDialog.datePicker.maxDate = System.currentTimeMillis()

            datePickerDialog.show()
        }
    }

    private fun registrar() {
        val curp = edtCurp.text.toString()
        val nombre = edtNombre.text.toString()
        val apellidoPaterno = edtAPaterno.text.toString()
        val apellidoMaterno = edtAMaterno.text.toString()
        val fechaNacimiento = edtFNacimiento.text.toString()
        val email = edtCorreo.text.toString()
        val usuario = edtUName.text.toString()
        val contrasena = edtContrasena.text.toString()

        presenter.registrarPaciente(
            curp,
            nombre,
            apellidoPaterno,
            apellidoMaterno,
            fechaNacimiento,
            email,
            usuario,
            contrasena
        )
    }

    override fun mostrarCargando() {
        progressBar.visibility = View.VISIBLE
        btnGuardar.isEnabled = false
    }

    override fun ocultarCargando() {
        progressBar.visibility = View.GONE
        btnGuardar.isEnabled = true
    }

    override fun onRegistroExitoso(response: RegistroResponse) {
        Toast.makeText(this, response.msg, Toast.LENGTH_LONG).show()

        // Regresar al login después de 2 segundos
        android.os.Handler(android.os.Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, vistaLogin::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }, 2000)
    }

    override fun onRegistroError(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()
    }

    override fun mostrarErrorCampo(campo: String, mensaje: String) {
        when (campo) {
            "curp" -> edtCurp.error = mensaje
            "nombre" -> edtNombre.error = mensaje
            "apellido_paterno" -> edtAPaterno.error = mensaje
            "apellido_materno" -> edtAMaterno.error = mensaje
            "fecha_nacimiento" -> edtFNacimiento.error = mensaje
            "email" -> edtCorreo.error = mensaje
            "usuario" -> edtUName.error = mensaje
            "contrasena" -> edtContrasena.error = mensaje
        }
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }
}