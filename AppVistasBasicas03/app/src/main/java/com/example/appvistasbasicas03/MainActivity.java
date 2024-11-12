package com.example.appvistasbasicas03;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    EditText num1,num2;
    TextView res;
    Spinner spOp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        num1 = (EditText)findViewById(R.id.numero1);
        num2 = (EditText)findViewById(R.id.numero2);
        res = (TextView) findViewById(R.id.resultado);
        spOp = (Spinner) findViewById(R.id.operaciones);

        String [] datos = {"suma", "resta", "multiplica","divide"};
        ArrayAdapter adaptador = new ArrayAdapter(this, android.R.layout.simple_spinner_item, datos);
        spOp.setAdapter(adaptador);

        spOp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                int n1, n2;

                n1 = num1.getText().toString().equals("") ? 0 :
                        Integer.parseInt(num1.getText().toString());

                n2 = num2.getText().toString().equals("") ? 0 :
                        Integer.parseInt(num2.getText().toString());

                switch (i){
                    case 0:
                        int resSum = n1 + n2;
                        res.setText(String.valueOf(resSum));
                    break;
                    case 1:
                        int resResta = n1 - n2;
                        res.setText(String.valueOf(resResta));
                    break;
                    case 2:
                        int resMult = n1 * n2;
                        res.setText(String.valueOf(resMult));
                    break;
                    case 3:
                        int resDiv = n1 / n2;
                        res.setText(String.valueOf(resDiv));
                    break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

         ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
           Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

    }

    public void realizarOperacion(View v){


        if(spOp.getSelectedItem().toString().equals("suma")){


        }else if(spOp.getSelectedItem().toString().equals("resta")){


        }else if(spOp.getSelectedItem().toString().equals("multiplica")) {


        }else if(spOp.getSelectedItem().toString().equals("divide")) {

        }

    }

}