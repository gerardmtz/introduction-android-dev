package com.example.appcalculadora;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    TextView pantalla;
    double num1;
    double num2 = 0;
    char operador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        pantalla = (TextView) findViewById(R.id.display);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


    }

    public void onClickNum(View v) {
        String currentText = pantalla.getText().toString();

        if (v.getId() == R.id.cero) {
            pantalla.setText(currentText + "0");
        } else if (v.getId() == R.id.uno) {
            pantalla.setText(currentText + "1");
        } else if (v.getId() == R.id.dos) {
            pantalla.setText(currentText + "2");
        } else if (v.getId() == R.id.tres) {
            pantalla.setText(currentText + "3");
        } else if (v.getId() == R.id.cuatro) {
            pantalla.setText(currentText + "4");
        } else if (v.getId() == R.id.cinco) {
            pantalla.setText(currentText + "5");
        } else if (v.getId() == R.id.seis) {
            pantalla.setText(currentText + "6");
        } else if (v.getId() == R.id.siete) {
            pantalla.setText(currentText + "7");
        } else if (v.getId() == R.id.ocho) {
            pantalla.setText(currentText + "8");
        } else if (v.getId() == R.id.nueve) {
            pantalla.setText(currentText + "9");
        } else if (v.getId() == R.id.punto) {
            if (!currentText.contains(".")) {
                pantalla.setText(currentText + ".");
            }
        }
    }

    public void onCLickOperador(View v){
        //Extraer el primer numero (operando)
        String valor = pantalla.getText().toString();
        num1 = Double.parseDouble(valor);
        if(v.getId() == R.id.suma){
            operador = '+';
        } else if(v.getId() == R.id.resta){
            operador = '-';
        } else if(v.getId() == R.id.multi){
            operador = '*';
        } else if(v.getId() == R.id.dividir){
            operador = '/';
        } else if (v.getId() == R.id.frac) {
            operador = '#';
        } else if (v.getId() == R.id.poten) {
            operador = '?';
        } else if (v.getId() == R.id.raiz) {
            operador = '¿';
        }
        pantalla.setText("");
    }

    public void onCLickIgual(View v){
        double resul = 0;

        //NUEVO

        if(operador == '#'){
            resul = 1 / num1;
        }else if(operador == '?'){
            resul = Math.pow( num1, 2 );
        }else if(operador == '¿'){
            resul = Math.sqrt( num1 );
        }else

        //Extraer el segundo numero
        num2 = Double.parseDouble(pantalla.getText().toString());
        pantalla.setText("");

        //Realizar operacion

        switch (operador){
            case '+' : resul = num1 + num2; break;
            case '-' : resul = num1 - num2; break;
            case '*' : resul = num1 * num2; break;
            case '/' : resul = num1 / num2; break;
        }

        pantalla.setText(String.valueOf(resul));
    }

    public void onClear(View v){
        pantalla.setText("");
        num1 = num2 = 0.0;
    }

}