package com.example.appbouncingballgame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

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
    }

    /**
     * Método principal del hilo del juego.
     */
    @Override
    public void run() {
        while (isPlaying) {
            update();
            draw();
            control();
        }
    }

    /**
     * Actualiza la lógica del juego.
     */
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


    /**
     * Dibuja los elementos del juego en el lienzo.
     */
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

    /**
     * Controla la velocidad de fotogramas del juego.
     */
    private void control() {
        try {
            gameThread.sleep(17); // Aproximadamente 60 fps
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Maneja los eventos táctiles.
     *
     * @param event El evento táctil.
     * @return true si el evento fue manejado.
     */
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
                // Opcional: Aumentar la velocidad de la pelota
                ball.vx *= 1.1;
                ball.vy *= 1.1;
            }
        }
        return true;
    }

    /**
     * Inicia el hilo del juego.
     */
    public void resume() {
        isPlaying = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    /**
     * Pausa el juego y detiene el hilo.
     */
    public void pause() {
        try {
            isPlaying = false;
            gameThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Establece los valores del sensor de acelerómetro.
     *
     * @param ax Valor del acelerómetro en el eje X.
     * @param ay Valor del acelerómetro en el eje Y.
     */
    public void setSensorValues(float ax, float ay) {
        this.ax = ax;
        this.ay = ay;
    }

    /**
     * Obtiene la puntuación actual del jugador.
     *
     * @return La puntuación actual.
     */
    public int getScore() {
        return score;
    }


}
