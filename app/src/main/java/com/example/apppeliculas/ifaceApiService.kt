package com.example.apppeliculas

import retrofit2.http.GET
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ifaceApiService {
    @GET("apiPeliculas.php")
    fun obtenerPeliculas():Call<List<clsPeliculas>>

    //Para login y registro
    @FormUrlEncoded
    @POST("apiAcceso.php")
    fun registrarUsuario(
        @Field("action") action: String,
        @Field("nombreUsuario") nombreusuario: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<List<clsDatosRespuesta>>

    @FormUrlEncoded
    @POST("apiAcceso.php")
    fun iniciarSesion(
        @Field("action") action: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<List<clsDatosRespuesta>>
}