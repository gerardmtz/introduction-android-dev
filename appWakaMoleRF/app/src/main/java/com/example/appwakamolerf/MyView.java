package com.example.appwakamolerf;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.MotionEvent;
import android.view.View;

import android.os.Handler;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MyView extends View {

    private Vibrator vibrator;

    private float xTouch, yTouch; // coordenada de toque en la pantalla
    private float xTopo,yTopo;   //Coordenada del topo en la pantalla
    private float w,h;  // ancho y alto del canvas
    private float wTopo,hTopo; // ancho y alto del topo

    private Bitmap bmpPrado, bmpTopo;

    private MediaPlayer sndGolpe;

    int puntuacion;
    int nivel;
    int tiempo;  // tiempo de espera
    
    MoverTopo moverTopo = new MoverTopo();

    Paint paint;

    Timer timer;

    Handler handler;

    public MyView(Context context) {
        super(context);

        vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);

        // Bitmaps
        bmpPrado = BitmapFactory.decodeResource(getResources(),R.drawable.prado);
        bmpTopo = BitmapFactory.decodeResource(getResources(),R.drawable.mole);
        wTopo = bmpTopo.getWidth();
        hTopo = bmpTopo.getHeight();

        // sonido
        sndGolpe = MediaPlayer.create(getContext(),R.raw.golpe_editado);

        tiempo     = 2000; // en milisegundos
        puntuacion = 0;
        nivel      = 1;

        xTouch = yTouch = 0;

        paint = new Paint();

        paint.setTextSize(80);

        timer = new Timer();
        handler = new Handler();

        startTimer();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // sonido
        //sndGolpe = MediaPlayer.create(this.getContext(),R.raw.golpe_editado);
        // obtener ancho y alto del canvas (lienzo)
        w = canvas.getWidth();
        h = getHeight();

       //canvas.drawBitmap(bmpPrado,0,0,null); // establecer fondo con imagen
        paint.setColor(Color.argb(250,0, 255, 0) );
        canvas.drawRect(0.0f,0.0f, (float)w, (float)h,paint); // establecer fondo con color sólido

        canvas.drawBitmap(bmpTopo,xTopo,yTopo,null);
        paint.setColor(Color.WHITE);
        canvas.drawText("SCORE: " + puntuacion, w/2, 60, paint);
        }


    @Override
    public boolean onTouchEvent(MotionEvent evento) {
        xTouch = evento.getX();
        yTouch = evento.getY();

        if((xTouch >= xTopo && xTouch<=xTopo+wTopo) && (yTouch >= yTopo && yTouch <= yTopo+hTopo)){
                 ++puntuacion;
                 sndGolpe.start();
        }

        // Evento vibracion / vibration event

        if (vibrator != null) {
            // Para versiones API 26 o superior
            vibrator.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE));
        }

        // de acuerdo a la puntuación incrementar nivel
        switch(nivel){
            case 1:
                if(puntuacion>=5 && puntuacion <=10){
                    nivel++;
                    tiempo-=100;
                }
                break;
            case 2:
                if(puntuacion>10 && puntuacion <=80){
                    nivel++;
                    tiempo-=200;
                }
                break;
            case 3:
                if(puntuacion>81 && puntuacion <=100){
                    nivel++;
                    tiempo-=400;
                }
                break;
        }
        return true;
    }

    public void moverTopo(){
        Random random = new Random();
        xTopo = random.nextFloat()*(w-wTopo);
        yTopo = random.nextFloat()*(h-hTopo);
    }

        private void startTimer() {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                //mover el topo
                moverTopo();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        invalidate();
                    }
                });
            }
        },0,tiempo);
        }
}
