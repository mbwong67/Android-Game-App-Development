package com.example.student.flappyspaceship;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.KeyEvent;

public class GameActivity extends Activity
{
    //Object to handle the view
    private FSView gameView;



    // If the player hits the back button, quit the app
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            return true;
        }
        return false;
    }



    //"Play" button from HomeActivity sends the program
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        //Get a Display object to access screen details
        Display display = getWindowManager().getDefaultDisplay();

        //Create an instance of Point object
        //*Point can store two coordinates*
        //Load the resolution into a Point object
        Point size = new Point();
        display.getSize(size);

        //Create an instance of Flappy Spaceship View (FSView)
        //by passing in "this"
        //by passing in the screen resolution to the constructor
        gameView = new FSView(this, size.x, size.y);

        //Make the gameView the view for the Activity
        setContentView(gameView);

    }


    //If the Activity is paused make sure to pause the thread
    @Override
    protected void onPause()
    {
        super.onPause();
        gameView.pause();
    }


    //If the Activity is resumed make sure to resume the thread
    @Override
    protected void onResume()
    {
        super.onResume();
        gameView.resume();
    }

    // If the player hits the back button, quit the app
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            return true;
        }
        return false;
    }
}
