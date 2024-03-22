package helloandroid.ut3.challengeandroid.model;

import android.graphics.Bitmap;
import android.graphics.Color;

import java.util.List;

public class Enemy extends Obstacle {
    public Enemy(int posX, int posY, List<Bitmap> srcList) {
        super((int) (BASE_WIDTH * 1.2), (int) (BASE_WIDTH * 1.5), posX, posY, srcList);
        this.color = Color.BLACK;
    }
}
