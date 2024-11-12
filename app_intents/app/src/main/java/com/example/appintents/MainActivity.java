package com.example.appintents;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void onClickEnviarCorreo(View v){
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("text/plain");
        i.putExtra(Intent.EXTRA_SUBJECT, "Saludar");
        i.putExtra(Intent.EXTRA_TEXT, "Hola, feliz miércoles");
        i.putExtra(Intent.EXTRA_EMAIL, new String[]{"12ignaciomartinez@gmail.com"});
        startActivity(i);
    }

    public void onClickTomarFoto(View v){
        Intent i = new Intent("android.media.action.IMAGE_CAPTURE");
        startActivity(i);
    }

    public void onClickGeolocalizacion(View v){
        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:35.624524503945025, 139.42930568073953"));
        startActivity(i);
    }

    public void onClickHacerLlamada(View v){
        Intent i = new Intent(Intent.ACTION_CALL, Uri.parse("tel:8711060231"));
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.CALL_PHONE}, 1);
        } else {
            startActivity(i);
        }
    }

    public void onClickPaginaWeb(View v){
        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com.mx"));
        startActivity(i);
    }
}
