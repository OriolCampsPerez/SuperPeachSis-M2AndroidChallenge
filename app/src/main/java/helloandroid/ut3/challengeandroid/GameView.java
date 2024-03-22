package helloandroid.ut3.challengeandroid;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
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
import helloandroid.ut3.challengeandroid.utils.ResourceFetcher;

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
            canvas.drawRect(0, (float) ((screenHeight * groundYLevel) + (Asset.BASE_WIDTH / 2)), (float) screenWidth,
                    (float) screenHeight, paint);
            Rect rectTopBackground = new Rect(0, 0, (int) screenWidth, (int) (screenHeight * groundYLevel + (Asset.BASE_WIDTH / 2)));
            Rect rectBotBackground = new Rect(0, (int) ((screenHeight * groundYLevel) + (Asset.BASE_WIDTH / 2)), (int) screenWidth, (int) screenHeight);
            canvas.drawBitmap(ResourceFetcher.getBackgroundBitmap(getContext()), null, rectTopBackground, null);
            canvas.drawRect(rectBotBackground, paint);

            paint.setColor(character.color);
            canvas.drawRect(character.getRect(), paint);
            for (Obstacle obstacle : obstacles) {
                paint = new Paint();
                paint.setColor(obstacle.color);
                canvas.drawBitmap(obstacle.getSprite(), null, obstacle.getRect(), paint);
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
            if (this.isColliding()) {
                // this.thread.setRunning(false);
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
                (int) (this.screenHeight * this.groundYLevel), ResourceFetcher.getGameCharacterBitmap(getContext()));
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

    public void updateObstacles() {
        Random random = new Random();

        double randomNumber = random.nextDouble();
        int screenWidthObstacle = (int) (screenWidth * 1.1);
        int screenHeightObstacle = (int) (screenHeight * groundYLevel);
        if (randomNumber < 0.33) {
            this.addObstacle(new Wall(screenWidthObstacle, screenHeightObstacle, ResourceFetcher.getWallsBitmap(getContext())));
        } else if (randomNumber < 0.66) {
            this.addObstacle(new Ghost(screenWidthObstacle, screenHeightObstacle, ResourceFetcher.getGhostsBitmap(getContext())));
        } else {
            this.addObstacle(new Enemy(screenWidthObstacle, screenHeightObstacle, ResourceFetcher.getEnemiesBitmap(getContext())));
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

    private boolean isColliding() {
        for (Obstacle obstacle : obstacles) {
            if (character.isColliding(obstacle)) {
                return true;
            }
        }
        return false;
    }

}
