package com.example.appsonidosyanimaciones

import android.media.MediaPlayer
import android.media.MediaPlayer.OnCompletionListener
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.MediaController
import android.widget.VideoView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    // Para controlar que ningún sonido o video
    // multimedia se reproduzca antes de que
    // termine la ejecución del elemento anterior
    // se necesita de OnCompletionListener para sonidos
    // y de MediaController para los videos
    private var currentMediaPlayer: MediaPlayer? = null
    private var isPlaying = false
    private var videoView: VideoView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(
            findViewById<View>(R.id.main)
        ) { v: View, insets: WindowInsetsCompat ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        videoView = findViewById<VideoView>(R.id.videoView2)
        val mediaController = MediaController(this)
        videoView.setMediaController(mediaController)
    }

    private fun playSound(soundResource: Int) {
        if (isPlaying) return

        currentMediaPlayer = MediaPlayer.create(this, soundResource)
        currentMediaPlayer.setOnCompletionListener(OnCompletionListener { mp: MediaPlayer? ->
            isPlaying = false
            currentMediaPlayer.release()
        })
        isPlaying = true
        currentMediaPlayer.start()
    }

    fun sonidoGrillo(v: View?) {
//        MediaPlayer mp = MediaPlayer.create(this, R.raw.grillos);
//        mp.start();
        playSound(R.raw.grillos)
    }

    fun sonidoGato(v: View?) {
//        MediaPlayer mp = MediaPlayer.create(this, R.raw.gato);
//        mp.start();
        playSound(R.raw.gato)
    }

    fun sonidoPerro(v: View?) {
//        MediaPlayer mp = MediaPlayer.create(this, R.raw.perro);
//        mp.start();
        playSound(R.raw.perro)
    }

    fun reproducirVideo(v: View?) {
        //        VideoView vv = (VideoView) findViewById(R.id.videoView2);
//        Uri path = Uri.parse("android.resource://" + "com.example.appsonidosyanimaciones/" + R.raw.video01);
//        vv.setVideoURI(path);
//        vv.start();

        if (isPlaying) return

        val path =
            Uri.parse("android.resource://" + "com.example.appsonidosyanimaciones/" + R.raw.video01)
        videoView!!.setVideoURI(path)
        videoView!!.setOnCompletionListener { mp: MediaPlayer? -> isPlaying = false }
        isPlaying = true
        videoView!!.start()
    }
}