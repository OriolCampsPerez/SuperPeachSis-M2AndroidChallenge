package helloandroid.ut3.challengeandroid.model;

public class Wall extends Obstacle {
    public Wall(int posX, int posY) {
        super(BASE_WIDTH, BASE_WIDTH, posX, posY);
        this.color = 0xFFFF00FF;
    }
}
