package com.example.student.flappyspaceship;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.media.MediaPlayer;

import java.util.Random;

import static com.example.student.flappyspaceship.ObjectType.BULLET;
import static com.example.student.flappyspaceship.ObjectType.MISSILE;
import static com.example.student.flappyspaceship.ObjectType.ENEMY;

/**
 * EnemyShip :: Used as a blueprint for the enemies in the game.
 * Created by Jonathon Tyson & Cristian Murarescu on 13/02/2018.
 */

public class EnemyShip extends GameObject
{
    // Has a copy of the players ship for use in logic
    private PlayerShip m_PlayerShip;
    final MediaPlayer m_MediaDestroyed;
    public Context m_context;
    public Explosion explosion;

    public MissileUI m_missileDrop;

    //integer that stores the speed of the ship
    private int speed = 1;

    //Constructor for EnemyShip
    public EnemyShip(Context context, int screenX, int screenY, PlayerShip player, MediaPlayer media)
    {
        // Determines the type of the GameObject
        super(ENEMY);

        m_context = context;
        m_PlayerShip = player;
        m_MediaDestroyed = media;

        //Creates an instance of the Random object
        Random generator = new Random();
        int whichBitmap = generator.nextInt(3);
        switch (whichBitmap)
        {
            case 0:
                //Assigns the bitmap of the enemy ship
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

        //Generates and assigns a random number between 10 and 15
        //to the speed of the enemy ship
        speed = generator.nextInt(6) + 10;
        //Assigns the x-coordinate on the screen for the enemy ship
        //(Spawning it off screen for it to emerge in)
        x = screenX;
        //Generates and assigns a random y-coordinate for the enemy ship
        y = generator.nextInt(maxY) - bitmap.getHeight();
        //Initialises the collision box of the player ship
        hitBox = new Rect(x, y, bitmap.getWidth(), bitmap.getHeight());
    }


    //Updates the enemy ship
    public void Update()
    {
        //Increases speed when the player ship increases speed
        x -= m_PlayerShip.getSpeed();
        x -= speed;

        //Respawns when the enemy ship is offscreen
        if(x < minX - bitmap.getWidth())
        {
            //Creates an instance of the Random object
            Random generator = new Random();

            //Generates and assigns a random number between 9 and 19
            //to the speed of the enemy ship
            speed = generator.nextInt(10) + 10;

            //Assigns the x-coordinate on the screen for the enemy ship
            x = maxX;

            //Assigns the y-coordinate on the screen for the enemy ship
            y = generator.nextInt(maxY) - bitmap.getHeight();
        }

        //Updates the collision box location for the enemy ship
        hitBox.left = x;
        hitBox.top = y;
        hitBox.right = x + bitmap.getWidth();
        hitBox.bottom = y + bitmap.getHeight();
    }

    public void ProcessCollision(GameObject other)
    {
        if (other.GetType() == BULLET || other.GetType() == MISSILE)
        {
            explosion = new Explosion(m_context, x, y);
            ObjectManager.GetInstance().AddItem(explosion, false);
            m_MediaDestroyed.start();

            m_missileDrop = new MissileUI(m_context, x, y);
            ObjectManager.GetInstance().AddItem(m_missileDrop, true);

            setX(-300);
        }
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

}
