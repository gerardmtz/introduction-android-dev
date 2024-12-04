package com.example.appbouncingballgame;

public class Ball {

    public float x, y;
    public float vx = 10, vy = 10;
    public int radius = 50;

    public Ball(int screenX, int screenY) {
        x = screenX / 2;
        y = screenY / 2;
    }

    public void update(int screenX, int screenY) {
        x += vx;
        y += vy;

        // Rebote en los bordes
        if ((x - radius) < 0 || (x + radius) > screenX) {
            vx = -vx;
        }
        if ((y - radius) < 0 || (y + radius) > screenY) {
            vy = -vy;
        }
    }
}
