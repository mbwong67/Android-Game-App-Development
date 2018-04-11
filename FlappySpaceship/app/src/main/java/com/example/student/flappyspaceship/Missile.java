package com.example.student.flappyspaceship;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import static com.example.student.flappyspaceship.ObjectType.ENEMY;
import static com.example.student.flappyspaceship.ObjectType.MISSILE;

/**
 * Created by Jonathon Tyson on 11/04/2018.
 */

public class Missile extends GameObject
{
    //integer that stores the speed of the ship
    private int speed = 30;

    // When the timer expires, the bullet will be removed from the game.
    private float m_timer;

    private EnemyShip m_enemy;

    int maxMag = 2000;

    public Missile(Context context, int startX, int startY)
    {
        super(MISSILE);

        x = startX;
        y = startY;

        m_timer = 120.0f;

        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.missile);

        hitBox = new Rect(x, y, bitmap.getWidth(), bitmap.getHeight());
    }

    public void Update()
    {
        for (int i = 0; i < ObjectManager.GetInstance().m_enemyList.size(); i++)
        {
            int magnitudeX = (ObjectManager.GetInstance().m_enemyList.get(i).x - x);
            int magnitudeY = (ObjectManager.GetInstance().m_enemyList.get(i).y - y);
            int magnitude = (int)Math.sqrt(magnitudeX * magnitudeX + magnitudeY * magnitudeY);

            if (magnitude < maxMag)
            {
                maxMag = magnitude;
                m_enemy = (EnemyShip)ObjectManager.GetInstance().m_enemyList.get(i);
            }
        }

        m_timer -= 1.0f;

        x += speed;

        if (y < m_enemy.y)
        {
            y += 20.0f;
        }
        else if (y > m_enemy.y)
        {
            y -= 20.0f;
        }

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