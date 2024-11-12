/*
package com.example.appmultimediak2

import android.media.MediaPlayer
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

    lateinit var currentMediaPlayer: MediaPlayer;
    var isPlaying: Boolean = false;
    lateinit var videoView: VideoView;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        videoView = findViewById(R.id.videoView2);
        val mediaController = MediaController(this)
        videoView.setMediaController(mediaController);
    }

    private fun playSound(soundResource: Int) {
        if (isPlaying) return

        currentMediaPlayer = MediaPlayer.create(this, soundResource)
        currentMediaPlayer.setOnCompletionListener { mp: MediaPlayer? ->
            isPlaying = false
            currentMediaPlayer.release()
        }
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

        val path = Uri.parse("android.resource://${packageName}/${R.raw.video01}")

        videoView.setVideoURI(path)
        videoView.setOnCompletionListener { mp: MediaPlayer? -> isPlaying = false }
        isPlaying = true
        videoView.start()
    }
}
*/

package com.example.appmultimediak2

import android.media.MediaPlayer
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

    private var currentMediaPlayer: MediaPlayer? = null
    private var isPlaying: Boolean = false
    private lateinit var videoView: VideoView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        videoView = findViewById(R.id.videoView2)
        val mediaController = MediaController(this)
        videoView.setMediaController(mediaController)
    }

    private fun playSound(soundResource: Int) {
        // Detener y liberar el MediaPlayer actual si está reproduciendo
        if (currentMediaPlayer != null && currentMediaPlayer!!.isPlaying) {
            currentMediaPlayer!!.stop()
            currentMediaPlayer!!.release()
            currentMediaPlayer = null
            isPlaying = false
        }

        // Detener el VideoView si está reproduciendo
        if (videoView.isPlaying) {
            videoView.stopPlayback()
            isPlaying = false
        }

        // Crear y reproducir el nuevo sonido
        currentMediaPlayer = MediaPlayer.create(this, soundResource)
        currentMediaPlayer?.setOnCompletionListener {
            isPlaying = false
            currentMediaPlayer?.release()
            currentMediaPlayer = null
        }
        isPlaying = true
        currentMediaPlayer?.start()
    }

    fun sonidoGrillo(v: View?) {
        playSound(R.raw.grillos)
    }

    fun sonidoGato(v: View?) {
        playSound(R.raw.gato)
    }

    fun sonidoPerro(v: View?) {
        playSound(R.raw.perro)
    }

    fun reproducirVideo(v: View?) {
        // Detener y liberar el MediaPlayer actual si está reproduciendo
        if (currentMediaPlayer != null && currentMediaPlayer!!.isPlaying) {
            currentMediaPlayer!!.stop()
            currentMediaPlayer!!.release()
            currentMediaPlayer = null
            isPlaying = false
        }

        // Detener el VideoView si está reproduciendo
        if (videoView.isPlaying) {
            videoView.stopPlayback()
            isPlaying = false
        }

        // Configurar y reproducir el nuevo video
        val path = Uri.parse("android.resource://${packageName}/${R.raw.video01}")
        videoView.setVideoURI(path)
        videoView.setOnCompletionListener {
            isPlaying = false
        }
        isPlaying = true
        videoView.start()
    }
}

