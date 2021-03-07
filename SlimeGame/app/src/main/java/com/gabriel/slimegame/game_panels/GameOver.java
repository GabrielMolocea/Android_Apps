package com.gabriel.slimegame.game_panels;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.core.content.ContextCompat;

import com.gabriel.slimegame.R;

/**
 * GameOver is a panel which draws the text Game Over to the screen
 */
public class GameOver {

    private Context context;

    public GameOver(Context context) {
        this.context = context;
    }

    public void draw(Canvas canvas) {

        String text = "Game Over";

        float x = 800, y = 200, textSize = 150;
        int color = ContextCompat.getColor(context, R.color.gameOver);

        Paint paint = new Paint();
        paint.setColor(color);
        paint.setTextSize(textSize);

        canvas.drawText(text, x, y, paint);
    }
}
