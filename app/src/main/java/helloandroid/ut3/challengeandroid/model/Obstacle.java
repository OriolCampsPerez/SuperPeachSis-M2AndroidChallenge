package helloandroid.ut3.challengeandroid.model;

import android.graphics.Rect;

public abstract class Obstacle extends Asset {

    public Obstacle(int width, int height, int posX, int posY) {
        this.width = width;
        this.height = height;
        this.posX = posX;
        this.posY = posY;
    }
    public void update() {
        this.posX -= 10;
    }
}
