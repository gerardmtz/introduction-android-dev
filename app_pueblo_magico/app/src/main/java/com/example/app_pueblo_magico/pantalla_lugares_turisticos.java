package com.example.app_pueblo_magico;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class pantalla_lugares_turisticos extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pantalla_lugares_turisticos);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button button_inicio = findViewById(R.id.button_inicio_turistico);

        button_inicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showInicio();
            }
        });
    } // end of oCreate

    private void showInicio() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

} // end of Main class