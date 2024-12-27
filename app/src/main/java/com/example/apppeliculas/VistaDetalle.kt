package com.example.apppeliculas

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide

class VistaDetalle : AppCompatActivity() {
    private lateinit var imgFoto:ImageView
    private lateinit var txtNombre:TextView
    private lateinit var txtDescripcion:TextView
    private lateinit var txtSinopsis:TextView
    private lateinit var btnReproducir: Button
    private lateinit var nombrepeli:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_vista_detalle)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        imgFoto = findViewById(R.id.imgDetalle)
        txtNombre = findViewById(R.id.txtNombreDetalle)
        txtDescripcion = findViewById(R.id.txtDescripcionDetalle)
        txtSinopsis = findViewById(R.id.txtSinopsis)
        btnReproducir=findViewById(R.id.btnReproducir)
        val peliculaNombre = intent.getStringExtra("pelicula_nombre")
        val peliculaDescripcion = intent.getStringExtra("pelicula_descripcion")
        val peliculaSinopsis = intent.getStringExtra("pelicula_sinopsis")
        val peliculaImagen = intent.getStringExtra("pelicula_imagen")
        nombrepeli=intent.getStringExtra("pelicula_video").toString()
        // Asignar los datos a las vistas
        // Cargar la imagen de la película usando Glide
        Glide.with(this)
            //.load("http://172.20.10.11/Comidas/imagenes/" +peliculaImagen)
            .load("https://darkslategray-bison-304007.hostingersite.com/Comidas/imagenes/" +peliculaImagen)
            .into(imgFoto)
        //asignar los datos a la vista
        txtNombre.text = peliculaNombre
        txtDescripcion.text = peliculaDescripcion
        txtSinopsis.text = peliculaSinopsis
        btnReproducir.setOnClickListener(this::iraverpelicula)
    }
    fun iraverpelicula(v: View) {
            val intent = Intent(this,reproducir::class.java).apply {
                putExtra("pelicula_video", nombrepeli)
            }
            this.startActivity(intent)
    }
}