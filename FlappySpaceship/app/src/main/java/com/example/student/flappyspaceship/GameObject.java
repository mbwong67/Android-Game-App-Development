package com.example.student.flappyspaceship;

import android.graphics.Bitmap;
import android.graphics.Rect;

import static com.example.student.flappyspaceship.ObjectType.ENEMY;

/**
 * GameObject :: The base in which all objects inherit.
 * Created by Jonathon Tyson & Cristian Murarescu on 16/02/2018.
 */

public abstract class GameObject
{
    //Bitmap that stores the image for the ship
    protected Bitmap bitmap;

    //integers that store the coordinates of the ship
    protected int x, y;

    //integers that store the boundaries of the ship movement (X-axis)
    protected int maxX, minX;

    //integers that store the boundaries of the ship movement (Y-axis)
    protected int maxY, minY;

    //Rect that stores the collision box of the player ship
    protected Rect hitBox;

    // Determines the type of the object
    protected ObjectType TYPE;

    protected boolean m_active;

    public void Update()
    {

    }

    public void Draw()
    {

    }


    public GameObject(ObjectType type)
    {
     TYPE = type;
     m_active = true;
    }

    public int GetX()
    {
        return x;
    }

    public int GetY()
    {
        return y;
    }

    public ObjectType GetType()
    {
        return TYPE;
    }

    //Sets an enemy ship out of bounds and force a respawn
    //*Used by FSView update() method*
    public void setX(int x)
    {
        this.x = x;
    }

    //Gets the bitmap of the enemy ship
    public Bitmap getBitmap()
    {
        return bitmap;
    }

    //Gets the collision box of the enemy ship
    public Rect getHitbox()
    {
        return hitBox;
    }

    public boolean IsActive()
    {
        return m_active;
    }

    public void Deactivate()
    {
        m_active = false;
    }

    public void ProcessCollision(GameObject other) {}

    public boolean HasCollided(GameObject other)
    {
        boolean checkThis = false;
        if ((m_active) && (other.m_active) && Rect.intersects(this.getHitbox(), other.getHitbox()))
        {
            checkThis = true;
        }
        return checkThis;
    }

    public double Magnitude()
    {
     return Math.sqrt((x * x) + (y * y));
    }

}
