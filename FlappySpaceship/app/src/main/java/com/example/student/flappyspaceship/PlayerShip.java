package com.example.student.flappyspaceship;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
<<<<<<< HEAD

=======
>>>>>>> fe6d1acc4aa6e6c4418ad38b42288985960659e5


public class PlayerShip
{
    //Bitmap that stores the image for the player ship
    private Bitmap bitmap;
    //integers that store the coordinates of the player ship
    private int x, y;
    //integer that stores the speed of the player ship
    private int speed = 0;
    //boolean to check if the player ship is boosting or not
    private boolean boosting;
    //integer that stores the gravity of the environment
    private final int GRAVITY = -12;
    //integers that store the boundaries of the player ship movement
    private int maxY, minY;
    //integers that store the boundaries of the player ship speed
    private final int MIN_SPEED = 1;
    private final int MAX_SPEED = 20;
    //Rect that stores the collision box of the player ship
    private Rect hitBox;
    //integer that stores the strength of the shield of the player ship
    private int shieldStrength;



<<<<<<< HEAD
    //Constructor for PlayerShip
=======
    // A hit box for collision detection
    private Rect hitBox;

    private int shieldStrength;


>>>>>>> fe6d1acc4aa6e6c4418ad38b42288985960659e5
    public PlayerShip(Context context, int screenX, int screenY)
    {
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
<<<<<<< HEAD
        //Initialises the collision box of the player ship
        hitBox = new Rect(x, y, bitmap.getWidth(), bitmap.getHeight());
        //Initialises the strength of the shield of the player ship
=======

        // Initialize the hit box
        hitBox = new Rect(x, y, bitmap.getWidth(), bitmap.getHeight());

>>>>>>> fe6d1acc4aa6e6c4418ad38b42288985960659e5
        shieldStrength = 2;
    }



    //Updates the player ship
    public void update()
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

<<<<<<< HEAD
        //Updates the collision box location for the player ship
=======
        // Refresh hit box location
>>>>>>> fe6d1acc4aa6e6c4418ad38b42288985960659e5
        hitBox.left = x;
        hitBox.top = y;
        hitBox.right = x + bitmap.getWidth();
        hitBox.bottom = y + bitmap.getHeight();
<<<<<<< HEAD
    }



    public void reduceShieldStrength()
    {
        shieldStrength--;
=======
>>>>>>> fe6d1acc4aa6e6c4418ad38b42288985960659e5
    }



    //Getters & Setters

    //Gets the bitmap of the player ship
    public Bitmap getBitmap()
    {
        return bitmap;
    }
    //Gets the speed of the player ship
    public int getSpeed()
    {
        return speed;
    }
    //Gets the x-coordinate of the player ship
    public int getX()
    {
        return x;
    }
    //Gets the y-coordinate of the player ship
    public int getY()
    {
        return y;
    }
<<<<<<< HEAD
    //Gets the collision box of the player ship
=======

    public void setBoosting() { boosting = true; }

    public void stopBoosting() { boosting = false; }

>>>>>>> fe6d1acc4aa6e6c4418ad38b42288985960659e5
    public Rect getHitbox()
    {
        return hitBox;
    }
<<<<<<< HEAD
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
=======

    public int getShieldStrength() {
        return shieldStrength;
    }

    public void reduceShieldStrength(){
        shieldStrength --;
>>>>>>> fe6d1acc4aa6e6c4418ad38b42288985960659e5
    }
}
