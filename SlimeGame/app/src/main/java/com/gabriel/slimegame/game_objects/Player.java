package com.gabriel.slimegame.game_objects;

import android.content.Context;
import android.graphics.Canvas;

import androidx.core.content.ContextCompat;

import com.gabriel.slimegame.GameLoop;
import com.gabriel.slimegame.game_panels.Joystick;
import com.gabriel.slimegame.R;
import com.gabriel.slimegame.Utils;

public class Player extends Circle {

    public static final int MAX_HEALTH_POINTS = 10;
    protected static final double SPEED_PIXELS_PER_SECOND = 400.0;
    protected static final double MAX_SPEED = SPEED_PIXELS_PER_SECOND / GameLoop.MAX_UPS;
    private Joystick joystick;
    private HealthBar healthBar;
    private int healthPoints;

    public Player(Context context, Joystick joystick, double positionX, double positionY, double radius) {

        super(context, ContextCompat.getColor(context, R.color.slime), positionX, positionY, radius);
        this.joystick = joystick;
        this.healthBar = new HealthBar(context ,this);
        this.healthPoints = MAX_HEALTH_POINTS;
    }


    public void update() {
        // Update velocity based on actuator of joystick
        velocityX = joystick.getActuatorX() * MAX_SPEED;
        velocityY = joystick.getActuatorY() * MAX_SPEED;

        // Update position
        positionX += velocityX;
        positionY += velocityY;

        // Update direction
        if (velocityX != 0 || velocityY != 0) {
            // Normalize velocity to get direction (unit vector of velocity)
            double distance = Utils.getDistanceBetweenObjects(0, 0, velocityX, velocityY);
            directionX = velocityX / distance;
            directionY = velocityY / distance;
        }
    }

    public void draw(Canvas canvas) {
        super.draw(canvas);
        healthBar.draw(canvas);
    }

    public int getHealthPoints() {
        return healthPoints;
    }

    public void setHealthPoints(int healthPoints) {
        // Only allow positive values
        if (healthPoints >= 0){
            this.healthPoints = healthPoints;
        }

    }
}
