package com.gabriel.slimegame.objects;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

public abstract class Circle extends GameObject {

    protected double radius;
    protected Paint paint;

    public Circle(Context context, int color, double positionX, double positionY, double radius) {
        super(positionX, positionY);

        this.radius = radius;

        paint = new Paint();
        paint.setColor(color);
    }

    /**
     * isColliding checks if two circles objects are colliding, base on the position and radii
     * @param object1
     * @param object2
     * @return
     */
    public static boolean isColliding(Circle object1, Circle object2) {
        double distance = getDistanceBetweenObjects(object1, object2);
        double distanceToCollision = object1.getRadius() + object2.getRadius();
        if (distance < distanceToCollision)
            return true;
        else
            return false;
    }

    private double getRadius() {
        return radius;
    }

    public void draw(Canvas canvas) {
        canvas.drawCircle((float) positionX, (float)positionY, (float)radius, paint);
    }


}
