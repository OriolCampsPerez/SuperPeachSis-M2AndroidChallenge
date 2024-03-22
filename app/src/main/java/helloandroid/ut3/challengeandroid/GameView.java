package helloandroid.ut3.challengeandroid;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.Random;

import helloandroid.ut3.challengeandroid.model.GameCharacter;
import helloandroid.ut3.challengeandroid.model.Obstacle;

public class GameView extends SurfaceView implements
        SurfaceHolder.Callback {
    private final double groundYLevel = 0.6;
    private final double characterXLevel = 0.2;
    private GameThread thread;
    private GameCharacter character;
    private double screenWidth;
    private double screenHeight;



    private int randomIntervalGenerated = 20;


    private int count= 0;
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
            canvas.drawColor(Color.WHITE);
            Paint paint = new Paint();
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
            this.count ++ ;
            // Generate a random integer between 0 and 100 (inclusive)
            if(this.count%this.randomIntervalGenerated == 0) {
                this.thread.calculGameLogic();

                int min = 40;
                int max = 100;
                // Create a Random object
                Random random = new Random();
                // Generate a random integer between min (inclusive) and max (inclusive)
                this.randomIntervalGenerated = random.nextInt(max - min + 1) + min;

                this.count = 0;

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
}
