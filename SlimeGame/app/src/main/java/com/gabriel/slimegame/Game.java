package com.gabriel.slimegame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.gabriel.slimegame.objects.Circle;
import com.gabriel.slimegame.objects.Enemy;
import com.gabriel.slimegame.objects.Player;
import com.gabriel.slimegame.objects.Spell;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * Game manages all objects in the game and is responsible for updating and rendering
 * of all objects to screen
 */

public class Game extends SurfaceView implements SurfaceHolder.Callback {
    private final Player player;
    private final Joystick joystick;
    private GameLoop gameLoop;
    private List<Enemy> enemyList = new ArrayList<>();
    private List<Spell> spellList = new ArrayList<Spell>();
    private int joystickPointedId = 0;
    private int numberOfSpellsToCast = 0;

    public Game(Context context) {
        super(context);

        // Initialization of constructor
        SurfaceHolder surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);

        gameLoop = new GameLoop(this, surfaceHolder);

        // Initialize game objects
        joystick = new Joystick(275, 700, 70, 40);
        player = new Player(getContext(), joystick , 500, 500, 30);

        setFocusable(true);
    }

    // Handle on touch actions on user
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                if (joystick.getIsPressed()){
                    // Joystick was press before the event ->  cast spell
                    numberOfSpellsToCast++;
                } else if (joystick.isPressed((double) event.getX(), (double) event.getY())) {
                    // Joystick is pressed in this event  -> setIsPressed(true) and store ID
                    joystickPointedId = event.getPointerId(event.getActionIndex());
                    joystick.setIsPressed(true);
                } else {
                    //Joystick was not pressed, and is not pressed in this event -> cast spell
                    spellList.add(new Spell(getContext(),player));
                }
                return true;
            case MotionEvent.ACTION_MOVE:
                // Joystick was pressed previously and is now moved
                if (joystick.getIsPressed()) {
                    joystick.setActuator((double) event.getX(), (double) event.getY());
                }
                return true;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                if (joystickPointedId == event.getPointerId(event.getActionIndex())) {
                    // Joystick was let go of -> setIsPressed(false) and resetActuator
                    joystick.setIsPressed(false);
                    joystick.resetActuator();
                }
                return true;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        gameLoop.startLoop();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {

    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        drawUPS(canvas);
        drawFPS(canvas);

        joystick.draw(canvas);
        player.draw(canvas);

        for (Enemy enemy : enemyList) {
            enemy.draw(canvas);
        }

        for (Spell spell : spellList) {
            spell.draw(canvas);
        }
    }


    //  Methods for Updates per second and
    // Frames per second
    public void drawUPS(Canvas canvas) {
        String averageUPS = Double.toString(gameLoop.getAverageUPS());
        Paint paint = new Paint();
        int color = ContextCompat.getColor(getContext(), R.color.magenta);
        paint.setColor(color);
        paint.setTextSize(50);
        canvas.drawText("UPS: " + averageUPS, 0, 40, paint);
    }

    public void drawFPS(Canvas canvas) {
        String averageFPS = Double.toString(gameLoop.getAverageFPS());
        Paint paint = new Paint();
        int color = ContextCompat.getColor(getContext(), R.color.magenta);
        paint.setColor(color);
        paint.setTextSize(50);
        canvas.drawText("FPS: " + averageFPS, 0, 80, paint);
    }

    public void update() {
        joystick.update();
        player.update();

        // Spawn enemy if is time to spawn
        if (Enemy.readyToSpawn()) {
            enemyList.add(new Enemy(getContext(), player));
        }

        //  Update the state of each enemy

        while (numberOfSpellsToCast > 0 ) {
            spellList.add(new Spell(getContext(),player));
            numberOfSpellsToCast--;
        }
        for (Enemy enemy : enemyList) {
            enemy.update();
        }

        //  Update the state of each spell
        for (Spell spell : spellList) {
            spell.update();
        }

         //Iterate through @enemyList and check for collision between enemy and player
        // and all spells
        Iterator<Enemy> iteratorEnemy = enemyList.iterator();
        while (iteratorEnemy.hasNext()) {
            Circle enemy = iteratorEnemy.next();
            if (Circle.isColliding(enemy, player)){
                // Remove enemy if it collided with the player
                iteratorEnemy.remove();
                continue;
            }

            Iterator<Spell> iteratorSpell = spellList.iterator();
            while (iteratorSpell.hasNext()) {
                Circle spell = iteratorSpell.next();
                // Remove sell if is collided with the enemy
                if (Circle.isColliding(spell, enemy)) {
                    iteratorSpell.remove();
                    iteratorEnemy.remove();
                    break;
                }
            }
        }
    }


}
