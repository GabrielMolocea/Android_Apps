package com.gabriel.slimegame;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Joystick {

    private Paint innerCirclePaint;
    private Paint outerCirclePaint;
    private int outerCircleRadius;
    private int innerCircleRadius;
    private int outerCircleCenterPositionX;
    private int outerCircleCenterPositionY;
    private int innerCircleCenterPositionX;
    private int innerCircleCenterPositionY;

    public Joystick(int centerPositionX, int centerPositionY, int outerCircleRadius, int innerCircleRadius) {

        // Outer and inner circle for joystick
        outerCircleCenterPositionX = centerPositionX;
        outerCircleCenterPositionY = centerPositionY;
        innerCircleCenterPositionX = centerPositionX;
        innerCircleCenterPositionY = centerPositionY;

        // Radius of circles
        this.outerCircleRadius = outerCircleRadius;
        this.innerCircleRadius = innerCircleRadius;

        // Paint of circles
        outerCirclePaint = new Paint();
        outerCirclePaint.setColor(Color.GRAY);
        outerCirclePaint.setStyle(Paint.Style.FILL_AND_STROKE);

        innerCirclePaint = new Paint();
        innerCirclePaint.setColor(Color.RED);
        innerCirclePaint.setStyle(Paint.Style.FILL_AND_STROKE);

    }

    public void draw(Canvas canvas) {
        canvas.drawCircle(
                outerCircleCenterPositionX, outerCircleCenterPositionY, outerCircleRadius, outerCirclePaint
        );
        canvas.drawCircle(
                innerCircleCenterPositionX, innerCircleCenterPositionY, innerCircleRadius, innerCirclePaint
        );
    }

    public void update() {
    }
}
