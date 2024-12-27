package com.example.apppeliculas

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
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

class registro : AppCompatActivity() {
    private lateinit var etNombreUsuario: EditText
    private lateinit var etEmail: EditText
    private lateinit var etPasswordRegistro: EditText
    private lateinit var btnRegistrar: Button
    private lateinit var apiService: ifaceApiService
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registro)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        etNombreUsuario = findViewById(R.id.etNombreUsuario)
        etEmail = findViewById(R.id.etEmail)
        etPasswordRegistro = findViewById(R.id.etPasswordRegistro)
        btnRegistrar = findViewById(R.id.btnRegistrar)

        val retrofit = Retrofit.Builder()
            //.baseUrl("http://172.20.10.11/Comidas/api/") // Cambia esto por tu URL base
            .baseUrl("https://darkslategray-bison-304007.hostingersite.com/Comidas/api/") // Cambia esto por tu URL base
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiService = retrofit.create(ifaceApiService::class.java)
        btnRegistrar.setOnClickListener {
            val nombreUsuario = etNombreUsuario.text.toString()
            val email = etEmail.text.toString()
            val password = etPasswordRegistro.text.toString()
            registrarUsuario(nombreUsuario, email, password)
        }
    }

    private fun registrarUsuario(nombreUsuario: String, email: String, password: String) {
        apiService.registrarUsuario(action = "registrar",nombreusuario=nombreUsuario, email = email, password = password).enqueue(object :
            Callback<List<clsDatosRespuesta>> {
            override fun onResponse(call: Call<List<clsDatosRespuesta>>, response: Response<List<clsDatosRespuesta>>) {
                if (response.isSuccessful) {
                    response.body()?.let { datos ->
                        if (datos[0].Estado == "true") {
                            // Si el registro es exitoso, regresar al login
                            Toast.makeText(this@registro, datos[0].Salida, Toast.LENGTH_SHORT).show()
                            finish()
                        } else {
                            Toast.makeText(this@registro, datos[0].Salida, Toast.LENGTH_SHORT).show()
                        }
                    } ?: run {
                        Toast.makeText(this@registro,"Respuesta vacía o en formato incorrecto",Toast.LENGTH_SHORT).show()
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    Toast.makeText(this@registro,"Error en la respuesta del servidor: $errorBody",Toast.LENGTH_LONG).show()
                }
            }
            override fun onFailure(call: Call<List<clsDatosRespuesta>>, t: Throwable) {
                Toast.makeText(this@registro, "Error en la conexión: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}

