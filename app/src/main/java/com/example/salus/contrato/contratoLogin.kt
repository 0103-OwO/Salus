package com.example.salus.contrato
import com.example.salus.modelo.apiService
import com.example.salus.modelo.loginResponse
import com.example.salus.vista.vistaLogin

interface contratoLogin {
    interface LoginView {
        fun showProgress()
        fun hideProgress()
        fun onLoginSuccess(response: loginResponse)
        fun onLoginError(message: String)

    }

    // LoginPresenter.kt
    interface LoginPresenter {
        fun login(usuario: String, contrasena: String)
        fun onDestroy()
    }
}