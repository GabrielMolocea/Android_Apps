package com.gabriel.slimegame;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class GameLoop extends Thread {

    public static final double MAX_UPS = 30.0;
    private static final double UPS_PERIOD = 1E+3/MAX_UPS;
    private Game game;
    private SurfaceHolder surfaceHolder;
    private boolean isRunning = false;
    private double averageUPS;
    private double averageFPS;



    public GameLoop(Game game, SurfaceHolder surfaceHolder) {
        this.surfaceHolder = surfaceHolder;
        this.game = game;
    }

    public double getAverageUPS() {
        return averageUPS;
    }

    public double getAverageFPS() {
        return averageFPS;
    }

    public void startLoop() {
        isRunning = true;
        start();
    }

    @Override
    public void run() {
        super.run();

        //  Declaring time and cycle count variables
        int updateCount = 0;
        int frameCount = 0;

        long startTime;
        long elapseTime;
        long sleepTime;

        // Game loop and is responsible for running the game
        Canvas canvas = null;
        startTime = System.currentTimeMillis();
        while (isRunning) {

            //  For each chicle is trying ro update and render game
            try {
                canvas = surfaceHolder.lockCanvas();
                synchronized (surfaceHolder) {
                    game.update();
                    updateCount++;
                    game.draw(canvas);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (canvas != null) {
                    try {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                        frameCount++;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            // Pause game to not exceed the target UPS
            elapseTime = System.currentTimeMillis() - startTime;
            sleepTime = (long) (updateCount * UPS_PERIOD - elapseTime);
            if (sleepTime > 0) {
                try {
                    sleep(sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            // Skipping frames to keep the target UPS

            while (sleepTime < 0 && updateCount < MAX_UPS - 1) {
                game.update();
                updateCount++;
                elapseTime = System.currentTimeMillis() - startTime;
                sleepTime = (long) (updateCount * UPS_PERIOD - elapseTime);
            }

            // Calculating average UPS and FPS
            elapseTime = System.currentTimeMillis() - startTime;
            if (elapseTime >=  1000) {
                averageUPS = updateCount / (1E-3 * elapseTime); // 1E-3 -> 10 at power -3
                averageFPS = frameCount  / (1E-3 * elapseTime); // 1E-3 -> 10 at power -3
                updateCount = 0;
                frameCount = 0;
                startTime = System.currentTimeMillis();
            }
        }
    }
}
