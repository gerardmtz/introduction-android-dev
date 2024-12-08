package com.example.appbouncingballgame;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Display;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

/**
 * MainActivity es la actividad principal del juego donde se ejecuta la lógica del juego.
 */
public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private GameView gameView;
    private SensorManager sensorManager;
    private Sensor accelerometer;

    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inicializa la base de datos
        dbHelper = new DatabaseHelper(this);

        // Obtén las dimensiones de la pantalla
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        // Inicializa GameView
        gameView = new GameView(this, size.x, size.y);

        // Crea un RelativeLayout para contener GameView y el botón
        RelativeLayout layout = new RelativeLayout(this);
        layout.addView(gameView);

        // Crea el botón para ver las mejores puntuaciones
        Button highScoresButton = new Button(this);
        highScoresButton.setText("Ver Mejores Puntuaciones");
        RelativeLayout.LayoutParams buttonParams = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        buttonParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        buttonParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        buttonParams.setMargins(0, 0, 0, 50); // Ajusta los márgenes según sea necesario

        highScoresButton.setLayoutParams(buttonParams);

        // Configura el evento click del botón
        highScoresButton.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, HighScoresActivity.class);
            startActivity(intent);
        });

        // Añade el botón al layout
        layout.addView(highScoresButton);

        // Establece el layout como contenido de la actividad
        setContentView(layout);

        // Configura el sensor de acelerómetro
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager != null) {
            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Reanuda el juego cuando la actividad vuelve a primer plano
        gameView.resume();

        if (accelerometer != null) {
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME);
        }
    }



    @Override
    protected void onPause() {
        super.onPause();
        gameView.pause();

        // Guarda la puntuación actual en la base de datos en un hilo separado
        int currentScore = gameView.getScore();
        long timestamp = System.currentTimeMillis();
        new InsertScoreTask().execute((long) currentScore, timestamp);

        sensorManager.unregisterListener(this);
    }

    /**
     * AsyncTask para insertar una puntuación en la base de datos.
     */
    private class InsertScoreTask extends android.os.AsyncTask<Long, Void, Void> {
        @Override
        protected Void doInBackground(Long... params) {
            int points = params[0].intValue();
            long timestamp = params[1];
            dbHelper.insertScore(points, timestamp);
            return null;
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        // Pasa los datos del sensor a GameView
        gameView.setSensorValues(event.values[0], event.values[1]);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // No se utiliza en este ejemplo
    }

}