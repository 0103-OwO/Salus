package com.example.salus.modelo

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import retrofit2.Response

interface apiService {
    // Login
    @POST("login.php")
    fun login(@Body request: Map<String, String>): Call<loginResponse>

    @POST("registro.php")
    fun registrarPaciente(@Body request: RegistroRequest): Call<RegistroResponse>
    @GET("citasPaciente.php")
    fun obtenerCitas(@Query("id") idPaciente: Int): Call<CitaRepuesta>

    @GET("citasMedicos.php")
    fun obtenerCitaMedico(@Query("id") idMedico: Int): Call<respuestasCitasMedico>

    @GET("detalleCita.php")
    fun obtenerDetalleCita(@Query("id") idCita: Int): Call<DetalleCitaResponse>

    @GET("historialCita.php") //
    fun obtenerHistorialCita(@Query("idCita") idCita: Int): Call<HistorialResponse>

    companion object {
        private const val BASE_URL = "https://equipo3.grupoahost.com/Api/"

        fun create(): apiService {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(apiService::class.java)
        }
    }
}


