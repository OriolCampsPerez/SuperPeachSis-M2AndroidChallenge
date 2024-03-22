package helloandroid.ut3.challengeandroid;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.Random;

import helloandroid.ut3.challengeandroid.model.Asset;
import helloandroid.ut3.challengeandroid.model.Enemy;
import helloandroid.ut3.challengeandroid.model.GameCharacter;
import helloandroid.ut3.challengeandroid.model.Ghost;
import helloandroid.ut3.challengeandroid.model.Obstacle;
import helloandroid.ut3.challengeandroid.model.Wall;

public class GameView extends SurfaceView implements
        SurfaceHolder.Callback {
    private final double groundYLevel = 0.6;
    private final double characterXLevel = 0.2;
    private GameThread thread;
    private GameCharacter character;
    private double screenWidth;
    private double screenHeight;

    private int gameStage = 0;

    private int nbEnemy = 0;


    private int randomIntervalGenerated = 20;


    private int count = 0;
    private ArrayList<Obstacle> obstacles = new ArrayList<>();
    private boolean running = false;

    public GameView(Context context) {
        super(context);
        getHolder().addCallback(this);
        thread = new GameThread(getHolder(), this);

        setFocusable(true);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (canvas != null) {
            canvas.drawColor(Color.GRAY);
            Paint paint = new Paint();
            paint.setColor(Color.GREEN);
            canvas.drawRect(0, (float) ((screenHeight * groundYLevel) + (Asset.BASE_WIDTH/2)), (float) screenWidth,
                    (float) screenHeight, paint);
            paint.setColor(character.color);
            canvas.drawRect(character.getRect(), paint);
            for (Obstacle obstacle : obstacles) {
                paint = new Paint();
                paint.setColor(obstacle.color);
                canvas.drawRect(obstacle.getRect(), paint);
            }
        }
    }


    public void update() {
        try {
            Thread.sleep(30);
            this.count++;
            if (this.count % this.randomIntervalGenerated == 0) {
                this.updateObstacles();
                this.updateRandomIntervalGenerated();
                this.updateSpeed();
                this.count = 0;
                this.nbEnemy++;
            }
            obstacles.forEach(Obstacle::update);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        this.screenWidth = getHolder().getSurfaceFrame().width();
        this.screenHeight = getHolder().getSurfaceFrame().height();
        character = new GameCharacter((int) (this.screenWidth * this.characterXLevel),
                (int) (this.screenHeight * this.groundYLevel));
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while (retry) {
            try {
                thread.setRunning(false);
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            retry = false;
        }
    }


    public void addObstacle(Obstacle obstacle) {
        this.obstacles.add(obstacle);
    }

    public double getGroundYLevel() {
        return groundYLevel;
    }

    public double getCharacterXLevel() {
        return characterXLevel;
    }

    public double getScreenWidth() {
        return screenWidth;
    }

    public double getScreenHeight() {
        return screenHeight;
    }

    public void updateObstacles() {
        Random random = new Random();

        double randomNumber = random.nextDouble();
        int screenWidthObstacle = (int) (screenWidth * 1.1);
        int screenHeightObstacle = (int) (screenHeight * groundYLevel);
        if (randomNumber < 0.33) {
            this.addObstacle(new Wall(screenWidthObstacle, screenHeightObstacle));
        } else if (randomNumber < 0.66) {
            this.addObstacle(new Ghost(screenWidthObstacle, screenHeightObstacle));
        } else {
            this.addObstacle(new Enemy(screenWidthObstacle, screenHeightObstacle));
        }
    }

    public void updateRandomIntervalGenerated() {
        int min = 40;
        int max = 100;
        Random random = new Random();
        this.randomIntervalGenerated = random.nextInt(max - min + 1) + min;
    }

    public void updateSpeed() {
        if (this.nbEnemy % 5 == 0) {
            this.gameStage += 1;
            Obstacle.setSpeed(Obstacle.getSpeed() + 1);
        }
    }

}
