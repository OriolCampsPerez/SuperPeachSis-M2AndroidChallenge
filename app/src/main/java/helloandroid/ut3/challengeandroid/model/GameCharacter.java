package helloandroid.ut3.challengeandroid.model;

import static helloandroid.ut3.challengeandroid.utils.ResourceFetcher.NB_JUMP_SPRITES;
import static helloandroid.ut3.challengeandroid.utils.ResourceFetcher.NB_RUN_SPRITES;

import android.graphics.Bitmap;

import java.util.List;

import android.graphics.Color;

public class GameCharacter extends Asset {
    public Bitmap sprite;
    private boolean isJumping;
    private int tempoFrameI = 0;
    public List<Bitmap> srcJumpList;
    private final int basePosY;
    private final int MAX_JUMP_HEIGHT = 350;

    public GameCharacter(int posX, int posY, List<Bitmap> runSprites, List<Bitmap> jumpSprites) {
        super(runSprites);
        this.srcJumpList = jumpSprites;

        this.posX = posX;
        this.posY = posY;
        this.width = BASE_WIDTH - 40;
        this.height = BASE_WIDTH;

        this.basePosY = posY;

        this.color = Color.BLACK;
        this.isJumping = false;

        this.sprite = srcList.get(0);
    }

    public boolean isColliding(Obstacle obstacle) {
        if (!obstacle.isVisible()) {
            return false;
        }
        return this.getRect().intersect(obstacle.getRect());
    }

    public void jump() {
        if (!isJumping) {
            isJumping = true;
            tempoFrameI = 0;
            this.sprite = srcJumpList.get(tempoFrameI);
        }
    }

    public void update() {
        tempoFrameI++;
        if (!isJumping) { // Running
            this.sprite = srcList.get(tempoFrameI % NB_RUN_SPRITES);
        } else { // Jumping
            this.sprite = srcJumpList.get(tempoFrameI % NB_JUMP_SPRITES);
            if ((int) (tempoFrameI) < NB_JUMP_SPRITES) {
                posY = basePosY - (int) (MAX_JUMP_HEIGHT * Math.sin(Math.PI * tempoFrameI / NB_JUMP_SPRITES));
            } else {
                posY = basePosY;
                isJumping = false;
            }
        }
    }

    @Override
    public Bitmap getSprite() {
        return this.sprite;
    }
}
