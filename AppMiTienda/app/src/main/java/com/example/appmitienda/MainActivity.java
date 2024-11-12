package com.example.appmitienda;

import static android.provider.BaseColumns._ID;
import static com.example.appmitienda.Estructura.EstructuraDeDatos.COLUMNA_PRECIO;
import static com.example.appmitienda.Estructura.EstructuraDeDatos.COLUMNA_PRODUCTO;
import static com.example.appmitienda.Estructura.EstructuraDeDatos.COLUMNA_SECCION;
import static com.example.appmitienda.Estructura.EstructuraDeDatos.NOMBRE_TABLA;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private EditText txtProducto;
    private EditText txtPrecio;
    private EditText txtID;
    private Spinner spSeccion;

    // declaramos la clase encargada de crear y actualizar la BD
    Metodos BD;

    // creamos un arreglo de Strins con las secciones de la tienda
    String[] secciones = {"Frutas y Verduras", "Hogar", "Juguetes", "Blancos"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // enlazamos lo controles definidos (en java) con sus recursos definidos en el layout
        txtProducto = findViewById(R.id.producto);
        txtPrecio = findViewById(R.id.precio);
        txtID = findViewById(R.id.ID);
        spSeccion = findViewById(R.id.seccion);

        spSeccion.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, secciones));
    }

    // metodo para guardar un producto en la tabla
    public void guardarProducto(View v) {
        // se inicializa la BD
        BD = new Metodos(this);
        SQLiteDatabase sqlite = BD.getWritableDatabase();

        // Leer campos del UI
        String producto = txtProducto.getText().toString();
        String precio = txtPrecio.getText().toString();
        String seccion = spSeccion.getSelectedItem().toString();

        ContentValues contenedor = new ContentValues();

        if (producto.equals("") || precio.equals("")) {
            Toast.makeText(this, "Los campos \"Producto\" y \"Precio\" son obligatorios",
                    Toast.LENGTH_LONG).show();
        } else {
            // Se agregan los valores introducidos, con estructura de par.
            contenedor.put(COLUMNA_PRODUCTO, producto);
            contenedor.put(COLUMNA_PRECIO, precio);
            contenedor.put(COLUMNA_SECCION, seccion);
            sqlite.insert(NOMBRE_TABLA, null, contenedor);

            txtProducto.setText("");
            txtPrecio.setText("");
            spSeccion.setSelection(0);

            // notificamos al usuario de que el producto ha sido eliminado
            Toast.makeText(this, "Nuevo producto agregado: " + producto, Toast.LENGTH_LONG).show();

        }
    }

    public void buscarProducto(View v) {
        BD = new Metodos(this);
        SQLiteDatabase sqlite = BD.getWritableDatabase();

        String[] columnas = {_ID, COLUMNA_PRODUCTO, COLUMNA_PRECIO, COLUMNA_SECCION};
        String queryProducto = COLUMNA_PRODUCTO + " LIKE ?";
        String[] args = {"%" + txtProducto.getText().toString() + "%"};
        String orden = COLUMNA_PRODUCTO + " DESC";

        Cursor result = sqlite.query(NOMBRE_TABLA, columnas, queryProducto, args, null, null, orden);

        if (result.getCount() != 0) {
            result.moveToFirst();

            long idProducto = result.getLong(result.getColumnIndexOrThrow(_ID));
            txtID.setText(String.valueOf(idProducto));

            String producto = result.getString(result.getColumnIndexOrThrow(COLUMNA_PRODUCTO));
            txtProducto.setText(producto);

            String precio = result.getString(result.getColumnIndexOrThrow(COLUMNA_PRECIO));
            txtPrecio.setText(precio);

            String seccion = result.getString(result.getColumnIndexOrThrow(COLUMNA_SECCION));
            for (int i = 0; i < secciones.length; i++) {
                if (seccion.equals(secciones[i])) {
                    spSeccion.setSelection(i);
                }
            }
        } else {
            Toast.makeText(this, "PRODUCTO NO ENCONTRADO", Toast.LENGTH_SHORT).show();
        }

        result.close(); // Cerrar el cursor después de usarlo
        sqlite.close(); // Cerrar la base de datos después de usarla
    }

    public void modificarProducto(View v) {
        BD = new Metodos(this);
        SQLiteDatabase sqlite = BD.getWritableDatabase();

        Long idModificar = Long.valueOf(txtID.getText().toString());
        String producto = txtProducto.getText().toString();
        String precio = txtPrecio.getText().toString();
        String seccion = spSeccion.getSelectedItem().toString();

        ContentValues content = new ContentValues();
        content.put(COLUMNA_PRODUCTO, producto);
        content.put(COLUMNA_PRECIO, precio);
        content.put(COLUMNA_SECCION, seccion);

        String seleccion = _ID + " LIKE ?";
        String[] args = {String.valueOf(idModificar)};

        int count = sqlite.update(NOMBRE_TABLA, content, seleccion, args);
        Toast.makeText(this, "Se actualizó el producto " + producto, Toast.LENGTH_LONG).show();

        // Limpiar los campos de captura
        txtID.setText("");
        txtProducto.setText("");
        txtPrecio.setText("");
        spSeccion.setSelection(0);

        sqlite.close();
    }

    public void eliminarProducto(View v) {
        String producto = txtProducto.getText().toString();

        BD = new Metodos(this);
        SQLiteDatabase sqlite = BD.getWritableDatabase();

        String query = COLUMNA_PRODUCTO + " LIKE ?";
        String[] args = {producto};

        sqlite.delete(NOMBRE_TABLA, query, args);

        // Limpiar los campos
        txtID.setText("");
        txtProducto.setText("");
        txtPrecio.setText("");
        spSeccion.setSelection(0);

        Toast.makeText(this, "Se eliminó el producto " + producto, Toast.LENGTH_LONG).show();

        sqlite.close();
    }
}
