package com.example.app_space_invaders;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.media.MediaPlayer;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;

public class MiVista extends View {

    // Variables de la pantalla
    private int w;
    private int h;

    // Variables nave defensora
    private float anchoNave, altoNave, xNave, yNave;

    // Nave invasora
    private float anchoNI, altoNI, xNI, yNI;
    private float separacion;

    private boolean [] dibuja;

    // proyectil/bala
    private float xBala, yBala;
    private boolean dispara;

    // acelerometro
    private float newX;
    private int sensibilidad;

    // recursos: imagenes
    private Bitmap bmpBackground, bmpNave, bmpNI, bmpBala;

    // recursos: sonidos
    private MediaPlayer disparo;
    private MediaPlayer explosion;


    public MiVista(Context context) {
        super(context);
        sensibilidad = 200;
        dispara = false;
        separacion = 30;

        // obtener recursos
        // Imagenes:

        bmpBackground = BitmapFactory.decodeResource(getResources(), R.drawable.universe2);
        bmpNave = BitmapFactory.decodeResource(getResources(), R.drawable.spaceship2);
        bmpNI = BitmapFactory.decodeResource(getResources(), R.drawable.badship2);
        bmpBala = BitmapFactory.decodeResource(getResources(), R.drawable.shot);

        // Sonidos:
        disparo = MediaPlayer.create(context, R.raw.disparo);
        explosion = MediaPlayer.create(context, R.raw.explosion);

        // dimensiones de naves
        anchoNave = bmpNave.getWidth();
        altoNave = bmpNave.getHeight();
        anchoNI = bmpNI.getHeight();
        altoNI = bmpNI.getHeight();

        // Valores iniciales para las posiciones
        // de la nave invasora
        xNI = 0;
        yNI = 50;

        // Bala
        xBala = 0;
        yBala = 0;

        // Arreglo de banderas para dibujar las naves invasoras
        dibuja = new boolean [30];
        for (int i = 0; i < dibuja.length; i++) {
            dibuja[i] = true;
        }
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);

        // Obtener el ancho y el alto de la pantalla del celular
        w = getWidth();
        h = getHeight();

        // localizacion de la nave defensora
        xNave = w/2 - anchoNave/2 - sensibilidad * newX;
        yNave = h - altoNave;

        // establecer el fondo
        canvas.drawBitmap(bmpBackground, 0.0f, 0.0f, null);

        // nave defensora
        canvas.drawBitmap(bmpNave, xNave, yNave, null);

        // dibuja naves invasoras
        xNI = 0;
        int numNI = (int) (w/anchoNI);

        for (int i = 0; i < numNI; i++) {
            if (dibuja[i])
                canvas.drawBitmap(bmpNI, xNI, yNI, null);
            xNI = xNI + anchoNI + separacion;
        }

        // revisar si se hizo un disparo
        if (dispara) {
            yBala = -10;
            canvas.drawBitmap(bmpBala, xBala, yBala, null);
        }

        // agregar validacion en X
        if (xBala < xNI + anchoNI) {
            explosion.start();
            dispara = false;
        }

        // revisar colision en de la bala para Y (en nave invasora)
        if (yBala < yNI + altoNI) {
           explosion.start();
           dispara = false;
        }

        // repintar el escenario
        invalidate();
    }

    public void actualizaX (float x) {
        newX = x;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        dispara = true;
        disparo.start();
        return true;
    }
}
