package com.example.appsensores;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    TextView lista;
    SensorManager sm;
    Sensor sensorArcc, sensorLuz;

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
        lista = (TextView) findViewById(R.id.textView);
        sm = (SensorManager) getSystemService(SENSOR_SERVICE);

        List <Sensor> listaSensores = sm.getSensorList(Sensor.TYPE_ALL);

        for(Sensor s: listaSensores){
            lista.append(s.getName() + "\n");
        }

        sensorArcc = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorLuz = sm.getDefaultSensor(Sensor.TYPE_LIGHT);
        sm.registerListener(this,sensorArcc,SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Sensor miSensor = sensorEvent.sensor;
        if(miSensor.getType() == Sensor.TYPE_ACCELEROMETER){
            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];
            float z = sensorEvent.values[2];
            Toast.makeText(this, "X = "+x+",Y = "+y+",Z = "+z,Toast.LENGTH_LONG).show();
        }
        if(miSensor.getType() == Sensor.TYPE_LIGHT){
            float valor = sensorEvent.values[0];
            Toast.makeText(this, "valor = "+valor, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}