package com.example.appbouncingballgame;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

//import com.google.android.material.color.utilities.Score;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Nombre de la base de datos
    private static final String DATABASE_NAME = "game_scores.db";

    // Versión de la base de datos
    private static final int DATABASE_VERSION = 1;

    // Nombre de la tabla
    public static final String TABLE_SCORES = "scores";

    // Columnas de la tabla
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_POINTS = "points";
    public static final String COLUMN_TIMESTAMP = "timestamp";

    // Sentencia SQL para crear la tabla
    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_SCORES + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_POINTS + " INTEGER NOT NULL, " +
                    COLUMN_TIMESTAMP + " INTEGER NOT NULL" +
                    ");";

    /**
     * Constructor de DatabaseHelper.
     *
     * @param context El contexto de la aplicación.
     */
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Metodo llamado cuando se crea la base de datos por primera vez.
     *
     * @param db El objeto SQLiteDatabase.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    /**
     * Método llamado cuando se actualiza la versión de la base de datos.
     *
     * @param db         El objeto SQLiteDatabase.
     * @param oldVersion La versión antigua.
     * @param newVersion La nueva versión.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // En este ejemplo, simplemente eliminamos la tabla antigua y la recreamos.
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SCORES);
        onCreate(db);
    }

    /**
     * Inserta una nueva puntuación en la base de datos.
     *
     * @param points    La puntuación obtenida.
     * @param timestamp El tiempo en que se obtuvo la puntuación (en milisegundos).
     */
    public void insertScore(int points, long timestamp) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_POINTS, points);
        values.put(COLUMN_TIMESTAMP, timestamp);
        db.insert(TABLE_SCORES, null, values);
        db.close(); // Cierra la conexión para liberar recursos
    }

    /**
     * Recupera las mejores puntuaciones ordenadas de mayor a menor.
     *
     * @param limit El número máximo de puntuaciones a recuperar.
     * @return Una lista de objetos Score.
     */
    public List<Score> getTopScores(int limit) {
        List<Score> scoreList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Sentencia SQL para seleccionar las mejores puntuaciones
        String query = "SELECT * FROM " + TABLE_SCORES +
                " ORDER BY " + COLUMN_POINTS + " DESC LIMIT " + limit;

        Cursor cursor = db.rawQuery(query, null);

        // Itera sobre el Cursor y añade cada puntuación a la lista
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID));
                int points = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_POINTS));
                long timestamp = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_TIMESTAMP));

                Score score = new Score(id, points, timestamp);
                scoreList.add(score);
            } while (cursor.moveToNext());
        }

        cursor.close(); // Cierra el Cursor
        db.close(); // Cierra la conexión
        return scoreList;
    }
}
