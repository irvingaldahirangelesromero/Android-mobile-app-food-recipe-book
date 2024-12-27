package com.example.apppeliculas

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class login : AppCompatActivity() {
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnAcceder: Button
    private lateinit var tvRegistrar: TextView
    private lateinit var apiService: ifaceApiService
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        etEmail = findViewById(R.id.etUsuario)
        etPassword = findViewById(R.id.etPassword)
        btnAcceder = findViewById(R.id.btnAcceder)
        tvRegistrar = findViewById(R.id.tvRegistrar)

        val retrofit = Retrofit.Builder()
            //.baseUrl("http://172.20.10.11/Comidas/api/") // Cambia esto por tu URL base
            .baseUrl("https://darkslategray-bison-304007.hostingersite.com/Comidas/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiService = retrofit.create(ifaceApiService::class.java)

        // Listener para el botón de login
        btnAcceder.setOnClickListener {
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()
            acceder(email, password)
        }
        tvRegistrar.setOnClickListener {
            val intent = Intent(this, registro::class.java)
            startActivity(intent)
        }
    }

    // Función para hacer login
    private fun acceder(email: String, password: String) {

        apiService.iniciarSesion(action="login", email = email, password = password)
            .enqueue(object : Callback<List<clsDatosRespuesta>> {
                override fun onResponse(call: Call<List<clsDatosRespuesta>>, response: Response<List<clsDatosRespuesta>>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let { datos ->

                            if (datos[0].Estado=="Correcto") {

                                val intent = Intent(this@login, MainActivity::class.java)
                                startActivity(intent)
                                finish()
                            } else {
                                Toast.makeText(this@login,"No se encontraron datos de películas.",Toast.LENGTH_SHORT).show()
                            }
                        } ?: run {
                            Toast.makeText(this@login,"Respuesta vacía o en formato incorrecto",Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        val errorBody = response.errorBody()?.string()
                        Toast.makeText(this@login,"Error en la respuesta del servidor: $errorBody",Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<List<clsDatosRespuesta>>, t: Throwable) {
                    Toast.makeText(this@login,"Error en la conexión: ${t.message}",Toast.LENGTH_SHORT).show()
                }
            })
    }
}


