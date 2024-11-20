package com.example.appsqlitekotlin

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase

class Usuario(context: Context) {
    private val dbHelper: MiBDHelper = MiBDHelper(context);
    private val db: SQLiteDatabase = dbHelper.writableDatabase;

    companion object {
        const val  TABLE_NAME = "usuarios";
        const val COLUMN_ID = "id";
        const val COLUMN_NOMBRE = "nombre";
        const val COLUMN_EDAD = "edad";
    }

    // Funciones de la BD

    // agregar un usuario nuevo
    fun agregarUsuario(nombre: String, edad: Int): Long {
        val valores = ContentValues().apply {
            put("nombre", nombre);
            put("edad", edad);
        }
        // insertar en la bd y regresar el id del registro
        return db.insert(TABLE_NAME, null, valores);
    }

    // obtener todos los usuarios en la bd
    fun obtenerUsuarioPorID(id: Int): Cursor? {

        val columnas = arrayOf(COLUMN_ID, COLUMN_NOMBRE, COLUMN_EDAD);

        val cursor = db.query(TABLE_NAME, columnas, "id = ?",
            arrayOf(id.toString()), null, null, null);

        return if (cursor != null && cursor.moveToFirst()) {
            cursor;
        }
        else {
            null; // si no existen usuarios en la base de datos se ingresa el ID NULL
        }

//        return db.query("usuarios", columnas,
//            "id = ?", arrayOf(id.toString()),
//            null, null, null);
    }

    // actualizar un usuario por ID
    fun actualizaUsuarioPorId(id: Int, nombre: String, edad: Int): Int {

        val valores = ContentValues().apply {
            put("nombre", nombre);
            put("edad", edad)
        }

        return db.update("usuario", valores, "id = ?",
            arrayOf(id.toString()));
    }

    // eliminar usuario por id
    fun eliminarUsuario(id  Int): Int {
        return db.delete("usuarios", "id = ?",
            arrayOf(id.toString()));
    }

    // cerramos la conexion a la bd
    fun cerrar() {
        db.close();
    }
}