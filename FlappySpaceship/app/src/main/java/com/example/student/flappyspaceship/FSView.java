package com.example.student.flappyspaceship;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;
import java.util.ArrayList;



public class FSView extends SurfaceView implements Runnable
{
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;

    private Context context;

    //objects and integers to store the sound assets
    private SoundPool soundPool;
    private int start = -1;
    private int bump = -1;
    private int win = -1;

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
    public EnemyShip enemy4;
    public EnemyShip enemy5;

    //Drawing objects
    private Paint paint;
    private Canvas canvas;
    private SurfaceHolder ourHolder;

    //SpaceDust objects
    public ArrayList<SpaceDust> spaceDustList =
            new ArrayList<SpaceDust>();



    public FSView(Context context, int x, int y)
    {
        super(context);
        this.context = context;

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

        //This SoundPool is deprecated but don't worry
        soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);

        try
        {
//Create objects of the 2 required classes
            AssetManager assetManager = context.getAssets();
            AssetFileDescriptor descriptor;
//create our three fx in memory ready for use
            descriptor = assetManager.openFd("start.ogg");
            start = soundPool.load(descriptor, 0);
            descriptor = assetManager.openFd("win.ogg");
            win = soundPool.load(descriptor, 0);
            descriptor = assetManager.openFd("bump.ogg");
            bump = soundPool.load(descriptor, 0);
            //descriptor = assetManager.openFd("explosion.ogg");
            //explosion = soundPool.load(descriptor, 0);
        }catch(IOException e)
        {
//Print an error message to the console
            Log.e("error", "failed to load sound files");
        }

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
    public void pause()
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
    public void resume()
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
            update();
            draw();
            control();
        }
    }



    private void update()
    {
        //Collision detection on new positions,
        //checking last frames position which has just been drawn

        //Images in excess of 100 pixels wide
        //use the -100 value accordingly

        //boolean to register a hit
        boolean hitDetected = false;

        if(Rect.intersects
                (player.getHitbox(), enemy1.getHitbox()))
        {
            hitDetected = true;
            enemy1.setX(-300);
        }
        if(Rect.intersects
                (player.getHitbox(), enemy2.getHitbox()))
        {
            hitDetected = true;
            enemy2.setX(-300);
        }
        if(Rect.intersects
                (player.getHitbox(), enemy3.getHitbox()))
        {
            hitDetected = true;
            enemy3.setX(-300);
        }

        if(screenX > 1000){
            if(Rect.intersects(player.getHitbox(), enemy4.getHitbox())){
                hitDetected = true;
                enemy4.setX(-300);
            }
        }
        if(screenX > 1200){
            if(Rect.intersects(player.getHitbox(), enemy5.getHitbox())){
                hitDetected = true;
                enemy5.setX(-300);
            }
        }

        if(hitDetected)
        {
            soundPool.play(bump, 1, 1, 0, 0, 1);
            player.reduceShieldStrength();
            if(player.getShieldStrength() < 0)
            {
                //soundPool.play(destroyed, 1, 1, 0, 0, 1);
                gameEnded = true;
            }
        }

        //Update the player
        player.update();

        //Update the enemies
        enemy1.update(player.getSpeed());
        enemy2.update(player.getSpeed());
        enemy3.update(player.getSpeed());

        if(screenX > 1000)
        {
            enemy4.update(player.getSpeed());
        }
        if(screenX > 1200)
        {
        enemy5.update(player.getSpeed());
    }

        //Update the space dust particles
        for (SpaceDust sd : spaceDustList)
        {
            sd.update(player.getSpeed());
        }

        if(!gameEnded)
        {
            //subtract distance to home planet based on current speed
            distanceRemaining -= player.getSpeed();

            //How long has the player been flying
            timeTaken = System.currentTimeMillis() - timeStarted;
        }

        //Completed the game!
        if(distanceRemaining < 0)
        {
            soundPool.play(win, 1, 1, 0, 0, 1);
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
    }



    private void draw()
    {
        if(ourHolder.getSurface().isValid())
        {
            //Locks the area of memory it will be drawn to
            canvas = ourHolder.lockCanvas();

            //Erases last frame
            canvas.drawColor(Color.argb(255,0,0,0));


            //Code for debugging purposes

            //Switch to white pixels
            paint.setColor(Color.argb(255,255,255,255));

            //Draw collision boxes
            canvas.drawRect(
                    player.getHitbox().left,
                    player.getHitbox().top,
                    player.getHitbox().right,
                    player.getHitbox().bottom,
                    paint);
            canvas.drawRect(
                    enemy1.getHitbox().left,
                    enemy1.getHitbox().top,
                    enemy1.getHitbox().right,
                    enemy1.getHitbox().bottom,
                    paint);
            canvas.drawRect(
                    enemy2.getHitbox().left,
                    enemy2.getHitbox().top,
                    enemy2.getHitbox().right,
                    enemy2.getHitbox().bottom,
                    paint);
            canvas.drawRect(
                    enemy3.getHitbox().left,
                    enemy3.getHitbox().top,
                    enemy3.getHitbox().right,
                    enemy3.getHitbox().bottom,
                    paint);


            //Sets the colour of the space dust particles to white
            paint.setColor(Color.argb(255, 255, 255, 255));

            //Draws the dust from our arrayList
            for (SpaceDust sd : spaceDustList)
            {
                canvas.drawPoint(sd.getX(), sd.getY(), paint);
            }

            //Draws the player
            canvas.drawBitmap(
                    player.getBitmap(),
                    player.getX(),
                    player.getY(),
                    paint);

            //Draws the enemies
            canvas.drawBitmap(
                    enemy1.getBitmap(),
                    enemy1.getX(),
                    enemy1.getY(),
                    paint);
            canvas.drawBitmap(
                    enemy2.getBitmap(),
                    enemy2.getX(),
                    enemy2.getY(),
                    paint);
            canvas.drawBitmap(
                    enemy3.getBitmap(),
                    enemy3.getX(),
                    enemy3.getY(),
                    paint);

            if(screenX > 1000)
            {
                canvas.drawBitmap(enemy4.getBitmap(),
                        enemy4.getX(), enemy4.getY(), paint);
            }if(screenX > 1200)
            {
            canvas.drawBitmap(enemy5.getBitmap(),
                    enemy5.getX(), enemy5.getY(), paint);
        }


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

        //Initialising the player ship object
        player = new PlayerShip(context, screenX, screenY);

        //Initialising the enemy ship objects
        enemy1 = new EnemyShip(context, screenX, screenY);
        enemy2 = new EnemyShip(context, screenX, screenY);
        enemy3 = new EnemyShip(context, screenX, screenY);

        if(screenX > 1000)
        {
            enemy4 = new EnemyShip(context, screenX, screenY);
        }
        if(screenX > 1200)
        {
            enemy5 = new EnemyShip(context, screenX, screenY);
        }

        //Initialising the space dust objects
        int numSpecs = 200;

        for (int i = 0; i < numSpecs; i++)
        {
            //Allocates position for space dust to spawn
            SpaceDust spec = new SpaceDust(screenX,screenY);
            spaceDustList.add(spec);
        }

        //Resets the time and distance
        distanceRemaining = 10000;  //10 km
        timeTaken = 0;

        //Get start time
        timeStarted = System.currentTimeMillis();

        //Plays the sound pool
        soundPool.play(start, 1, 1,0,0,1);
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
}




