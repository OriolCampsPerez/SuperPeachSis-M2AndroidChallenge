package helloandroid.ut3.challengeandroid.model;

import android.graphics.Rect;

public abstract class Asset {

    public static final int BASE_WIDTH = 100;

    public int posX;
    public int posY;
    public int height;
    public int width;
    public int color;

    public Rect getRect() {
        return new Rect(this.posX, this.posY, this.posX + this.width, this.posY - this.height);
    }

}
