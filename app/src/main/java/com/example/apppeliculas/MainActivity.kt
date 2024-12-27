package com.example.apppeliculas

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private lateinit var rcvLista:RecyclerView
    private lateinit var apiService: ifaceApiService//esto es la interface api

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        rcvLista = findViewById(R.id.rcvLista)
        rcvLista.layoutManager = LinearLayoutManager(this)

        // Configuración de Retrofit
        val retrofit = Retrofit.Builder()
            //.baseUrl("http://172.20.10.11/Comidas/api/") // Cambia esto por tu URL base
            .baseUrl("https://darkslategray-bison-304007.hostingersite.com/Comidas/api/") // Cambia esto por tu URL base
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient())
            .build()
        apiService = retrofit.create(ifaceApiService::class.java)

        obtenerPeliculas()
    }

    private fun obtenerPeliculas() {
        apiService.obtenerPeliculas().enqueue(object:retrofit2.Callback<List<clsPeliculas>> {
            override fun onResponse(call:Call<List<clsPeliculas>>,response:retrofit2.Response<List<clsPeliculas>>) {
                if (response.isSuccessful) {
                    response.body()?.let {peliculas->

                        val adaptador = peliculasAdaptador(this@MainActivity, peliculas)
                        rcvLista.adapter = adaptador
                    }
                } else {
                    Toast.makeText(baseContext,"Error: ${response.message()}",Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call:Call<List<clsPeliculas>>,t:Throwable) {
                Toast.makeText(baseContext, "Error:${t.message}",Toast.LENGTH_SHORT).show()
            }
        })
    }
}