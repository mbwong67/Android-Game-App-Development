package com.example.student.flappyspaceship;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import java.util.Random;



public class EnemyShip
{
    //Bitmap that stores the image for the ship
    private Bitmap bitmap;
    //integers that store the coordinates of the ship
    private int x, y;
    //integer that stores the speed of the ship
    private int speed = 1;
<<<<<<< HEAD
    //integers that store the boundaries of the ship movement (X-axis)
    private int maxX, minX;
    //integers that store the boundaries of the ship movement (Y-axis)
    private int maxY, minY;
    //Rect that stores the collision box of the player ship
    private Rect hitBox;



    //Constructor for EnemyShip
    public EnemyShip(Context context, int screenX, int screenY)
    {
        //Creates an instance of the Random object
        Random generator = new Random();
        int whichBitmap = generator.nextInt(3);
        switch (whichBitmap)
        {
            case 0:
                //Assigns the bitmap of the enemy ship
=======
    // Detect enemies leaving the screen
    private int maxX;
    private int minX;
    // Spawn enemies within screen bounds
    private int maxY;
    private int minY;

    // A hit box for collision detection
    private Rect hitBox;

    // Constructor
    public EnemyShip(Context context, int screenX, int screenY)
    {
        Random generator = new Random();
        int whichBitmap = generator.nextInt(3);
        switch (whichBitmap){
            case 0:
>>>>>>> fe6d1acc4aa6e6c4418ad38b42288985960659e5
                bitmap = BitmapFactory.decodeResource
                        (context.getResources(), R.drawable.enemy3);
                break;
            case 1:
                bitmap = BitmapFactory.decodeResource
                        (context.getResources(), R.drawable.enemy2);
                break;
            case 2:
                bitmap = BitmapFactory.decodeResource
                        (context.getResources(), R.drawable.enemy);
                break;
        }

        scaleBitmap(screenX);

        //Assigns the right x-axis boundary for the enemy ship
        maxX = screenX;
        //Assigns the top y-axis boundary for the enemy ship
        maxY = screenY;
        //Assigns the left x-axis boundary for the enemy ship
        minX = 0;
        //Assigns the bottom y-axis boundary for the enemy ship
        minY = 0;

<<<<<<< HEAD
        //Generates and assigns a random number between 10 and 15
        //to the speed of the enemy ship
        speed = generator.nextInt(6) + 10;
        //Assigns the x-coordinate on the screen for the enemy ship
        //(Spawning it off screen for it to emerge in)
=======
        speed = generator.nextInt(6)+10;
>>>>>>> fe6d1acc4aa6e6c4418ad38b42288985960659e5
        x = screenX;
        //Generates and assigns a random y-coordinate for the enemy ship
        y = generator.nextInt(maxY) - bitmap.getHeight();
<<<<<<< HEAD
        //Initialises the collision box of the player ship
        hitBox = new Rect(x, y, bitmap.getWidth(), bitmap.getHeight());
=======

        // Initialize the hit box
        hitBox = new Rect(x, y, bitmap.getWidth(), bitmap.getHeight());
    }

    //Getters and Setters
    public Bitmap getBitmap()
    {
        return bitmap;
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
>>>>>>> fe6d1acc4aa6e6c4418ad38b42288985960659e5
    }



    //Updates the enemy ship
    public void update(int playerSpeed)
    {
<<<<<<< HEAD
        //Increases speed when the player ship increases speed
=======
        // Move to the left
>>>>>>> fe6d1acc4aa6e6c4418ad38b42288985960659e5
        x -= playerSpeed;
        x -= speed;
        //respawn when off screen

<<<<<<< HEAD
        //Respawns when the enemy ship is offscreen
=======
>>>>>>> fe6d1acc4aa6e6c4418ad38b42288985960659e5
        if(x < minX - bitmap.getWidth())
        {
            //Creates an instance of the Random object
            Random generator = new Random();
<<<<<<< HEAD

            //Generates and assigns a random number between 9 and 19
            //to the speed of the enemy ship
            speed = generator.nextInt(10) + 10;

            //Assigns the x-coordinate on the screen for the enemy ship
=======
            speed = generator.nextInt(10)+10;
>>>>>>> fe6d1acc4aa6e6c4418ad38b42288985960659e5
            x = maxX;

            //Assigns the y-coordinate on the screen for the enemy ship
            y = generator.nextInt(maxY) - bitmap.getHeight();
        }

<<<<<<< HEAD
        //Updates the collision box location for the enemy ship
=======
        // Refresh hit box location
>>>>>>> fe6d1acc4aa6e6c4418ad38b42288985960659e5
        hitBox.left = x;
        hitBox.top = y;
        hitBox.right = x + bitmap.getWidth();
        hitBox.bottom = y + bitmap.getHeight();
<<<<<<< HEAD
    }



    public void scaleBitmap(int x){
        if(x < 1000) {
            bitmap = Bitmap.createScaledBitmap(bitmap,
                    bitmap.getWidth() / 3,
                    bitmap.getHeight() / 3,
                    false);
        }else if(x < 1200){
            bitmap = Bitmap.createScaledBitmap(bitmap,
                    bitmap.getWidth() / 2,
                    bitmap.getHeight() / 2,
                    false);
        }
    }



    //Getters & Setters

    //Gets the bitmap of the enemy ship
    public Bitmap getBitmap()
=======
    }

    public Rect getHitbox()
>>>>>>> fe6d1acc4aa6e6c4418ad38b42288985960659e5
    {
        return hitBox;
    }
<<<<<<< HEAD
    //Gets the x-coordinate of the enemy ship
    public int getX()
=======

    // This is used by the TDView update() method to
    // Make an enemy out of bounds and force a re-spawn
    public void setX(int x)
>>>>>>> fe6d1acc4aa6e6c4418ad38b42288985960659e5
    {
        this.x = x;
    }
<<<<<<< HEAD
    //Gets the y-coordinate of the enemy ship
    public int getY()
    {
        return y;
    }
    //Gets the collision box of the enemy ship
    public Rect getHitbox()
    {
        return hitBox;
    }
    //Sets an enemy ship out of bounds and force a respawn
    //*Used by FSView update() method*
    public void setX(int x)
    {
        this.x = x;
    }
}
=======

    public void scaleBitmap(int x){
        if(x < 1000) {
            bitmap = Bitmap.createScaledBitmap(bitmap,
                    bitmap.getWidth() / 3,
                    bitmap.getHeight() / 3,
                    false);
        }else if(x < 1200){
            bitmap = Bitmap.createScaledBitmap(bitmap,
                    bitmap.getWidth() / 2,
                    bitmap.getHeight() / 2,
                    false);
        }
    }

}
>>>>>>> fe6d1acc4aa6e6c4418ad38b42288985960659e5
