package com.example.appbouncingballgame;

public class Score {
    private int id;
    private int points;
    private long timestamp;

    /**
     * Constructor de Score.
     *
     * @param id        El ID de la puntuación en la base de datos.
     * @param points    La puntuación obtenida.
     * @param timestamp El tiempo en que se obtuvo la puntuación (en milisegundos).
     */
    public Score(int id, int points, long timestamp) {
        this.id = id;
        this.points = points;
        this.timestamp = timestamp;
    }

    // Getters
    public int getId() {
        return id;
    }

    public int getPoints() {
        return points;
    }

    public long getTimestamp() {
        return timestamp;
    }

    // Setters (opcional, si necesitas modificar las puntuaciones)
    public void setId(int id) {
        this.id = id;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
