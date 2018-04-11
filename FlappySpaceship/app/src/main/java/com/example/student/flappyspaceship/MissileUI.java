package com.example.student.flappyspaceship;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import static com.example.student.flappyspaceship.ObjectType.MISSILEUI;
import static com.example.student.flappyspaceship.ObjectType.PLAYER;

public class MissileUI extends GameObject
{
    //integer that stores the speed of the ship
    private int speed = 10;

    // When the timer expires, the bullet will be removed from the game.
    private float m_timer;

    public MissileUI(Context context, int startX, int startY)
    {
        super(MISSILEUI);

        x = startX;
        y = startY;

        m_timer = 120.0f;

        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.missleui);

        hitBox = new Rect(x, y, bitmap.getWidth(), bitmap.getHeight());
    }

    public void Update()
    {
        x -= speed;

        if (m_timer <= 0.0f)
        {
            Deactivate();
        }

        //Updates the collision box location for the enemy ship
        hitBox.left = x;
        hitBox.top = y;
        hitBox.right = x + bitmap.getWidth();
        hitBox.bottom = y + bitmap.getHeight();
    }

    public void Draw() {}

    public void ProcessCollision(GameObject other)
    {
        if (other.GetType() == PLAYER)
        {
            Deactivate();
        }
    }
}
