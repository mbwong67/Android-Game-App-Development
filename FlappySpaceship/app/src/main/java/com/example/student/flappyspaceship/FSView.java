package com.example.student.flappyspaceship;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Debug;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;
import java.util.ArrayList;

import static com.example.student.flappyspaceship.ObjectType.BULLET;
import static com.example.student.flappyspaceship.ObjectType.ENEMY;
import static com.example.student.flappyspaceship.ObjectType.EXPLOSION;
import static com.example.student.flappyspaceship.ObjectType.SPACEDUST;


public class FSView extends SurfaceView implements Runnable
{
    //MediaPlayer objects to configure sounds
    static MediaPlayer gameOnSound;
    final MediaPlayer killedEnemySound;
    final MediaPlayer gameOverSound;
    final MediaPlayer winSound;
    final MediaPlayer bulletSound;

    private boolean debugging = false;

    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;

    private Context context;

    //boolean that checks if the game has ended
    private boolean gameEnded;

    //integers that store the screen boundaries
    private int screenX;
    private int screenY;

    //Game stats
    private float distanceRemaining;
    private long timeTaken;
    private long timeStarted;
    private long fastestTime;

    volatile boolean playing;
    Thread gameThread = null;

    //Game objects
    private PlayerShip player;
    public EnemyShip enemy1;
    public EnemyShip enemy2;
    public EnemyShip enemy3;
    // public EnemyShip enemy4;
    // public EnemyShip enemy5;

    //Drawing objects
    private Paint paint;
    private Canvas canvas;
    private SurfaceHolder ourHolder;


    public FSView(Context context, int x, int y)
    {
        super(context);
        this.context = context;

        //initialising the media players for the game sounds
        gameOnSound = MediaPlayer.create(context,R.raw.gameon);
        killedEnemySound = MediaPlayer.create(context,R.raw.explosion1);
        gameOverSound = MediaPlayer.create(context,R.raw.gameover);
        winSound = MediaPlayer.create(context,R.raw.win);
        bulletSound = MediaPlayer.create(context,R.raw.shoot);

        // Get a reference to a file called HiScores.
        // If id doesn't exist one is created
        prefs = context.getSharedPreferences("HiScores",
                context.MODE_PRIVATE);
        // Initialize the editor ready
        editor = prefs.edit();
        // Load fastest time from a entry in the file
        // labeled "fastestTime"
        // if not available high score = 1000000
        fastestTime = prefs.getLong("fastestTime", 1000000);

        //Initialising screen boundaries
        screenX = x;
        screenY = y;

        //Initialising drawing objects
        ourHolder = getHolder();
        paint = new Paint();

        startGame();

        //Initialising game objects

        /*
        //Initialising the player ship object
        player = new PlayerShip(context, x, y);

        //Initialising the enemy ship objects
        enemy1 = new EnemyShip(context, x, y);
        enemy2 = new EnemyShip(context, x, y);
        enemy3 = new EnemyShip(context, x, y);

        //Initialising the space dust objects
        int numSpecs = 200;

        for (int i = 0; i < numSpecs; i++)
        {
            //Allocates position for space dust to spawn
            SpaceDust spec = new SpaceDust(x,y);
            spaceDustList.add(spec);
        }
        */
    }



    //Clean up the thread if the game is:
    //interrupted or the player quits
    public void Pause()
    {
        playing = false;
        try
        {
            gameThread.join();
        }catch(InterruptedException e)
        {

        }
    }



    //Create new thread and start it
    //Execution moves to R
    public void Resume()
    {
        playing = true;
        gameThread = new Thread(this);
        gameThread.start();
    }



    @Override
    public void run()
    {
        while(playing)
        {
            Update();
            Draw();
            control();
        }
    }



