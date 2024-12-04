package com.example.appbouncingballgame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements Runnable {

    private Thread gameThread;
    private boolean isPlaying;
    private Ball ball;
    private Paint paint;
    private Canvas canvas;
    private SurfaceHolder surfaceHolder;
    private int screenX, screenY;
    private int score = 0;
    private float ax, ay;

    public GameView(Context context, int screenX, int screenY) {
        super(context);

        this.screenX = screenX;
        this.screenY = screenY;

        surfaceHolder = getHolder();
        paint = new Paint();

        // Se inicializa la pelota
        ball = new Ball(screenX, screenY);
    }

    private void update() {
        // Actualiza la velocidad basada en el acelerómetro
        ball.vx += ax / 5;
        ball.vy += ay / 5;

        // Limita la velocidad máxima
        float maxSpeed = 20;
        ball.vx = Math.max(-maxSpeed, Math.min(ball.vx, maxSpeed));
        ball.vy = Math.max(-maxSpeed, Math.min(ball.vy, maxSpeed));

        ball.update(screenX, screenY);
    }


    private void draw() {
        if (surfaceHolder.getSurface().isValid()) {
            canvas = surfaceHolder.lockCanvas();
            canvas.drawColor(Color.WHITE);

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
            gameThread.sleep(17); // Aproximadamente 60fps
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
                // Opcional: Aumentar la velocidad de la pelota
                ball.vx *= 1.1;
                ball.vy *= 1.1;
            }
        }
        return true;
    }

    public void setSensorValues(float ax, float ay) {
        this.ax = ax;
        this.ay = ay;
    }

    public void resume() {
        isPlaying = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void pause() {
        try {
            isPlaying = false;
            gameThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while(isPlaying) {
            update();
            draw();
            control();

        }
    }
}
