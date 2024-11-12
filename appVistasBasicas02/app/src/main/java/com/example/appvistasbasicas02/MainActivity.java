package com.example.appvistasbasicas02;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    EditText num1, num2;
    TextView res;
    CheckBox suma, resta, mult, div;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        num1 = (EditText) findViewById(R.id.numero1);
        num2 = (EditText) findViewById(R.id.numero2);

        suma = (CheckBox) findViewById(R.id.checkSuma);
        resta = (CheckBox) findViewById(R.id.checkResta);
        mult = (CheckBox) findViewById(R.id.checkMult);
        div = (CheckBox) findViewById(R.id.checkDiv);

        res = (TextView) findViewById(R.id.result);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void operaciones(View v) {
        // Clear previous result
        res.setText("");

        int n1 = Integer.parseInt(num1.getText().toString());
        int n2 = Integer.parseInt(num2.getText().toString());

        if (suma.isChecked()) {
            int s = n1 + n2;
            res.setText(res.getText().toString() + "Suma = " + s + "\n");
        }

        if (resta.isChecked()) {
            int r = n1 - n2;
            res.setText(res.getText().toString() + "Resta = " + r + "\n");
        }

        if (mult.isChecked()) {
            int m = n1 * n2;
            res.setText(res.getText().toString() + "Multiplicación = " + m + "\n");
        }

        if (div.isChecked()) {
            if (n2 != 0) {
                double d = (double) n1 / n2;
                res.setText(res.getText().toString() + "División = " + d + "\n");
            } else {
                res.setText(res.getText().toString() + "Error: División por cero\n");
            }
        }
    }
}
