package com.example.appbouncingballgame;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * HighScoresActivity muestra las mejores puntuaciones del jugador.
 */
public class HighScoresActivity extends AppCompatActivity {

    private ListView scoresListView;
    private DatabaseHelper dbHelper;
    private ArrayAdapter<String> adapter;
    private List<Score> topScores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_scores);

        // Inicializa los componentes
        scoresListView = findViewById(R.id.scoresListView);
        dbHelper = new DatabaseHelper(this);

        // Recupera las mejores puntuaciones en un hilo separado
        new RetrieveScoresTask().execute(10);
    }

    /**
     * AsyncTask para recuperar las mejores puntuaciones de la base de datos.
     */
    private class RetrieveScoresTask extends android.os.AsyncTask<Integer, Void, List<Score>> {
        @Override
        protected List<Score> doInBackground(Integer... params) {
            int limit = params[0];
            return dbHelper.getTopScores(limit);
        }

        @Override
        protected void onPostExecute(List<Score> scores) {
            topScores = scores;
            displayScores();
        }
    }

    /**
     * Muestra las puntuaciones recuperadas en el ListView.
     */
    private void displayScores() {
        String[] displayScores = new String[topScores.size()];
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());

        for (int i = 0; i < topScores.size(); i++) {
            Score score = topScores.get(i);
            String date = sdf.format(new Date(score.getTimestamp()));
            displayScores[i] = (i + 1) + ". Puntos: " + score.getPoints() + " - Fecha: " + date;
        }

        // Configura el adaptador
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, displayScores);
        scoresListView.setAdapter(adapter);
    }
}
