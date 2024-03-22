package helloandroid.ut3.challengeandroid;

import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

import java.util.Random;

import helloandroid.ut3.challengeandroid.model.Enemy;
import helloandroid.ut3.challengeandroid.model.Ghost;
import helloandroid.ut3.challengeandroid.model.Wall;

public class GameThread extends Thread {
    private final SurfaceHolder surfaceHolder;
    private final GameView gameView;

    Canvas canvas;
    Boolean running;

    public GameThread(SurfaceHolder surfaceHolder, GameView gameView) {
        super();
        this.surfaceHolder = surfaceHolder;
        this.gameView = gameView;
    }

    @Override
    public void run() {
        while (running) {
            canvas = null;
            try {
                canvas = this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder) {
                    this.gameView.update();
                    this.gameView.draw(canvas);
                }
            } catch (Exception e) {
            } finally {
                if (canvas != null) {
                    try {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }


    public void calculGameLogic() {
        Random random = new Random();

        double randomNumber = random.nextDouble();
        int screenWidthObstacle = (int) (this.gameView.getScreenWidth() * 1.1);
        int screenHeightObstacle = (int) (this.gameView.getScreenHeight() * this.gameView.getGroundYLevel());
        if (randomNumber < 0.33) {

            this.gameView.addObstacle(new Wall(screenWidthObstacle, screenHeightObstacle));
        } else if (randomNumber < 0.66) {
            this.gameView.addObstacle(new Ghost(screenWidthObstacle, screenHeightObstacle));
        } else {
            this.gameView.addObstacle(new Enemy(screenWidthObstacle, screenHeightObstacle));
        }

    }

    public void setRunning(boolean isRunning) {
        running = isRunning;
    }
}
