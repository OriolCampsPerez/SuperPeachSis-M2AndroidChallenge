package helloandroid.ut3.challengeandroid.model;

import android.graphics.Bitmap;
import android.graphics.Color;

import java.util.List;
import java.util.Random;

public class Cloud extends Asset{

    private static int speed = 3;

    public Bitmap sprite;

    public Cloud(int posX, int posY, List<Bitmap> srcList) {
        super(srcList);
        this.width = BASE_WIDTH;
        this.height = BASE_WIDTH;
        this.posX = posX;
        this.posY = posY+60;
        this.color = Color.rgb(100,100,100);

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
        Cloud.speed = speed;
    }

    @Override
    public Bitmap getSprite() {
        return this.sprite;
    }
}
