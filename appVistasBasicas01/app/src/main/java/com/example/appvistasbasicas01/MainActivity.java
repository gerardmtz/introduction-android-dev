// C20131354 Gerardo Ignacio Martinez Marin

package com.example.appvistasbasicas01;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    EditText num1, num2;
    TextView res;
    RadioButton suma, resta, mult, div;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        num1 = (EditText) findViewById(R.id.numero1);
        num2 = (EditText) findViewById(R.id.numero2);
        res = (TextView) findViewById(R.id.resultado);

        suma = (RadioButton) findViewById(R.id.sumaRbtn);
        resta = (RadioButton) findViewById(R.id.restaRbtn);
        mult = (RadioButton) findViewById(R.id.multRbtn);
        div = (RadioButton) findViewById(R.id.divRbtn);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void operaciones(View v) {
        int n1 = Integer.parseInt(num1.getText().toString());
        int n2 = Integer.parseInt(num2.getText().toString());

        if (suma.isChecked()) {
            int res01 = n1+n2;
            res.setText(String.valueOf(res01));

        } else if (resta.isChecked()) {
            int res01 = n1-n2;
            res.setText(String.valueOf(res01));

        } else if (mult.isChecked()) {
            int res01 = n1*n2;
            res.setText(String.valueOf(res01));

        } else if (div.isChecked()) {
            int res01 = n1/n2;
            res.setText(String.valueOf(res01));
        }


    }

}