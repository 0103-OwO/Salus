package com.example.salus.modelo

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface apiService {
    // Login
    @POST("login.php")
    fun login(@Body request: Map<String, String>): Call<loginResponse>

    @GET("citasPaciente.php")
    fun obtenerCitas(@Query("id") id: Int): Call<respuestaCitas>
    companion object {
        fun create(): apiService {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://equipo3.grupoahost.com/Api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(apiService::class.java)
        }
    }
}


