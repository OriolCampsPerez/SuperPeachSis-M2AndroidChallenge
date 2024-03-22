package helloandroid.ut3.challengeandroid.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.ArrayList;
import java.util.List;

import helloandroid.ut3.challengeandroid.R;

public class Enemy extends Obstacle {
    public Enemy(int posX, int posY, List<Bitmap> srcList) {
        super(BASE_WIDTH, BASE_WIDTH * 3, posX, posY, srcList);
        this.color = 0xCC0000FF;
    }
}
