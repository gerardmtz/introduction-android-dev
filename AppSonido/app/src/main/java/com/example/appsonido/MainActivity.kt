package com.example.appsonido

import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    lateinit var playButton: Button;
    var mp: MediaPlayer? = null;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        playButton = findViewById(R.id.buttonPlay);

        // cargar el archivo de sonido
        mp = MediaPlayer.create(this, R.raw.dog_audio);

        playButton.setOnClickListener {
            mp?.start();
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mp?.release() // liberamos el recurso cuando la actividad se destruye
    }
}