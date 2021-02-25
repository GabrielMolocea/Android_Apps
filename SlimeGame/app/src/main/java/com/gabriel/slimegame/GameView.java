package com.gabriel.slimegame;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Build;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements Runnable {
    private Thread thread;
    private boolean isPlaying, isGameOver = false;
    private GameActivity activity;
    private int screenX, screenY, score = 0;
    private static float screenRatioX, screenRatioY;
    private Paint paint;
    private SharedPreferences preferences;

    public GameView(GameActivity activity, int screenX, int screenY) {
        super(activity);
        this.activity = activity;
        this.screenX = screenX;
        this.screenY = screenY;
        preferences = activity.getSharedPreferences("game", Context.MODE_PRIVATE);

        // Checking for the version of device
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

        }

        // Setting the screenRatio
        screenRatioX = 1920f / screenX;
        screenRatioY = 1080f / screenY;

    }

    @Override
    public void run() {

    }

    public void pause() {
    }

    public void resume() {
    }
}
