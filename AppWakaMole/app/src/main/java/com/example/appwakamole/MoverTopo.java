package com.example.appwakamole;


import android.content.Context;
import android.os.Vibrator;

public class MoverTopo {

   // Vibrator v = (Vibrator)getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);


    public float moverX(float anchoPrado,float wTopo){
        float x = (float)(Math.random()*anchoPrado);
        if(x + wTopo < anchoPrado)
            return(x);
        else
            return (float)(anchoPrado/2.0);
    }
    public float moverY(float altoPrado, float hTopo){
        float y = (float)(Math.random()*altoPrado);
        if(y + hTopo < altoPrado)
            return(y);
        else
            return (float)(altoPrado/2.0);
    }

}
