package com.gabriel.a2dplanegame;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import static com.gabriel.a2dplanegame.GameView.screenRatioX;
import static com.gabriel.a2dplanegame.GameView.screenRatioY;

public class Bullet {

    int x, y, width, height;
    Bitmap bullet;

    public Bullet(Resources resources) {
        bullet = BitmapFactory.decodeResource(resources, R.drawable.bullet);

        width = bullet.getWidth();
        height = bullet.getHeight();

        width /= 4;
        height /= 4;

        width = (int) (width *screenRatioX);
        height = (int) (height *screenRatioY);

        bullet = Bitmap.createScaledBitmap(bullet, width, height,false);

    }

    Rect getCollisionShape() {
        return new Rect(x, y, x + width, y + height);
    }
}
