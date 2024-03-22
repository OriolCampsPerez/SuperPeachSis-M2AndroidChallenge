package helloandroid.ut3.challengeandroid.model;

import android.graphics.Bitmap;

import java.util.List;

public class Wall extends Obstacle {
    public Wall(int posX, int posY, List<Bitmap> srcList) {
        super((int) (BASE_WIDTH * 0.7), (int) (BASE_WIDTH * 0.7), posX, posY, srcList);
        this.color = 0xFFFF00FF;
    }
}
