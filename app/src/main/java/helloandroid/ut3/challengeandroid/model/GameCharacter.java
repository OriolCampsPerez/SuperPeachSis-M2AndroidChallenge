package helloandroid.ut3.challengeandroid.model;

import android.graphics.Bitmap;

import java.util.List;

public class GameCharacter extends Asset {
    public Bitmap sprite;

    public GameCharacter(int posX, int posY, List<Bitmap> srcList) {
        super(srcList);
        this.posX = posX;
        this.posY = posY;
        this.width = BASE_WIDTH;
        this.height = BASE_WIDTH;
        this.color = 0xFF0000FF;

        this.sprite = srcList.get(0);
    }

    public boolean isColliding(Obstacle obstacle) {
       return this.getRect().intersect(obstacle.getRect());
    }

    @Override
    public Bitmap getSprite() {
        return null;
    }
}
