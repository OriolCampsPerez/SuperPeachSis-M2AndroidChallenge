package helloandroid.ut3.challengeandroid.model;

import android.graphics.Color;

public class Cloud extends Asset{

    private static int speed = 3;

    public Cloud(int posX, int posY) {
        this.width = BASE_WIDTH;
        this.height = BASE_WIDTH;
        this.posX = posX;
        this.posY = posY;
        this.color = Color.rgb(100,100,100);

    }
    public void update() {
        this.posX -= speed;
    }

    public static int getSpeed() {
        return speed;
    }

    public static void setSpeed(int speed) {
        Cloud.speed = speed;
    }
}
