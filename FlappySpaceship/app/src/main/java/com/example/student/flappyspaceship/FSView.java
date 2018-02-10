package com.example.student.flappyspaceship;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;

/**
 * Created by student on 08/02/2018.
 */

public class FSView extends SurfaceView implements Runnable
{
    volatile boolean playing;
    Thread gameThread = null;

    // Game Object
    private PlayerShip player;
    public EnemyShip enemy1;
    public EnemyShip enemy2;
    public EnemyShip enemy3;
    public ArrayList<SpaceDust> dustList = new ArrayList<SpaceDust>();

    // For drawing
    private Paint paint;
    private Canvas canvas;
    private SurfaceHolder ourHolder;

    public FSView(Context context, int x, int y)

    {
        super(context);

        // Initialise
        ourHolder = getHolder();
        paint = new Paint();
        player = new PlayerShip(context, x, y);
        enemy1 = new EnemyShip(context, x, y);
        enemy2 = new EnemyShip(context, x, y);
        enemy3 = new EnemyShip(context, x, y);

        int numSpecs = 40;
        for (int i = 0; i < numSpecs; i++)
        {
            SpaceDust spec = new SpaceDust(x, y);
            dustList.add(spec);
        }
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
      player.update();
        enemy1.update(player.getSpeed());
        enemy2.update(player.getSpeed());
        enemy3.update(player.getSpeed());

        for (SpaceDust sd : dustList)
        {
            sd.update(player.getSpeed());
        }
    }

    private void draw()
    {
        if (ourHolder.getSurface().isValid())
        {
         //First we lock the area of memory we will be drawing to
            canvas = ourHolder.lockCanvas();
         // Rub out the last frame
            canvas.drawColor(Color.argb(255, 0, 0, 0));
         // Draw the player
            canvas.drawBitmap(
                    player.getBitmap(),
                    player.getX(),
                    player.getY(),
                    paint);

            // draw enemies
            canvas.drawBitmap(
                    enemy1.getBitmap(),
                    enemy1.getX(),
                    enemy1.getX(),
                    paint);

            canvas.drawBitmap(
                    enemy2.getBitmap(),
                    enemy2.getX(),
                    enemy2.getX(),
                    paint);

            canvas.drawBitmap(
                    enemy3.getBitmap(),
                    enemy3.getX(),
                    enemy3.getX(),
                    paint);

            paint.setColor(Color.argb(255,255,255,255));

            for (SpaceDust sd : dustList)
            {
                canvas.drawPoint(sd.getX(), sd.getY(), paint);
            }


         // Unlock and draw the scene
            ourHolder.unlockCanvasAndPost(canvas);
        }

    }

    private void control()
    {
        try {
            gameThread.sleep(17);
        } catch (InterruptedException e) {
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent)
    {
        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK)
        {
            case MotionEvent.ACTION_UP:
            player.stopBoosting();
                break;

            case MotionEvent.ACTION_DOWN:
            player.setBoosting();
                break;
        }
        return true;
    }
}




