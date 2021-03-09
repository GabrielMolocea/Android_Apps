package com.gabriel.slimegame.game_objects;

import android.graphics.Canvas;

import com.gabriel.slimegame.GameDisplay;

public abstract class GameObject {

    protected double positionX;
    protected double positionY;
    protected double velocityX;
    protected double velocityY;
    protected double directionX = 1;
    protected double directionY;

    public GameObject(double positionX, double positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
    }

    protected static double getDistanceBetweenObjects(GameObject object1, GameObject object2) {
        return Math.sqrt(
                Math.pow(object2.getPositionX() - object1.getPositionX(), 2) +
                        Math.pow(object2.getPositionY() - object1.getPositionY(), 2)
        );
    }

    public abstract void draw(Canvas canvas, GameDisplay gameDisplay);

    public abstract void update();

    public double getPositionX() {
        return positionX;
    }

    public double getPositionY() {
        return positionY;
    }

    protected double getDirectionX() {
        return directionX;
    }

    protected double getDirectionY() {
        return directionY;
    }
}
