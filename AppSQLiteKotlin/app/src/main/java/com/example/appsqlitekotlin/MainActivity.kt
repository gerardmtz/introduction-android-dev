package com.example.appsqlitekotlin

import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    // campos
    lateinit var idTxt: EditText
    lateinit var nameTxt: EditText
    lateinit var ageTxt: EditText

    //botones
    lateinit var agregarBtn: Button
    lateinit var consultarBtn: Button
    lateinit var cambiarBtn: Button
    lateinit var eliminarBtn: Button

    //recycler view
    private lateinit var recyclerView: RecyclerView

    // Clase métodos de BD para Usuario
    lateinit var usuarioMetodosCRUD:UsuarioMetodosCRUD

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Iniciamos
        usuarioMetodosCRUD = UsuarioMetodosCRUD(this)

        //Iniciamos los campos de captura
        idTxt = findViewById(R.id.idtxt)
        nameTxt = findViewById(R.id.nombretxt)
        ageTxt = findViewById(R.id.edadtxt)

        //Iniciamos los botones
        agregarBtn = findViewById(R.id.agregar)
        consultarBtn = findViewById(R.id.consultar)
        cambiarBtn = findViewById(R.id.cambiar)
        eliminarBtn = findViewById(R.id.eliminar)

        // Iniciamos el recyclerView, para mostrar todos los
        // elementos en la tabla usuario
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Código para agregar registros en la tabla usuario
        agregarBtn.setOnClickListener {
            // Obtener el texto ingresado en el EditText
            val ageText = ageTxt.text.toString()
            //agregar usuario
            val id = usuarioMetodosCRUD.agregarUsuario(
                nameTxt.text.toString(), ageText.toInt())
            Toast.makeText(this,
                "Usuario agregado con ID: $id",
                Toast.LENGTH_SHORT).show()
        }

        consultarBtn.setOnClickListener {
            //obtener el id ingresado en el EditText
            val idText = idTxt.text.toString()

            if(idText.isNotEmpty()){
                val id: Int = idText.toInt()
                consultaUsuarioPorID(id)

            } else {
                //obtener la lista de usuarios
                val lista = usuarioMetodosCRUD.obtenerTodosLosUsarios()
                //asignar el adaptador al RecyclerView
                println("<--------------despues de obtener la lista")
                Log.d("UsuarioToAdapter", "Tamaño de la lista: ${lista.size}")
                val adapter =UsuarioToAdapter(lista)
                recyclerView.adapter = adapter
                println("despues de asignar el adaptador ---------->")
            }
        }

        cambiarBtn.setOnClickListener {
            val filas = usuarioMetodosCRUD.actualizaUsuarioPorId(
                idTxt.text.toString().toIntOrNull() ?: 0,
                nameTxt.text.toString(),
                ageTxt.text.toString().toIntOrNull() ?: 0)
            Toast.makeText(this, "Filas actualizadas: $filas", Toast.LENGTH_SHORT).show()
        }

        eliminarBtn.setOnClickListener {
            val filas = usuarioMetodosCRUD.eliminarUsuario(
                idTxt.text.toString().toIntOrNull() ?: 0)
            Toast.makeText(this, "Filas eliminadas: $filas", Toast.LENGTH_SHORT).show()
        }
    }

    fun consultaUsuarioPorID(id: Int){
        val cursor: Cursor? = usuarioMetodosCRUD.obtenerUsuarioPorID(id)
        // Verificar si el Cursor tiene datos
        if (cursor != null && cursor.moveToFirst()) {
            // Obtener el índice de las columnas
            val idIndex =
                cursor.getColumnIndex("id")
            val nombreIndex =
                cursor.getColumnIndex("nombre")
            val edadIndex =
                cursor.getColumnIndex("edad")

            // Mover los valores del Cursor a los EditText
            idTxt.setText(cursor.getInt(idIndex).toString())
            nameTxt.setText(cursor.getString(nombreIndex))
            ageTxt.setText(cursor.getInt(edadIndex).toString())
        } else
            Toast.makeText(this, "ID no encontrado: $id", Toast.LENGTH_LONG).show()

        // Cerrar el cursor después de usarlo
        cursor?.close()
    }
}