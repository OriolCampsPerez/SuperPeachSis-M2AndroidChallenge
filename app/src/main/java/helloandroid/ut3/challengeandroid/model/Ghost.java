package helloandroid.ut3.challengeandroid.model;

public class Ghost extends Obstacle {
    public Ghost(int posX, int posY) {
        super(BASE_WIDTH, BASE_WIDTH * 2, posX, posY);
        this.color = 0xFF00FFFF;
    }
}
