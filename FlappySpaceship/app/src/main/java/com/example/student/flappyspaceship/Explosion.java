package com.example.student.flappyspaceship;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import static com.example.student.flappyspaceship.ObjectType.EXPLOSION;

/**
 * Created by Jonathon Tyson on 26/02/2018.
 */

public class Explosion extends GameObject
{
    private Context m_context;

    // When the timer expires, the bullet will be removed from the game.
    private float m_timer;


    public Explosion(Context context, int startX, int startY)
    {
        super(EXPLOSION);

        m_context = context;
        m_timer = 8.0f;

        bitmap = BitmapFactory.decodeResource(m_context.getResources(), R.drawable.explosion1);
        x = startX;
        y = startY;

        scaleBitmap();
    }


    public void Update()
    {
        m_timer -= 0.5;

        if (m_timer >= 7.0f)
        {
            bitmap = BitmapFactory.decodeResource(m_context.getResources(), R.drawable.explosion2);
        }
        else if (m_timer >= 6.0f && m_timer < 7.0f)
        {
            bitmap = BitmapFactory.decodeResource(m_context.getResources(), R.drawable.explosion3);
        }
        else if (m_timer >= 5.0f && m_timer < 6.0f)
        {
            bitmap = BitmapFactory.decodeResource(m_context.getResources(), R.drawable.explosion4);
        }
        else if (m_timer >= 4.0f && m_timer < 5.0f)
        {
            bitmap = BitmapFactory.decodeResource(m_context.getResources(), R.drawable.explosion5);
        }
        else if (m_timer >= 3.0f && m_timer < 4.0f)
        {
            bitmap = BitmapFactory.decodeResource(m_context.getResources(), R.drawable.explosion6);
        }
        else if (m_timer >= 2.0f && m_timer < 3.0f)
        {
            bitmap = BitmapFactory.decodeResource(m_context.getResources(), R.drawable.explosion7);
        }
        else if (m_timer >= 1.0f && m_timer < 2.0f)
        {
            bitmap = BitmapFactory.decodeResource(m_context.getResources(), R.drawable.explosion8);
        }
        else if (m_timer < 1.0f)
        {
            Deactivate();
        }


        bitmap = Bitmap.createScaledBitmap(bitmap,
                bitmap.getWidth() / 3,
                bitmap.getHeight() / 3,
                false);
    }


    public void ProcessCollision(GameObject other)
    {

    }


    public void scaleBitmap(){

            bitmap = Bitmap.createScaledBitmap(bitmap,
                    bitmap.getWidth() / 3,
                    bitmap.getHeight() / 3,
                    false);
    }
}
