package com.example.app_pueblo_magico;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

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

        Button button_gastronomia = findViewById(R.id.button_gastronomia);
        Button button_lugares_turisticos = findViewById(R.id.button_lugares_turisticos);
        Button button_entretenimiento = findViewById(R.id.button_entretenimiento);
        Button button_ubicacion = findViewById(R.id.button_ubicacion);

        button_gastronomia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showGatronomia();
            }
        });

        button_lugares_turisticos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLugaresTuristicos();
            }
        });

        button_entretenimiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showEntretenimiento();
            }
        });

        button_ubicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showUbicacion();
            }
        });

    } // end onCreateBundle

    private void showGatronomia() {
        Intent intent = new Intent(this, pantalla_gastronomia.class);
        startActivity(intent);
    }

    private void showLugaresTuristicos() {
        Intent intent = new Intent(this, pantalla_lugares_turisticos.class);
        startActivity(intent);
    }

    private void showEntretenimiento() {
        Intent intent = new Intent(this, pantalla_entretenimiento.class);
        startActivity(intent);
    }

    private void showUbicacion() {
//        Intent intent = new Intent(this, pantalla_ubicacion.class);
//        startActivity(intent);

        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:23.68983728717135, -100.88679942796175"));
        startActivity(i);

    }

}// end MainActivity class
