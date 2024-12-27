package com.example.apppeliculas

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.ui.StyledPlayerView

class reproducir : AppCompatActivity() {
    private lateinit var playerView: StyledPlayerView
    private lateinit var exoPlayer: ExoPlayer
    private lateinit var nombrePelicula: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_reproducir)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        playerView = findViewById(R.id.playerView)
        nombrePelicula = intent.getStringExtra("pelicula_video").toString()
        reproducir()
    }
    fun reproducir()
    {
        exoPlayer = ExoPlayer.Builder(this).build()
        playerView.player = exoPlayer
        playerView.useController = true
        //Constuye la URL del videl
        //val videoUri="http://172.20.10.11/Comidas/videos/$nombrePelicula"
        val videoUri="https://darkslategray-bison-304007.hostingersite.com/Comidas/videos/$nombrePelicula"
        val mediaItem = MediaItem.fromUri(videoUri)

        //Configura el video en el reproductor
        exoPlayer.setMediaItem(mediaItem)

        //Prepara y reproduce el video
        exoPlayer.prepare()
        exoPlayer.play()
    }

    override fun onPause() {
        super.onPause()
        exoPlayer.pause()
    }

    override fun onStop() {
        super.onStop()
        exoPlayer.release()
        }
    override fun onBackPressed() {
        exoPlayer.release()
        super.onBackPressed()
    }
}