package helloandroid.ut3.challengeandroid.model;

import android.graphics.Bitmap;

import java.util.List;
import java.util.Random;

public abstract class Obstacle extends Asset {

    private boolean visible = true;

    private static int speed = 20;

    public Bitmap sprite;

    public Obstacle(int width, int height, int posX, int posY, List<Bitmap> srcList) {
        super(srcList);
        this.width = width;
        this.height = height;
        this.posX = posX;
        this.posY = posY;

        Random rand = new Random();
        this.sprite = srcList.get(rand.nextInt(srcList.size()));
    }
    public void update() {
        this.posX -= speed;
    }

    public static int getSpeed() {
        return speed;
    }

    public static void setSpeed(int speed) {
        Obstacle.speed = speed;
    }

    @Override
    public Bitmap getSprite() {
        return this.sprite;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}
