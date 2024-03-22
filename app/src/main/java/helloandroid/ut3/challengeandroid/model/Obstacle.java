package helloandroid.ut3.challengeandroid.model;

public abstract class Obstacle extends Asset {

    private static int speed = 20;

    public Obstacle(int width, int height, int posX, int posY) {
        this.width = width;
        this.height = height;
        this.posX = posX;
        this.posY = posY;
    }
    public void update() {
        this.posX -= speed;
    }

    public static int getSpeed() {
        return speed;
    }

    public static void setSpeed(int speed) {
        Obstacle.speed = speed;
    }



}
