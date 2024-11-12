package com.example.appvistasbasicas04;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {


    String [] estados = {"Aguascalientes", "Campeche", "Coahuila", "Colima", "Chiapas" };
    String [] habitantes = {"200,000", "250,000", "250,000", "120,000", "300,000"};
    String [] capital = { "Aguascaliente", "San José de Campeche", "Saltillo", "Colima", "Tuxtla Guitierrez" };

    TextView texto;
    ListView listaEstados;

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

        texto = (TextView) findViewById(R.id.texto);
        listaEstados = (ListView) findViewById(R.id.lista);

        ArrayAdapter adaptador = new ArrayAdapter(this, android.R.layout.simple_list_item_1, estados);

        listaEstados.setAdapter(adaptador);
        listaEstados.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                texto.setText("La capital de " + adapterView.getItemAtPosition(i) + " es " + capital[i] + ", población " + habitantes[i]);
            }
        });
    }
}