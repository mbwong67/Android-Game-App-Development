package com.example.student.flappyspaceship;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.SurfaceHolder;

import static com.example.student.flappyspaceship.ObjectType.PLAYER;

/**
 * PlayerShip :: Used as a blueprint for the player in the game.
 * Created by Jonathon Tyson & Cristian Murarescu on 13/02/2018.
 */

public class PlayerShip extends GameObject
{
    //integer that stores the speed of the player ship
    private int speed = 0;
    //boolean to check if the player ship is boosting or not
    private boolean boosting;
    //integer that stores the gravity of the environment
    private final int GRAVITY = -12;
    //integers that store the boundaries of the player ship speed
    private final int MIN_SPEED = 1;
    private final int MAX_SPEED = 20;
    //integer that stores the strength of the shield of the player ship
    private int shieldStrength;
    private Paint m_paint;
    private Canvas m_canvas;
    private SurfaceHolder m_holder;

    public Context m_context;

    //Constructor for PlayerShip
    public PlayerShip(Context context, int screenX, int screenY, Paint paint, Canvas canvas, SurfaceHolder ourHolder)
    {
        super(PLAYER);

        m_context = context;

        //Assigns the starting x-coordinate of the player ship
        x = 50;
        //Assigns the starting y-coordinate of the player ship
        y = 50;
        //Assigns the starting speed of the player ship
        speed = 1;
        //Assigns the bitmap of the player player ship
        bitmap = BitmapFactory.decodeResource
                (context.getResources(), R.drawable.ship);
        //Assigns the starting value of the "boosting" boolean
        boosting = false;
        //Assigns the top y-axis boundary for the player ship
        maxY = screenY - bitmap.getHeight();
        //Assigns the bottom y-axis boundary for the player ship
        minY = 0;
        //Initialises the collision box of the player ship
        hitBox = new Rect(x, y, bitmap.getWidth(), bitmap.getHeight());
        //Initialises the strength of the shield of the player ship
        shieldStrength = 2;

        //Initialises the paint and canvas
        m_paint = paint;
        m_canvas = canvas;
        m_holder = ourHolder;
    }

    //Updates the player ship
    public void Update()
    {
        //Are we boosting?
        if(boosting)
        {
            //Increases speed
            speed += 2;
        }else
        {
            //Decreases speed
            speed -= 5;
        }

        //Constrains max speed
        if(speed > MAX_SPEED)
        {
            speed = MAX_SPEED;
        }

        //Constrains min speed
        if (speed < MIN_SPEED)
        {
            speed = MIN_SPEED;
        }

        //Moves the ship up or down
        y -= speed + GRAVITY;

        //Keeps the ship within the screen top y-axis boundary
        if (y < minY)
        {
            y = minY;
        }

        //Keeps the ship within the screen bottom y-axis boundary
        if (y > maxY)
        {
            y = maxY;
        }

        //Updates the collision box location for the player ship
        hitBox.left = x;
        hitBox.top = y;
        hitBox.right = x + bitmap.getWidth();
        hitBox.bottom = y + bitmap.getHeight();
    }

    public void Draw()
    {


            //Draws the player
            m_canvas.drawBitmap(
                    this.getBitmap(),
                    this.GetX(),
                    this.GetY(),
                    m_paint);



    }

    public void reduceShieldStrength()
    {
        shieldStrength--;
    }

    //Getters & Setters
    //Gets the speed of the player ship
    public int getSpeed()
    {
        return speed;
    }
    //Gets the strength of the shield of the player ship
    public int getShieldStrength()
    {
        return shieldStrength;
    }
    //Sets the "boosting" boolean to "true"
    public void setBoosting()
    {
        boosting = true;
    }
    //Sets the "boosting" boolean to "false"
    public void stopBoosting()
    {
        boosting = false;
    }
}
