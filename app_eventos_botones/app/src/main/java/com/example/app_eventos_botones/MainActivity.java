package com.example.app_eventos_botones;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity implements View

        .OnClickListener{

    Button btn1, btn2, btn3;

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

        btn1 = (Button) findViewById(R.id.boton_1);
        btn2 = (Button) findViewById(R.id.boton_2);
        btn3 = (Button) findViewById(R.id.boton_3);

        // 1. evento onClick con implements
        btn1.setOnClickListener(this); // este boton lanza el evento onClick

        // 2. evento con funcion anonima (inner function)
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // cuando utilizamos una funcion anonima (inner function)
                // queda fuera del contexto de la clase, por lo que se necesita
                // indicar en qué contexto se está trabajando

                Toast.makeText(MainActivity.this, "Evento con funcion anonima",
                        Toast.LENGTH_LONG).show();

            }
        });


        // 3. Mi propio evento onClick. Debo declarar un metodo que será
        // invocado desde el archivo XML.
    }

    @Override
    public void onClick(View view) {
        Toast.makeText(this, "Evento en implements", Toast.LENGTH_LONG).show();

    }

    // Mi propio evento onClick
    public void miOnClick(View v) {
        Toast.makeText(this, "Mi evento onClick", Toast.LENGTH_LONG).show();
    }


}















