package com.example.apppeliculas

import  androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import  android.view.View
import  android.view.ViewGroup
import  android.widget.ImageView
import  android.widget.TextView


class peliculasAdaptador(val contexto:Context,val listaPeliculas:List<clsPeliculas>):RecyclerView.Adapter<peliculasAdaptador.peliculaViewHolder>() {
    class peliculaViewHolder(control:View):RecyclerView.ViewHolder(control) {
        //aqui especificamos los controles del layout
        val imgPelicula:ImageView = control.findViewById(R.id.imgFoto)
        val txtNombre: TextView = control.findViewById(R.id.txtNombre)
        val txtDescripcion: TextView = control.findViewById(R.id.txtDescripcion)
        //val txtSinopsis:TextView=control.findViewById(R.id.txtSinopsis)
    }

    override fun onCreateViewHolder(parent:ViewGroup,viewType:Int):peliculaViewHolder{
        //aqui haremos el vinculo con el layout pelicula_layout.xml
        val view = LayoutInflater.from(parent.context).inflate(R.layout.pelicula_layout,parent,false)
        return peliculaViewHolder(view)
    }

    override fun getItemCount():Int {
        //aqui retornaremos el tamaño de la lista
        return listaPeliculas.size
    }

    override fun onBindViewHolder(holder: peliculaViewHolder, position: Int) {
        //aqui extraeremos los datos de la lista y los colocamos en cada control del layout
        val pelicula = listaPeliculas[position]
        holder.txtNombre.text = pelicula.comida
        holder.txtDescripcion.text = pelicula.descripcion

        // Cargar la imagen usando Glide
        Glide.with(contexto)
            //.load("http://172.20.10.11/Comidas/imagenes/" +pelicula.imagen) // Usar la URL de la imagen
            .load("https://darkslategray-bison-304007.hostingersite.com/Comidas/imagenes/" +pelicula.imagen) // Usar la URL de la imagen
            .into(holder.imgPelicula)
        holder.imgPelicula.setOnClickListener {
            verDetalle(pelicula)
        }
    }
    fun verDetalle(pelicula:clsPeliculas)
    {
        //aqui llamar al activity detalle
        val intent = Intent(contexto, VistaDetalle::class.java).apply{
            putExtra("pelicula_id", pelicula.idcomida)
            putExtra("pelicula_nombre", pelicula.comida)
            putExtra("pelicula_descripcion", pelicula.descripcion)
            putExtra("pelicula_sinopsis", pelicula.ingredientes)
            putExtra("pelicula_imagen", pelicula.imagen)
            putExtra("pelicula_video", pelicula.video) // Enviamos el nombre del archivo de video

        }
        contexto.startActivity(intent)
    }
}