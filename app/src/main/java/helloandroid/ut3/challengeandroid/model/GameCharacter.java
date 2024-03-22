package helloandroid.ut3.challengeandroid.model;

import android.graphics.Bitmap;

import java.util.List;

import android.graphics.Color;

public class GameCharacter extends Asset {
    public Bitmap sprite;
    private static final double JUMP_AMPLITUDE = 40; // Adjust jump amplitude as needed
    private static final double JUMP_FREQUENCY = 2.0; // Adjust jump frequency as needed
    private static final int JUMP_DURATION = 3400; // Adjust jump duration as needed

    private static int BASE_GROUND_Y = 0;
    private long jumpStartTime;

    private boolean isJumping;

    public GameCharacter(int posX, int posY, List<Bitmap> srcList) {
        super(srcList);
        this.posX = posX;
        this.posY = posY;
        this.width = BASE_WIDTH-40;
        this.height = BASE_WIDTH;

        BASE_GROUND_Y = posY;

        this.color = Color.BLACK;
        this.jumpStartTime = 0;
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
            jumpStartTime = System.currentTimeMillis();
            isJumping = true;
        }
    }

    public void update() {
        if (isJumping) {
            long elapsedTime = System.currentTimeMillis() - jumpStartTime;
            if (elapsedTime < JUMP_DURATION / 2) {
                // Calculate jump progress based on elapsed time
                double progress = (double) elapsedTime / JUMP_DURATION;
                // Use quadratic curve for smooth jump effect
                int jumpHeight = (int) (JUMP_AMPLITUDE * Math.sin(2 * Math.PI * JUMP_FREQUENCY * progress));
                this.posY = (int) (this.posY - jumpHeight);
            } else if (elapsedTime < JUMP_DURATION) {
                // Jump completed, reset jump state
                double progress = (double) elapsedTime / JUMP_DURATION;
                // Use quadratic curve for smooth jump effect
                int jumpHeight = (int) (JUMP_AMPLITUDE * Math.sin(2 * Math.PI * JUMP_FREQUENCY * progress));
                if (this.posY + jumpHeight < BASE_GROUND_Y) {
                    this.posY = (int) (this.posY + jumpHeight);
                } else {
                    jumpStartTime = 0;
                    isJumping = false;
                    this.posY = BASE_GROUND_Y;
                }

            } else {
                jumpStartTime = 0;
                isJumping = false;
                this.posY = BASE_GROUND_Y;
            }
        }
    }

    @Override
    public Bitmap getSprite() {
        return this.sprite;
    }
}
