package com.example.app_space_invaders;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private MiVista vista; // MiVista activity
    private SensorManager sensorManager; // instance of SensorManager
    private Sensor acc; // acelerometer


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        vista = new MiVista(this);
        setContentView(vista);

        // instanciar los objetos para el sensorManager
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        acc = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, acc, sensorManager.SENSOR_DELAY_NORMAL);

        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_main);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });

    }// end OnCreate


    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Sensor miSensor = sensorEvent.sensor;

        if (miSensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];
            float z = sensorEvent.values[2];

            // actualizar el nuevo valor de x
            vista.actualizaX(x);
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}// end MainActivity