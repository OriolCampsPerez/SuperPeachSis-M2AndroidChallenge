package helloandroid.ut3.challengeandroid.model;

import android.graphics.Bitmap;
import android.graphics.Rect;

import java.util.List;

public abstract class Asset {

    public static final int BASE_WIDTH = 220;

    public int posX;
    public int posY;
    public int height;
    public int width;
    public int color;

    public List<Bitmap> srcList;

    public Asset(List<Bitmap> srcList) {
        this.srcList = srcList;
    }

    /**
     * Used to display the asset on the screen
     *
     * @return the Rect
     */
    public Rect getRect() {
        return new Rect(this.posX, this.posY - this.height, this.posX + this.width, this.posY);
    }

    /**
     * Used to display the asset sprite on the screen
     * @return the Bitmap Sprite
     */
    public abstract Bitmap getSprite();
}
