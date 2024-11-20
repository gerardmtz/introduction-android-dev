package com.example.appsqlitekotlin

import android.database.Cursor
import android.net.wifi.p2p.WifiP2pManager.DnsSdTxtRecordListener
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    // campos
    lateinit var idTxt: EditText;
    lateinit var nameTxt: EditText;
    lateinit var ageTxt: EditText;

    // botones
    lateinit var agregarBtn: Button;
    lateinit var consultarBtn: Button;
    lateinit var cambiarBtn: Button;
    lateinit var eliminarBtn: Button;

    // clase Usuario
    lateinit var usuario: Usuario;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }

        usuario = Usuario(this);

        // iniciamos los campos de captura

        idTxt = findViewById(R.id.idtxt);
        nameTxt = findViewById(R.id.nombretxt);
        ageTxt = findViewById(R.id.edadtxt);

        // Iniciamos los botones

        agregarBtn = findViewById(R.id.agregar);
        consultarBtn = findViewById(R.id.consultar);
        cambiarBtn = findViewById(R.id.cambiar);
        eliminarBtn = findViewById(R.id.eliminar);

        // codigo para agregar registros en la tabla de usuario
        agregarBtn.setOnClickListener {

            // obtener el texo ingresado en el EditText
            val ageText = ageTxt.text.toString();

            // agregar usuario
            val id = usuario.agregarUsuario(nameTxt.text.toString(),
                ageText.toInt())

            Toast.makeText(this, "Usuario Agregado con ID: $id",
                Toast.LENGTH_SHORT).show();

        }

        // Realizar la consulta por id
        consultarBtn.setOnClickListener {
            val idText = idTxt.text.toString();
            val id: Int = idText.toInt();
            val cursor: Cursor? = usuario.obtenerUsuarioPorID(id);

            // verificar si el cursor tiene datos
            if (cursor != null && cursor.moveToFirst()) {
                // obtener le indice de las columnas

                val idIndex = cursor.getColumnIndex("id");
                val nombreIndex = cursor.getColumnIndex("nombre");
                val edadIndex = cursor.getColumnIndex("edad");

                // mover los valores al Cursosr a los EditText
                idTxt.setText(cursor.getInt(idIndex).toString());
                nameTxt.setText(cursor.getString(nombreIndex).toString());
                ageTxt.setText(cursor.getInt(edadIndex).toString());
            }

            // cerrar el cursor despues de usuarlo
            cursor?.close();
        }
    } // end of onCreate
}