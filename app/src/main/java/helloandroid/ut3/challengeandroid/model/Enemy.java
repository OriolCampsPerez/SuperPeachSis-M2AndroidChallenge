package helloandroid.ut3.challengeandroid.model;

import android.graphics.Color;

public class Enemy extends Obstacle {
    public Enemy(int posX, int posY) {
        super(BASE_WIDTH, BASE_WIDTH * 3, posX, posY);
        this.color = Color.BLACK;
    }




}
