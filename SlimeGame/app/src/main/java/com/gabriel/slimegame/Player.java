package com.gabriel.slimegame;

import android.content.Context;

import androidx.core.content.ContextCompat;

public class Player extends Circle {

    private static final double SPEED_PIXELS_PER_SECOND = 400.0;
    private static final double MAX_SPEED = SPEED_PIXELS_PER_SECOND / GameLoop.MAX_UPS;
    private Joystick joystick;

    public Player(Context context, Joystick joystick, double positionX, double positionY, double radius) {

        super(context, ContextCompat.getColor(context, R.color.slime), positionX, positionY, radius);
        this.joystick = joystick;
    }



    public void update() {
        // Update velocity based on actuator of joystick
        velocityX = joystick.getActuatorX()*MAX_SPEED;
        velocityY = joystick.getActuatorY()*MAX_SPEED;

        // Update position
        positionX += velocityX;
        positionY += velocityY;
    }

//    public void jumpSlime(double positionX, double positionY) {
//        this.positionX = positionX;
//        this.positionY = positionY;
//    }
}
