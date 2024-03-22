package helloandroid.ut3.challengeandroid.model;

import android.graphics.Bitmap;

import java.util.List;

public class Ghost extends Obstacle {
    public Ghost(int posX, int posY, List<Bitmap> srcList) {
        super(BASE_WIDTH, BASE_WIDTH, posX, posY, srcList);
        this.color = 0xFF00FFFF;
    }
}
