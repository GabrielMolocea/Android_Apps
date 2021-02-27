package com.gabriel.slimegame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.core.content.ContextCompat;

public class Slime {

    private double positionX;
    private double positionY;
    private double radius;
    private Paint paint;

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

    public void update() {
    }

    public void jumpSlime(double positionX, double positionY) {
        this.positionX = positionX + 100;
        this.positionY = positionY + 100;

    }
}
