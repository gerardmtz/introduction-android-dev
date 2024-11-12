package com.example.appmenu;

import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    //Varables
    public final static int OPC1 = 1;
    public final static int OPC2 = 2;
    public final static int OPC3 = 3;
    public final static int OPC31 = 4;
    public final static int OPC32 = 5;


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

        //Refencia al Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Ejer. Menu");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem option1 = menu.add(Menu.NONE, OPC1, Menu.NONE, "Option 1");
        setColor(option1, Color.BLACK);

        MenuItem option2 = menu.add(Menu.NONE, OPC2, Menu.NONE, "Option 2");
        setColor(option2, Color.BLACK);

        Menu submenu = menu.addSubMenu(Menu.NONE, OPC3, Menu.NONE, "Options 3");
        
        MenuItem option31 = submenu.add(Menu.NONE, OPC31, Menu.NONE, "Option 3.1");
        setColor(option31, Color.BLACK);

        MenuItem option32 = submenu.add(Menu.NONE, OPC32, Menu.NONE, "Option 3.2");
        setColor(option32, Color.BLACK);

        return true;
    }

    private void setColor(MenuItem item, int color) {
        SpannableString s = new SpannableString(item.getTitle());
        s.setSpan(new ForegroundColorSpan(color), 0, s.length(), 0);
        item.setTitle(s);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case OPC1:
                Toast.makeText(this, "You selected option 1", Toast.LENGTH_SHORT).show();
                return true;
            case OPC2:
                Toast.makeText(this, "You selected option 2", Toast.LENGTH_SHORT).show();
                return true;
            case OPC31:
                Toast.makeText(this, "You selected option 3.1", Toast.LENGTH_SHORT).show();
                return true;
            case OPC32:
                Toast.makeText(this, "You selected option 3.2", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}