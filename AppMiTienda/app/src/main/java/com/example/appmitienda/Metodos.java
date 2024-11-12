package com.example.appmitienda;



import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.example.appmitienda.Estructura.EstructuraDeDatos.*;
import androidx.annotation.Nullable;

public class Metodos extends SQLiteOpenHelper {
    String crearTabla;
    public Metodos(@Nullable Context context) {
        super(context, "Tienda.sqlite",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db ){

        crearTabla = "CREATE TABLE " + NOMBRE_TABLA + " (" +
                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMNA_PRODUCTO + " TEXT, " +
                COLUMNA_PRECIO + " TEXT, " +
                COLUMNA_SECCION + " TEXT )";
        db.execSQL(crearTabla);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String borrarTabla = "DROP TABLE IF EXISTS " + NOMBRE_TABLA;
        db.execSQL(borrarTabla);
        onCreate(db);
    }
}
