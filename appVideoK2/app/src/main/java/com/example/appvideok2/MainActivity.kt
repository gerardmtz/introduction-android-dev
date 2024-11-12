package com.example.appvideok2

import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.VideoView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var videoView: VideoView;
    private lateinit var playVideoButton: Button;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        videoView = findViewById(R.id.videoView);
        playVideoButton = findViewById(R.id.button);

        // cargar el video
        val videoUri: Uri = Uri.parse("android.resource://"
                + "com.example.appvideok2" + "/" + R.raw.el_cacahutazo);

        videoView.setVideoURI(videoUri);
        playVideoButton.setOnClickListener { videoView.start() }
    }
}