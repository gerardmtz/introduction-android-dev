package com.example.appsqlitekotlin

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase

class Usuario(context: Context) {
    private val dbHelper: MiBDHelper = MiBDHelper(context);
    private val db: SQLiteDatabase = dbHelper.writableDatabase;

    // Funciones de la BD

    // agregar un usuario nuevo
    fun agregarUsuario(nombre: String, edad: Int): Long {
        val valores = ContentValues().apply {
            put("nombre", nombre);
            put("edad", edad);
        }
        // insertar en la bd y regresar el id del registro
        return db.insert("usuarios", null, valores);
    }

    // obtener todos los usuarios en la bd
    fun obtenerUsuarioPorID(id: Int): Cursor? {

        val columnas = arrayOf("id", "nombre", "edad");

        return db.query("usuarios", columnas,
            "id = ?", arrayOf(id.toString()),
            null, null, null);
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
    fun eliminarUsuario(id: Int): Int {
        return db.delete("usuarios", "id = ?",
            arrayOf(id.toString()));
    }

    // cerramos la conexion a la bd
    fun cerrar() {
        db.close();
    }
}