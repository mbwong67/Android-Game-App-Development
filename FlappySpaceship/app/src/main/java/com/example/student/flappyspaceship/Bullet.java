package com.example.student.flappyspaceship;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import static com.example.student.flappyspaceship.ObjectType.BULLET;
import static com.example.student.flappyspaceship.ObjectType.ENEMY;

/**
 * Created by Jonathon Tyson on 16/02/2018.
 */

public class Bullet extends GameObject
{
    //integer that stores the speed of the ship
    private int speed = 25;

    // When the timer expires, the bullet will be removed from the game.
    private float m_timer;

    public Bullet(Context context, int startX, int startY)
    {
        super(BULLET);

        x = startX;
        y = startY;

        m_timer = 60.0f;

        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.bullet);

        hitBox = new Rect(x, y, bitmap.getWidth(), bitmap.getHeight());
    }

    public void Update()
    {
        m_timer -= 1.0f;

        x += speed;

        if (m_timer <= 0.0f)
        {
            Deactivate();
        }

        //Updates the collision box location for the player ship
        hitBox.left = x;
        hitBox.top = y;
        hitBox.right = x + bitmap.getWidth();
        hitBox.bottom = y + bitmap.getHeight();
    }

    public void Draw() {}

    public void ProcessCollision(GameObject other)
    {
        if (other.GetType() == ENEMY)
        {
            Deactivate();
        }
    }
}
