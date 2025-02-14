package com.example.appbouncingballgame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.media3.common.util.Log;

/**
 * GameView es una clase personalizada que maneja la lógica del juego, el dibujo y las actualizaciones.
 */
public class GameView extends SurfaceView implements Runnable {

    private Thread gameThread;
    private boolean isPlaying;
    private Ball ball;
    private Paint paint;
    private Canvas canvas;
    private SurfaceHolder surfaceHolder;
    private int screenX, screenY;
    private int score = 0;

    // Variables para el acelerómetro
    private float ax = 0, ay = 0;

    // MediaPlayer para reproducir el sonido push
    private MediaPlayer pushSound;

    /**
     * Constructor de GameView.
     *
     * @param context El contexto de la aplicación.
     * @param screenX Ancho de la pantalla.
     * @param screenY Alto de la pantalla.
     */
    public GameView(Context context, int screenX, int screenY) {
        super(context);

        this.screenX = screenX;
        this.screenY = screenY;

        surfaceHolder = getHolder();
        paint = new Paint();

        // Inicializa la pelota
        ball = new Ball(screenX, screenY);

        // Carga el sonido push.flac desde la carpeta raw
        // Asegúrate de que push.flac esté en res/raw/push.flac y sea compatible.
        pushSound = MediaPlayer.create(context, R.raw.push);

        // Verifica si pushSound se pudo cargar
        if (pushSound == null) {
            Log.e("GameView", "No se pudo cargar el sonido push (push.flac). " +
                    "Verifica que el archivo esté en res/raw/push.flac y sea un formato soportado. " +
                    "Si el problema persiste, intenta con un archivo .ogg o .wav.");
        }
    }

    @Override
    public void run() {
        while (isPlaying) {
            update();
            draw();
            control();
        }
    }

    private void update() {
        // Actualiza la velocidad basada en el acelerómetro
        ball.vx += ax / 5;
        ball.vy += ay / 5;

        // Limita la velocidad máxima
        float maxSpeed = 20;
        if (ball.vx > maxSpeed) ball.vx = maxSpeed;
        if (ball.vx < -maxSpeed) ball.vx = -maxSpeed;
        if (ball.vy > maxSpeed) ball.vy = maxSpeed;
        if (ball.vy < -maxSpeed) ball.vy = -maxSpeed;

        // Actualiza la posición de la pelota
        ball.update(screenX, screenY);
    }

    private void draw() {
        if (surfaceHolder.getSurface().isValid()) {
            canvas = surfaceHolder.lockCanvas();
            canvas.drawColor(Color.WHITE); // Fondo blanco

            // Dibuja la pelota
            paint.setColor(Color.RED);
            canvas.drawCircle(ball.x, ball.y, ball.radius, paint);

            // Dibuja la puntuación
            paint.setColor(Color.BLACK);
            paint.setTextSize(50);
            canvas.drawText("Puntuación: " + score, 50, 50, paint);

            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    private void control() {
        try {
            // Aproximadamente 60 fps
            Thread.sleep(17);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            float touchX = event.getX();
            float touchY = event.getY();

            // Verifica si se tocó la pelota
            float dx = touchX - ball.x;
            float dy = touchY - ball.y;
            if (dx * dx + dy * dy <= ball.radius * ball.radius) {
                score++;
                // Reproduce el sonido al tocar la pelota, sólo si pushSound no es nulo
                if (pushSound != null) {
                    // Vuelve al inicio del audio por si se reproduce múltiples veces seguidas
                    pushSound.seekTo(0);
                    pushSound.start();
                } else {
                    Log.e("GameView", "pushSound es null, no se puede reproducir el sonido. " +
                            "Verifica el archivo push.flac en res/raw y considera usar otro formato.");
                }

                // Opcional: Aumentar la velocidad de la pelota
                ball.vx *= 1.1;
                ball.vy *= 1.1;
            }
        }
        return true;
    }

    public void resume() {
        isPlaying = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void pause() {
        try {
            isPlaying = false;
            if (gameThread != null) {
                gameThread.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Liberar recursos opcionalmente
        if (pushSound != null) {
            pushSound.release();
            pushSound = null;
        }
    }

    public void setSensorValues(float ax, float ay) {
        this.ax = ax;
        this.ay = ay;
    }

    public int getScore() {
        return score;
    }
}
