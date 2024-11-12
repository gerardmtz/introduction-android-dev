package com.example.appsonidosyanimaciones;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private MediaPlayer currentMediaPlayer;
    private boolean isPlaying;
    private VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        videoView = findViewById(R.id.videoView2);
        MediaController mediaController = new MediaController(this);
        videoView.setMediaController(mediaController);
    }

    private void stopCurrentMedia() {
        if (currentMediaPlayer != null && isPlaying) {
            currentMediaPlayer.stop();
            currentMediaPlayer.release();
            currentMediaPlayer = null;
        }
        if (videoView.isPlaying()) {
            videoView.stopPlayback();
        }
        isPlaying = false;
    }

    private void playSound(int soundResource) {
        stopCurrentMedia();

        currentMediaPlayer = MediaPlayer.create(this, soundResource);
        currentMediaPlayer.setOnCompletionListener(mp -> {
            isPlaying = false;
            currentMediaPlayer.release();
        });
        isPlaying = true;
        currentMediaPlayer.start();
    }

    public void sonidoGrillo(View v) {
        playSound(R.raw.grillos);
    }

    public void sonidoGato(View v) {
        playSound(R.raw.gato);
    }

    public void sonidoPerro(View v) {
        playSound(R.raw.perro);
    }

    public void reproducirVideo(View v) {
        stopCurrentMedia();

        Uri path = Uri.parse("android.resource://" + "com.example.appsonidosyanimaciones/" + R.raw.video01);
        videoView.setVideoURI(path);
        videoView.setOnCompletionListener(mp -> isPlaying = false);
        isPlaying = true;
        videoView.start();
    }
}
