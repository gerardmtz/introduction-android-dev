package com.example.primera_activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity2 extends AppCompatActivity {

    TextView result;
    Button regresar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        result = findViewById(R.id.resultado);
        regresar = findViewById(R.id.button);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent i = getIntent();
        double n1 = i.getDoubleExtra("N1", 0);
        double n2 = i.getDoubleExtra("N2", 0);

        // Realizar la operación y mostrar el resultado
        double res = Math.pow(n1, n2);
        String msg = n1 + "^" + n2 + " = " + res;
        result.setText(msg);

        // Listener para el botón de regresar
        regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {  // Cambiar a onClick
                finish();
            }
        });
    }
}
