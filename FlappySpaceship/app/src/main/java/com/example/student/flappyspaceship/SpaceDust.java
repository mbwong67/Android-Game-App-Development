package com.example.student.flappyspaceship;

import java.util.Random;



public class SpaceDust
{
    //integers that store the coordinates of the space dust particle
    private int x, y;
    //integer that stores the speed at which the space dust particles move
    private int speed;

    //integers that store the boundaries of the ship movement (X-axis)
    private int maxX;
    private int minX;
    //integers that store the boundaries of the ship movement (Y-axis)
    private int maxY;
    private int minY;

    //Constructor for SpaceDust
    public SpaceDust(int screenX, int screenY)
    {
        //Assigns the right x-axis boundary for the space dust
        maxX = screenX;
        //Assigns the top y-axis boundary for the space dust
        maxY = screenY;
        //Assigns the left x-axis boundary for the space dust
        minX = 0;
        //Assigns the bottom y-axis boundary for the space dust
        minY = 0;

        //Creates an instance of the Random object
        Random generator = new Random();

        //Generates and assigns a random number between 0 and 9
        //to the speed of the space dust
        speed  = generator.nextInt(10);

        //Generates and assigns a random x-coordinate for the space dust
        x = generator.nextInt(maxX);
        //Generates and assigns a random y-coordinate for the space dust
        y = generator.nextInt(maxY);
    }

    public void update(int playerSpeed)
    {
        //Increases speed when the player ship increases speed
        x -= playerSpeed;
        x -= speed;

        //Respawns when the space dust is offscreen
        if(x < 0)
        {
            //Assigns the x-coordinate on the screen for the space dust
            x = maxX;

            //Creates an instance of the Random object
            Random generator = new Random();

            //Generates and assigns the y-coordinate on the screen for the space dust
            y = generator.nextInt(maxY);

            //Generates and assigns a random number between 9 and 19
            //to the speed of the space dust
            speed = generator.nextInt(15);
        }
    }

    //Gets the x-coordinate of the space dust
    public int getX()
    {
        return x;
    }
    //Gets the y-coordinate of the space dust
    public int getY()
    {
        return y;
    }
}