    private void Update()
    {
        ObjectManager.GetInstance().UpdateAll();
        ObjectManager.GetInstance().ProcessCollisions();
        ObjectManager.GetInstance().DeleteInactiveItems();

        //Collision detection on new positions,
        //checking last frames position which has just been drawn

        //Images in excess of 100 pixels wide
        //use the -100 value accordingly


        if (gameEnded)
        {
            gameOnSound.stop();
            gameOverSound.start();
            gameOverSound.stop();
        }
        else
        {
            gameOnSound.start();
        }



        //boolean to register a hit
        boolean hitDetected = false;



        // if(screenX > 1000){
        //     if(Rect.intersects(player.getHitbox(), enemy4.getHitbox())){
        //        hitDetected = true;
        //        enemy4.setX(-300);
        //    }
        // }
        // if(screenX > 1200){
        //    if(Rect.intersects(player.getHitbox(), enemy5.getHitbox())){
        //        hitDetected = true;
        //        enemy5.setX(-300);
        //    }
        // }




        // ObjectManager.GetInstance().ObjectsCollision();

        //Update the space dust particles
        // for (SpaceDust sd : spaceDustList)
        // {
        //    sd.update();
        // }

        if(!gameEnded)
        {

            //subtract distance to home planet based on current speed
            distanceRemaining -= player.getSpeed();

            //How long has the player been flying
            timeTaken = System.currentTimeMillis() - timeStarted;
        }

        /*
        //Completed the game!
        if(distanceRemaining < 0)
        {
            winSound.start();
            winSound.stop();
            //Checks for new fastest time
            if(timeTaken < fastestTime)
            {
                // Save high score
                editor.putLong("fastestTime", timeTaken);
                editor.commit();
                fastestTime = timeTaken;
            }

            //Avoids negative numbers in HUD
            distanceRemaining = 0;

            //Ends the game
            gameEnded = true;
        }
        */

        if(player.getShieldStrength() <= 0)
        {
            gameEnded = true;
        }
    }



    private void Draw()
    {
        if(ourHolder.getSurface().isValid())
        {
            //Locks the area of memory it will be drawn to
            canvas = ourHolder.lockCanvas();

            //Erases last frame
            canvas.drawColor(Color.argb(255,0,0,0));

            //Code for debugging purposes
            paint.setColor(Color.argb(255, 255, 255, 255));

            // Draws the dust from our arrayList
            // for (SpaceDust sd : spaceDustList)
            // {
            //     canvas.drawPoint(sd.getX(), sd.getY(), paint);
            // }

            // Draws all of the hit box in the game for Debugging purposes
            if (debugging)
            {
                for (int i = 0; i < ObjectManager.GetInstance().m_colliderList.size(); i++)
                {
                    canvas.drawRect(
                            ObjectManager.GetInstance().m_colliderList.get(i).getHitbox().left,
                            ObjectManager.GetInstance().m_colliderList.get(i).getHitbox().top,
                            ObjectManager.GetInstance().m_colliderList.get(i).getHitbox().right,
                            ObjectManager.GetInstance().m_colliderList.get(i).getHitbox().bottom,
                            paint);
                }
            }

            // Draws all of the objects in the game
            for (int i = 0; i < ObjectManager.GetInstance().m_allObjectList.size(); i++)
            {
                if (ObjectManager.GetInstance().m_allObjectList.get(i).GetType() != SPACEDUST)
                {
                    canvas.drawBitmap(ObjectManager.GetInstance().m_allObjectList.get(i).getBitmap(),
                            ObjectManager.GetInstance().m_allObjectList.get(i).GetX(),
                            ObjectManager.GetInstance().m_allObjectList.get(i).GetY(),
                            paint);

                }
                else
                {
                    if (ObjectManager.GetInstance().m_allObjectList.get(i).IsActive())
                    {
                        canvas.drawPoint(ObjectManager.GetInstance().m_allObjectList.get(i).GetX(),
                                ObjectManager.GetInstance().m_allObjectList.get(i).GetY(),
                                paint);
                    }
                }
            }


            // if(screenX > 1000)
            // {
            //    canvas.drawBitmap(enemy4.getBitmap(),
            //            enemy4.GetX(), enemy4.GetY(), paint);
            // }if(screenX > 1200)
            // {
            // canvas.drawBitmap(enemy5.getBitmap(),
            //        enemy5.GetX(), enemy5.GetY(), paint);
            //   }

            if(!gameEnded)
            {
                //Draws the HUD
                paint.setTextAlign(Paint.Align.LEFT);
                paint.setColor(Color.argb(255,255,255,255));
                paint.setTextSize(25);
                //canvas.drawText(
                //        "Fastest: " + fastestTime + "s",
                //        10,
                //        20,
                //        paint);
                canvas.drawText("Fastest:" + formatTime(fastestTime) + "s",
                        10,
                        20,
                        paint);
                //canvas.drawText(
                //        "Time: " + timeTaken + "s",
                //        screenX / 2,
                //        20,
                //        paint);
                canvas.drawText("Time:" + formatTime(timeTaken) + "s",
                        screenX / 2,
                        20,
                        paint);
                canvas.drawText(
                        "Distance: " + distanceRemaining / 1000 + " km",
                        screenX / 3,
                        screenY - 20,
                        paint);
                canvas.drawText(
                        "Shield: " + player.getShieldStrength(),
                        10,
                        screenY - 20,
                        paint);
                canvas.drawText(
                        "Speed: " + player.getSpeed() * 60 + " MPS",
                        (screenX / 3) * 2,
                        screenY - 20,
                        paint);
            }
            else
            {
                // Show pause screen
                paint.setTextSize(80);
                paint.setTextAlign(Paint.Align.CENTER);
                canvas.drawText("Game Over",
                        screenX/2,
                        100,
                        paint);
                paint.setTextSize(25);
                //canvas.drawText("Fastest:"+ fastestTime + "s",
                //        screenX/2,
                //        160,
                //        paint);
                canvas.drawText("Fastest:" + formatTime(fastestTime) + "s",
                        10,
                        20,
                        paint);
                //canvas.drawText("Time:" + timeTaken + "s",
                //        screenX / 2,
                //        200,
                //        paint);
                canvas.drawText("Time:" + formatTime(timeTaken) + "s",
                        screenX / 2,
                        20,
                        paint);
                canvas.drawText("Distance remaining:" +
                        distanceRemaining/1000 + " KM",
                        screenX/2,
                        240,
                        paint);
                paint.setTextSize(80);
                canvas.drawText("Tap to replay!",
                        screenX/2,
                        350,
                        paint);
            }

            //Unlocks and draw the scene
            ourHolder.unlockCanvasAndPost(canvas);
        }
    }



