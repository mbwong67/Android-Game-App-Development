package com.example.student.flappyspaceship;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import static com.example.student.flappyspaceship.ObjectType.BULLET;

/**
 * Created by Jonathon Tyson and Cristian Murarescu on 16/02/2018.
 */

public class Bullet extends GameObject
{
    //integer that stores the speed of the ship
    private int speed = 25;
    private int maxX;

    public Bullet(Context context, int startX, int startY, int screenX)
    {
        super(BULLET);

        x = startX;
        y = startY;
        maxX = screenX;

        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.bullet);

        hitBox = new Rect(x, y, bitmap.getWidth(), bitmap.getHeight());
    }

    public void Update()
    {
        x += speed;
    }

    public void Draw()
    {

    }
}
