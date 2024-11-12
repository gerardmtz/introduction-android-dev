package com.example.appmitienda;

import android.provider.BaseColumns;

public class Estructura {
    public Estructura(){

    }
    public static abstract class  EstructuraDeDatos implements BaseColumns{
        public static final String NOMBRE_TABLA = "Tienda";
        public static final  String COLUMNA_PRODUCTO = "Producto";
        public  static final  String COLUMNA_PRECIO = "Precio";
        public static final  String COLUMNA_SECCION = "Seccion";
    }
}
