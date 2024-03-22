package helloandroid.ut3.challengeandroid;

import static androidx.core.content.ContextCompat.getSystemService;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
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
    private final double groundYLevel = 0.8;
    private final double characterXLevel = 0.2;
    private GameThread thread;
    private GameCharacter character;
    private double screenWidth;
    private double screenHeight;

    private int gameStage = 0;

    private int nbEnemy = 0;
    private int randomIntervalGenerated = 20;

    private SensorManager sensorManager;
    private int count = 0;
    private ArrayList<Obstacle> obstacles = new ArrayList<>();

    private boolean isDark = false;

    public GameView(Context context) {
        super(context);
        getHolder().addCallback(this);
        thread = new GameThread(getHolder(), this);
        setFocusable(true);
        this.sensorManager = (SensorManager) getSystemService(context,
                SensorManager.class);
        Sensor lightSensor = this.sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        SensorEventListener lightSensorListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                float lux = event.values[0];
                isDark = lux < 10;
                for (Obstacle obstacle : obstacles) {
                    if (obstacle instanceof Ghost) {
                        Ghost ghost = (Ghost) obstacle;
                        ghost.setVisible(!isDark);
                    }
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
                // Gérer les changements de précision si nécessaire
            }
        };
        sensorManager.registerListener(lightSensorListener, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
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
            canvas.drawRect(0, (float) ((screenHeight * groundYLevel) - (Asset.BASE_WIDTH / 2)),
                    (float) screenWidth,
                    (float) screenHeight, paint);
            paint.setColor(character.color);
            canvas.drawRect(character.getRect(), paint);
            for (Obstacle obstacle : obstacles) {
                if (!obstacle.isVisible()) {
                    continue;
                }
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
            ArrayList<Obstacle> obstaclesToRemove = new ArrayList<>();
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

    public void updateObstacles() {
        Random random = new Random();

        double randomNumber = random.nextDouble();
        int screenWidthObstacle = (int) (screenWidth * 1.1);
        int screenHeightObstacle = (int) (screenHeight * groundYLevel);
        if (randomNumber < 0.33) {
            this.addObstacle(new Wall(screenWidthObstacle, screenHeightObstacle));
        } else if (randomNumber < 0.66) {
            Ghost ghost = new Ghost(screenWidthObstacle, screenHeightObstacle);
            ghost.setVisible(!isDark);
            this.addObstacle(ghost);
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

    private boolean isColliding() {
        for (Obstacle obstacle : obstacles) {
            if (character.isColliding(obstacle)) {
                return true;
            }
        }
        return false;
    }

}
