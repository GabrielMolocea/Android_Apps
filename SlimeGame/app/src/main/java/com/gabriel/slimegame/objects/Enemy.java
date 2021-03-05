package com.gabriel.slimegame.objects;

import android.content.Context;

import androidx.core.content.ContextCompat;

import com.gabriel.slimegame.GameLoop;
import com.gabriel.slimegame.R;

public class Enemy extends Circle{

    private static final double SPEED_PIXELS_PER_SECOND = Player.SPEED_PIXELS_PER_SECOND * 0.6;
    protected static final double MAX_SPEED = SPEED_PIXELS_PER_SECOND / GameLoop.MAX_UPS;
    private static final int SPAWN_PER_MINUTE = 20;
    private static final double SPAWN_PER_SECOND = SPAWN_PER_MINUTE / 60;
    private static final double UPDATES_PER_SPAWN = GameLoop.MAX_UPS / SPAWN_PER_SECOND;
    private static double updatesUntilNextSpawn = UPDATES_PER_SPAWN;
    private final Player player;

    public Enemy(Context context, Player player, double positionX, double positionY, double radius) {
        super(context, ContextCompat.getColor(context, R.color.enemy), positionX, positionY, radius);
        this.player = player;
    }

    public Enemy(Context context, Player player) {
        super(
            context,
            ContextCompat.getColor(context, R.color.enemy),
            Math.random() * 1000,
            Math.random() * 1000,
            30
        );
        this.player = player;

    }

    /**
     * readyToSpawn checks if a new enemy is ready to be spawn, according the decided number of
     * spawns per minute ( see @SPAWN_PER_MINUTE at top)
     * @return
     */
    public static boolean isReadyToSpawn() {
        if (updatesUntilNextSpawn >= 0) {
            updatesUntilNextSpawn += UPDATES_PER_SPAWN;
            return true;
        } else {
            updatesUntilNextSpawn --;
            return false;
        }

    }

    @Override
    public void update() {
        // Update velocity of the enemy so that the velocity is in direction of player

        // Calculate vector for enemy to player
        double distanceToPlayerX = player.getPositionX() - positionX;
        double distanceToPlayerY = player.getPositionY() - positionY;

        // Calculate distance between enemy and player
        double distanceToPlayer = GameObject.getDistanceBetweenObjects(this, player);

        // Calculate direction from enemy to player
        double directionX = distanceToPlayerX / distanceToPlayer;
        double directionY = distanceToPlayerY / distanceToPlayer;

        // Set velocity in the direction to player
        if (distanceToPlayer > 0 ) { // Avoiding division by 0
            velocityX = directionX * MAX_SPEED;
            velocityY = directionY * MAX_SPEED;
        } else {
            velocityX = 0;
            velocityY = 0;
        }

        // Update position of enemy
        positionX += velocityX;
        positionY += velocityY;

    }
}
