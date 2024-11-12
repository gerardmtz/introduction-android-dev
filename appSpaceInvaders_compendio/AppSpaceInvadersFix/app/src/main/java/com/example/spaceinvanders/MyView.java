package com.example.spaceinvanders;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.NinePatchDrawable;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class MyView extends View {

    //Variables
    private int xWidth; //Ancho del lienzo de dibujo
    private int yHeight; //Ancho del lienzo de dibujo

    private float newX; //Nueva posicion del lienzo
    private int sensibilidad; //Sensibilidad del acelerometro
    private float xNave, yNave; //Posiciones x, y de la nave
    private float xNI, yNI; //Posiciones x, y de las naves invasoras
    private float xBala, yBala; //Posiciones x, y de la bala
    private float anchoNave, altoNave; //Ancho y alto de la imagen de la nave
    private float anchoNI, altoNI; //Ancho y alto de la imagen de la naves invasoras
    private float separacion; //La separacion de las naves invasoras
    private float numNaves; //Numero de naves invasoras a dibujar
    private boolean dispara; //Indicacion de un disparo
    boolean [] dibuja; //Arreglo de banderas para dibujar la nave invasora



    //Archivos para graficos

    Bitmap bmpBackground;
    Bitmap bmpNave;
    Bitmap bmpNI;
    Bitmap bmpBala;

    //Archivos para sonido

    private MediaPlayer shot;
    private MediaPlayer boom;

    public MyView(Context context){
        super(context);
        sensibilidad = 100;
        separacion = 30;
        dispara = false;
        numNaves = 5;

        //Recursos de imagenes
        bmpBackground = BitmapFactory.decodeResource(getResources(), R.drawable.universe2);
        bmpNave = BitmapFactory.decodeResource(getResources(), R.drawable.spaceship2);
        bmpNI = BitmapFactory.decodeResource(getResources(), R.drawable.badship2);
        bmpBala = BitmapFactory.decodeResource(getResources(), R.drawable.shot);

        //Recursos de sonidos
        shot = MediaPlayer.create(context, R.raw.disparo);
        boom = MediaPlayer.create(context, R.raw.explosion2);

        //Obtenemos las dimensiones de la nave
        anchoNave = bmpNave.getWidth();
        altoNave = bmpNave.getHeight();

        //Obtenemos las dimensiones de la nave invasora
        anchoNI = bmpNI.getWidth();
        altoNI = bmpNI.getHeight();

        //Valores iniciales de la nave invasora
        xNI = 0;
        yNI = 50;

        //Proyectil
        xBala = 0;
        yBala = 0;

        //Arreglo de banderas para dibujar NI
        dibuja = new boolean[30];
        for (int i = 0; i < dibuja.length; i++)
            dibuja[i] = true;


    } //Termina constructor

    //Metodo para dibujar el escenario 2D


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //Obetener el alto y ancho del lienzo (pantalla del celular)
        xWidth = getWidth();
        yHeight = getHeight();

        //Fondo ----------------------------------------------------------------------------------------------------------
        NinePatchDrawable background = (NinePatchDrawable) getResources().getDrawable(R.drawable.universe_nine);
        background.setBounds(0, 0, xWidth, yHeight);
        background.draw(canvas);

        //Posicion de la nave (en el universo)
        xNave = xWidth / 2 - anchoNave / 2 - sensibilidad * newX;
        yNave = yHeight - altoNave;

        //Calcular la nace denfensora
        canvas.drawBitmap(bmpNave, xNave, yNave, null);

        //Colocar las naves invasoras
        numNaves = (int)(xWidth / anchoNI);
        xNI = 0;
        for (int i = 1; i <= numNaves; i++){
            if (dibuja[i]) {
                canvas.drawBitmap(bmpNI, xNI, yNI, null);
            }
            xNI = xNI + anchoNI + separacion;
        }

        //Impacto de bala con nave invasora

        //Creamos un rectangulo para las balas --------------------------------------------------------------------------------------------------------
        Rect balaRectangulo = new Rect((int) xBala, (int) yBala, (int) (xBala + bmpBala.getWidth()), (int) (yBala + bmpBala.getHeight()));
        //Creamos un rectangulo para las NI
        Rect navesInvasorasRectangulo = new Rect();
        xNI = 0;
        
        for (int i = 1; i <= numNaves; i++) {
            if (dibuja[i]) {
                navesInvasorasRectangulo.set((int) xNI, (int) yNI, (int) (xNI + anchoNI), (int) (yNI + altoNI));
                // Colision
                if (Rect.intersects(balaRectangulo, navesInvasorasRectangulo)) {
                    //Quitamos la nave respectiva
                    dibuja[i] = false;
                    boom.start();
                }
                canvas.drawBitmap(bmpNI, xNI, yNI, null);
            }
            xNI = xNI + anchoNI + separacion;
        }

        //Disparo del proyectil
        //Colocar el proyectil con respecto a la nave que lo lanza
        if (!dispara){
            yBala = yNave;
            xBala = xNave + anchoNave / 2;
        }

        //Dibujar el proyectil
        if (dispara){
            yBala -= 10;
            canvas.drawBitmap(bmpBala, xBala, yBala, null);
        }

        //Preparar para el siguiente diaparo
        if (yBala < yNI + altoNI) {
            dispara = false;
            boom.start();
        }

        //Repintar el escenario
        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!dispara) {
            dispara = true;
            shot.start();
        }
        return true;
    }

    public void actualizaX(float x){
        newX = x;
    }
}
