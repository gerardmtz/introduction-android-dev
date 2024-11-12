package com.example.appsharedpreferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class MainActivity extends AppCompatActivity {

    private EditText userTxt;
    private EditText passwordTxt;
    private Button buttonSave;
    private Button buttonRecovery;
    private EditText recoverTxt;
    private EditText recoveredPasswordTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.passwordTxt), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Variables mapeadas a los elementos del UI
        userTxt = findViewById(R.id.userTxt);
        passwordTxt = findViewById(R.id.userPasswordTxt);
        recoverTxt = findViewById(R.id.recoverTxt);
        buttonSave = findViewById(R.id.buttonSave);
        buttonRecovery = findViewById(R.id.passwordButton);
        recoveredPasswordTxt = findViewById(R.id.recoveredPasswordTxt);

        // Crear EncryptedSharedPreferences
        SharedPreferences sp = null;
        try {
            sp = EncryptedSharedPreferences.create(
                    "MySecurePrefs",
                    MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC),
                    getApplicationContext(),
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            );
        } catch (GeneralSecurityException | IOException e) {
            e.printStackTrace();
        }

        if (sp != null) {
            SharedPreferences finalSp = sp;

            // Guardar usuario y contraseña
            buttonSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String user = userTxt.getText().toString();
                    String password = passwordTxt.getText().toString();

                    SharedPreferences.Editor editor = finalSp.edit();
                    editor.putString(user + "_password", password);  // Almacenar usando el nombre de usuario como clave
                    editor.apply();
                    Toast.makeText(MainActivity.this, "Credenciales guardadas de forma segura", Toast.LENGTH_SHORT).show();

                    // Borramos los datos guarados en los campos
                    userTxt.setText("");
                    passwordTxt.setText("");
                }
            });

            // Recuperar la contraseña
            buttonRecovery.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String username = recoverTxt.getText().toString();
                    String savedPassword = finalSp.getString(username + "_password", null);

                    if (savedPassword != null) {
//                        Toast.makeText(MainActivity.this, "Contraseña recuperada: " + savedPassword, Toast.LENGTH_LONG).show();
                        recoveredPasswordTxt.setText(savedPassword);
                        Toast.makeText(MainActivity.this, "Contraseña recuperada", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(MainActivity.this, "Usuario no encontrado", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
