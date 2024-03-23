package helloandroid.ut3.challengeandroid.model;

import android.graphics.Bitmap;

import java.util.List;

public class Ghost extends Obstacle {
    public Ghost(int posX, int posY, List<Bitmap> srcList) {
        super((int) (BASE_WIDTH * 0.8), (int) (BASE_WIDTH * 0.8), posX, posY-BASE_WIDTH/2 - 35, srcList);
        this.color = 0xFF00FFFF;
    }
}
