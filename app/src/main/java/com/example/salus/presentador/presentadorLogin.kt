    package com.example.salus.presentador

    import android.util.Log
    import com.example.salus.contrato.contratoLogin
    import com.example.salus.modelo.apiService
    import com.example.salus.modelo.loginResponse
    import retrofit2.Call
    import retrofit2.Callback
    import retrofit2.Response

    class presentadorLogin(
        private var view: contratoLogin.LoginView?,
        private val apiService: apiService
    ) : contratoLogin.LoginPresenter {
        override fun login(usuario: String, contrasena: String) {
            view?.showProgress()

            val request = mapOf("usuario" to usuario, "contrasena" to contrasena)

            apiService.login(request).enqueue(object : Callback<loginResponse> {
                override fun onResponse(
                    call: Call<loginResponse>,
                    response: Response<loginResponse>
                ) {
                    view?.hideProgress()

                    // --- Si la respuesta NO es exitosa ---
                    if (!response.isSuccessful) {
                        Log.e("LOGIN", "Código HTTP: ${response.code()}")
                        Log.e("LOGIN", "Error body: ${response.errorBody()?.string()}")
                        view?.onLoginError("Error en la respuesta del servidor")
                        return
                    }

                    // --- Si la respuesta es exitosa ---
                    val body = response.body()
                    Log.i("LOGIN", "Respuesta exitosa: $body")

                    if (body != null && body.status == "ok") {
                        view?.onLoginSuccess(body)
                    } else {
                        view?.onLoginError(body?.msg ?: "Error desconocido")
                    }
                }

                override fun onFailure(call: Call<loginResponse>, t: Throwable) {
                    view?.hideProgress()
                    Log.e("LOGIN", "Error de conexión: ${t.localizedMessage}")
                    view?.onLoginError(t.localizedMessage ?: "Error de conexión")
                }
            })
        }

        override fun onDestroy() {
            view = null
        }
    }
