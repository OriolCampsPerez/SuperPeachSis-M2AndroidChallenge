package helloandroid.ut3.challengeandroid.model;

public class GameCharacter extends Asset {
    public GameCharacter(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
        this.width = BASE_WIDTH;
        this.height = BASE_WIDTH;
        this.color = 0xFF0000FF;
    }
}
