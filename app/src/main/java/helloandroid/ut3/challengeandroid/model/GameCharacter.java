package helloandroid.ut3.challengeandroid.model;

public class GameCharacter extends Asset {

    private static final double JUMP_AMPLITUDE = 30; // Adjust jump amplitude as needed
    private static final double JUMP_FREQUENCY = 2.0; // Adjust jump frequency as needed
    private static final int JUMP_DURATION = 1900; // Adjust jump duration as needed

    private static int BASE_GROUND_Y = 0;
    private long jumpStartTime;
    private boolean isJumping;

    public GameCharacter(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
        this.width = BASE_WIDTH;
        this.height = BASE_WIDTH;

        BASE_GROUND_Y = posY;

        this.color = 0xFF0000FF;
        this.jumpStartTime = 0;
        this.isJumping = false;
    }

    public boolean isColliding(Obstacle obstacle) {
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
            if (elapsedTime < JUMP_DURATION/2) {
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
                if(this.posY + jumpHeight< BASE_GROUND_Y) {
                    this.posY = (int) (this.posY + jumpHeight);
                }
                else {
                    jumpStartTime = 0;
                    isJumping = false;
                    this.posY = BASE_GROUND_Y;
                }

            }
            else {
                jumpStartTime = 0;
                isJumping = false;
                this.posY = BASE_GROUND_Y;
            }
        }
    }
}