    private void startGame()
    {
        //Initialising the gameEnded boolean
        gameEnded = false;

        //starting the game music as the game starts
        gameOnSound.start();

        // Clear the lists on a new game
        ObjectManager.GetInstance().m_colliderList.clear();
        ObjectManager.GetInstance().m_allObjectList.clear();

        //Initialising the player ship object
        player = new PlayerShip(context, screenX, screenY, paint, canvas, ourHolder, bulletSound);
        ObjectManager.GetInstance().AddItem(player, true);

        //Initialising the enemy ship objects
        enemy1 = new EnemyShip(context, screenX, screenY, player, killedEnemySound);
        ObjectManager.GetInstance().AddItem(enemy1, true);
        ObjectManager.GetInstance().AddEnemy(enemy1);

        enemy2 = new EnemyShip(context, screenX, screenY, player, killedEnemySound);
        ObjectManager.GetInstance().AddItem(enemy2, true);
        ObjectManager.GetInstance().AddEnemy(enemy2);

        enemy3 = new EnemyShip(context, screenX, screenY, player, killedEnemySound);
        ObjectManager.GetInstance().AddItem(enemy3, true);
        ObjectManager.GetInstance().AddEnemy(enemy3);

        // if(screenX > 1000)
        // {
        //     enemy4 = new EnemyShip(context, screenX, screenY, player);
        // }
        // if(screenX > 1200)
        // {
        //    enemy5 = new EnemyShip(context, screenX, screenY, player);
        // }

        //Initialising the space dust objects
        for (int i = 0; i < 200; i++)
        {
            //Allocates position for space dust to spawn
            SpaceDust spec = new SpaceDust(screenX, screenY, player);
            ObjectManager.GetInstance().AddItem(spec, false);
        }

        //Resets the time and distance
        distanceRemaining = 1000;  //1 km
        timeTaken = 0;

        //Get start time
        timeStarted = System.currentTimeMillis();
    }



    private void control()
    {
        try
        {
            gameThread.sleep(17);
        }catch(InterruptedException e)
        {

        }
    }



    private String formatTime(long time)
    {
        long seconds = (time) / 1000;
        long thousandths = (time) - (seconds * 1000);
        String strThousandths = "" + thousandths;
        if (thousandths < 100){strThousandths = "0" + thousandths;}
        if (thousandths < 10){strThousandths = "0" + strThousandths;}
        String stringTime = "" + seconds + "." + strThousandths;
        return stringTime;
    }


    /*
    //SurfaceView allows handling of onTouchEvent
    @Override
    public boolean onTouchEvent(MotionEvent motionEvent)
    {
        //Several different events in MotionEvent
        //Using just 2 of them
        switch(motionEvent.getAction() & MotionEvent.ACTION_MASK)
        {
            //Has the player lifted their finger up?
            case MotionEvent.ACTION_UP:
                player.stopBoosting();
                break;

            //Has the player touched the screen?
            case MotionEvent.ACTION_DOWN:
                player.setBoosting();
                //If we currently on the pause screen, start a new game
                if(gameEnded)
                {
                    startGame();
                }
                break;
        }
        return true;
    }
    */

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent)
    {
        switch(motionEvent.getAction())
        {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                player.setY((int)motionEvent.getY());
                break;


        }

        return true;
    }



    public static void stopMusic()
    {
        gameOnSound.stop();
    }
}




