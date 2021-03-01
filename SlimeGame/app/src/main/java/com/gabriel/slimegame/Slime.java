package com.gabriel.slimegame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.core.content.ContextCompat;

public class Slime {

    private static final double SPEED_PIXELS_PER_SECOND = 400.0;
    private static final double MAX_SPEED = SPEED_PIXELS_PER_SECOND / GameLoop.MAX_UPS;
    private double positionX;
    private double positionY;
    private double radius;
    private Paint paint;
    private double velocityX;
    private double velocityY;

    public Slime(Context context, double positionX, double positionY, double radius) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.radius = radius;

        paint = new Paint();
        int color = ContextCompat.getColor(context, R.color.slime);
        paint.setColor(color);

    }

    //
    public void draw(Canvas canvas) {
        canvas.drawCircle((float) positionX, (float)positionY, (float)radius, paint);
    }

    public void update(Joystick joystick) {
        velocityX = joystick.getActuatorX() * MAX_SPEED;
        velocityX = joystick.getActuatorY() * MAX_SPEED;
        positionX += velocityX;
        positionY += velocityY;
    }

    public void jumpSlime(double positionX, double positionY) {
        this.positionX = positionX;
        this.positionY = positionY;

    }
}
