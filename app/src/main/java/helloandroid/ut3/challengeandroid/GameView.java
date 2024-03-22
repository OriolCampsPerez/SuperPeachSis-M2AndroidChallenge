package helloandroid.ut3.challengeandroid;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.graphics.Rect;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.Random;

import helloandroid.ut3.challengeandroid.model.Enemy;
import helloandroid.ut3.challengeandroid.model.GameCharacter;
import helloandroid.ut3.challengeandroid.model.Ghost;
import helloandroid.ut3.challengeandroid.model.Obstacle;
import helloandroid.ut3.challengeandroid.model.Wall;
import helloandroid.ut3.challengeandroid.utils.ResourceFetcher;

public class GameView extends SurfaceView implements
        SurfaceHolder.Callback, SensorEventListener {
    private final double groundYLevel = 0.85;
    private final double characterXLevel = 0.15;
    private GameThread thread;
    private GameCharacter character;
    private double screenWidth;
    private double screenHeight;

    private SensorManager sensorManager;
    private Sensor accelerometer;
    private static final float SHAKE_THRESHOLD = 3.0f; // Adjust this threshold as needed
    private long lastShakeTime;

    private int gameStage = 0;

    private int nbEnemy = 0;

    private int randomIntervalGenerated = 20;

    private int count = 0;
    private ArrayList<Obstacle> obstacles = new ArrayList<>();

    private boolean isDark = false;

    public GameView(Context context) {
        super(context);
        getHolder().addCallback(this);
        thread = new GameThread(getHolder(), this);
        setFocusable(true);

        this.sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager != null) {
            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        }
        lastShakeTime = System.currentTimeMillis();

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



            canvas.drawBitmap(ResourceFetcher.getBackgroundBitmap(getContext()), null, new Rect(0, 0, (int) screenWidth, (int) screenHeight), null);
            canvas.drawBitmap(character.getSprite(), null, character.getRect(), null);

            for (Obstacle obstacle : obstacles) {
                if (!obstacle.isVisible()) {
                    continue;
                }
                canvas.drawBitmap(obstacle.getSprite(), null, obstacle.getRect(), null);
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
            this.character.update();
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
        if (accelerometer != null) {
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while (retry) {
            try {
                thread.setRunning(false);
                thread.join();
                if (sensorManager != null) {
                    sensorManager.unregisterListener(this);
                }
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
            Ghost ghost = new Ghost(screenWidthObstacle, screenHeightObstacle, ResourceFetcher.getGhostsBitmap(getContext()));
            ghost.setVisible(!isDark);
            this.addObstacle(ghost);
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

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            long currentTime = System.currentTimeMillis();
            if ((currentTime - lastShakeTime) > 1000) { // Minimum interval between shakes
                float x = event.values[0];
                float y = event.values[1];
                float z = event.values[2];

                double acceleration = Math.sqrt(x * x + y * y + z * z) - SensorManager.GRAVITY_EARTH;
                if (acceleration > SHAKE_THRESHOLD) {
                    // Shake detected, call your method here
                    onShake();
                    lastShakeTime = currentTime;
                }
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Not used, but required to implement SensorEventListener
    }

    private void onShake() {
        this.character.jump();
    }

}
