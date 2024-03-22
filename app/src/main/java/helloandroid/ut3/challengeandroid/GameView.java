package helloandroid.ut3.challengeandroid;

import static androidx.core.content.ContextCompat.getSystemService;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.provider.Settings;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.Random;

import helloandroid.ut3.challengeandroid.model.Asset;
import helloandroid.ut3.challengeandroid.model.Cloud;
import helloandroid.ut3.challengeandroid.model.Enemy;
import helloandroid.ut3.challengeandroid.model.GameCharacter;
import helloandroid.ut3.challengeandroid.model.Ghost;
import helloandroid.ut3.challengeandroid.model.Obstacle;
import helloandroid.ut3.challengeandroid.model.Wall;

public class GameView extends SurfaceView implements
        SurfaceHolder.Callback, SensorEventListener {
    private final double groundYLevel = 0.8;
    private final double characterXLevel = 0.2;
    private GameThread thread;
    private GameCharacter character;
    private double screenWidth;
    private double screenHeight;

    private SensorManager sensorManager;
    private Sensor accelerometer;


    private static final float SHAKE_THRESHOLD = 3.0f; // Adjust this threshold as needed
    private long lastShakeTime;

    private MediaPlayer swordMediaPlayer;

    private MediaPlayer musicPlayer;

    private MediaPlayer gameOver;

    private int gameStage = 0;
    Paint linePaint ;


    private int nbEnemy = 0;

    private Paint paint;

    private int lineAlpha = 254;

    private PointF startPoint = new PointF();

    private PointF endPoint = new PointF();





    private int randomIntervalGenerated = 20;

    private int count = 0;
    private ArrayList<Obstacle> obstacles = new ArrayList<>();


    private ArrayList<Cloud> myClouds = new ArrayList<>();

    private boolean isDark = false;

    public GameView(Context context) {
        super(context);
        getHolder().addCallback(this);
        thread = new GameThread(getHolder(), this);
        setFocusable(true);
        linePaint = new Paint();

        swordMediaPlayer = MediaPlayer.create(this.getContext(), R.raw.sword_sound);

        musicPlayer = MediaPlayer.create(this.getContext(), R.raw.lade);
        musicPlayer.start();


        gameOver = MediaPlayer.create(this.getContext(), R.raw.gameover);

        musicPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                musicPlayer.seekTo(0); // Reset to start of the audio file
                musicPlayer.start(); // Start playback again
            }
        });


        linePaint.setStrokeWidth(5);
        linePaint.setStyle(Paint.Style.STROKE);
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


    private void playSound() {
        if (swordMediaPlayer != null) {
            swordMediaPlayer.start();
        }
    }

    private void stopSound() {
        if (swordMediaPlayer != null) {
            swordMediaPlayer.stop();
        }
    }




    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getActionMasked();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                startPoint.set(event.getX(), event.getY());

                swordMediaPlayer.seekTo(0); // Reset to start of the audio file

                break;
            case MotionEvent.ACTION_MOVE:
                endPoint.set(event.getX(), event.getY());

                break;
            case MotionEvent.ACTION_UP:
                endPoint.set(event.getX(), event.getY());
                playSound();
                markObstacleForDestruction();
                break;
        }
        return true;
    }


    private void markObstacleForDestruction() {
        for (Obstacle obstacle : obstacles) {
            Rect rec = obstacle.getRect();
            if (obstacle instanceof Enemy && rec.intersects((int) startPoint.x, (int) startPoint.y, (int) endPoint.x, (int) endPoint.y)) {
                ((Enemy) obstacle).setVisible(false);

                break;
            }
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (canvas != null) {


            canvas.drawColor(Color.rgb(50,50,130));
            Paint paint = new Paint();
            paint.setColor(Color.GREEN);

            canvas.drawLine(startPoint.x, startPoint.y, endPoint.x, endPoint.y, linePaint);

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

            for (Cloud cloud : myClouds) {

                paint = new Paint();
                paint.setColor(cloud.color);
                canvas.drawRect(cloud.getRect(), paint);
            }
        }
    }




    public void update() {
        try {
            Thread.sleep(30);
            this.count++;
            ArrayList<Obstacle> obstaclesToRemove = new ArrayList<>();
            this.updateClouds();

            if (this.count % this.randomIntervalGenerated == 0) {
                this.updateObstacles();
                this.updateRandomIntervalGenerated();
                this.updateSpeed();
                this.count = 0;
                this.nbEnemy++;
            }
                if (this.isColliding()) {
                    musicPlayer.stop();
                    gameOver.start();
                // this.thread.setRunning(false);
            }
            this.character.update();
            obstacles.forEach(Obstacle::update);
            myClouds.forEach(Cloud::update);

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
            this.addObstacle(new Wall(screenWidthObstacle, screenHeightObstacle));
        } else if (randomNumber < 0.66) {
            Ghost ghost = new Ghost(screenWidthObstacle, screenHeightObstacle);
            ghost.setVisible(!isDark);
            this.addObstacle(ghost);
        } else {
            this.addObstacle(new Enemy(screenWidthObstacle, screenHeightObstacle));
        }
    }


    public void updateClouds() {
        Random random = new Random();

        double randomNumber = random.nextDouble();
        int screenWidthObstacle = (int) (screenWidth * 1.1);
        int screenHeightObstacle = (int) (screenHeight * groundYLevel);

        // Generate a random number between 50 and 150
        int randomInt = random.nextInt(200) + 80; // Generates a number between 0 and 100, then add 50

        if (randomNumber < 0.01) {
            this.myClouds.add(new Cloud(screenWidthObstacle, randomInt));
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
