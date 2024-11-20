package com.example.appsqlitekotlin

/*
*   Usuario: Esta clase contiene todos los métodos CRUD necesarios
*   para interactuar con la tabla usuarios.
*   Aquí se agrupan las siguientes funciones:
    •	agregarUsuario(nombre: String, edad: Int): Inserta un nuevo usuario en la base de datos.
    •	obtenerTodosLosUsuarios(): Devuelve todos los usuarios de la base de datos en forma de un Cursor.
    •	obtenerUsuarioPorId(id: Int): Devuelve un solo usuario según su id.
    •	actualizarUsuario(id: Int, nombre: String, edad: Int): Actualiza un usuario existente por su id.
    •  	eliminarUsuario(id: Int): Elimina un usuario de la base de datos por su id.
    •	cerrar(): Cierra la conexión con la base de datos.
 */

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.example.appsqlitekotlin.Usuario

class UsuarioMetodosCRUD (context: Context){
    private val dbHelper: MiBDHelper = MiBDHelper(context)
    private val db: SQLiteDatabase = dbHelper.writableDatabase

    companion object {
        const val TABLE_NAME = "usuarios"
        const val COLUMN_ID = "id"
        const val COLUMN_NOMBRE = "nombre"
        const val COLUMN_EDAD = "edad"
    }
    // funcion para agregar un nuevo usuario
    fun agregarUsuario(nombre: String, edad: Int): Long {
        val valores = ContentValues().apply {
            put(COLUMN_NOMBRE, nombre)
            put(COLUMN_EDAD, edad)
        }
        // insertar en la bd y regresar el id del registro
        return db.insert(TABLE_NAME,null,valores)
    }

    // obtener todos los usuarios
    fun obtenerTodosLosUsarios(): List<Usuario> {
        val listaUsuarios = mutableListOf<Usuario>()

        val columnas = arrayOf(COLUMN_ID,COLUMN_NOMBRE,COLUMN_EDAD)
        val cursor = db.query(TABLE_NAME,columnas,null,null,
            null,null,null)

        // Obtener los índices de las columnas
        val idIndex = cursor.getColumnIndex(COLUMN_ID)
        val nombreIndex = cursor.getColumnIndex(COLUMN_NOMBRE)
        val edadIndex = cursor.getColumnIndex(COLUMN_EDAD)

        // Recorrer el cursor
        while (cursor.moveToNext()) {
            val id = cursor.getInt(idIndex)
            val nombre = cursor.getString(nombreIndex)
            val edad = cursor.getInt(edadIndex)

            // Agregar el usuario a la lista
            listaUsuarios.add(Usuario(id, nombre,edad))
        }

        //cerrar cursor
        cursor.close()

        return listaUsuarios
    }

    //obtener un usuario por ID
    fun obtenerUsuarioPorID(id: Int): Cursor? {
        val columnas =
            arrayOf(COLUMN_ID,COLUMN_NOMBRE,COLUMN_EDAD)
        val cursor = db.query(TABLE_NAME,columnas,
            "id = ?",
            arrayOf(id.toString()),
            null,null, null)
        return if(cursor != null && cursor.moveToFirst()){
            cursor
        } else{
            null //si no hay usuario con ese ID regresar null
        }
    }

    // actualizar los datos de un usuario
    fun actualizaUsuarioPorId(id: Int, nombre: String,
                              edad: Int): Int{
        val valores = ContentValues().apply {
            put(COLUMN_NOMBRE, nombre)
            put(COLUMN_EDAD, edad)
        }
        println("<----------EN ACTUALIZAR USUARIO--------->")
        return db.update(TABLE_NAME, valores,
            "id = ?", arrayOf(id.toString()))
    }

    // eliminar usuario por id
    fun eliminarUsuario(id: Int): Int{
        println("<---------EN ELIMINAR USUARIO -------->")
        return db.delete(TABLE_NAME, "id = ?",
            arrayOf(id.toString())
        )
    }

    // cerrar la bd
    fun cerrar(){
        db.close()
    }
}