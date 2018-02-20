package com.example.student.flappyspaceship;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.KeyEvent;

public class GameActivity extends Activity
{
    //Object to handle the view
    private FSView gameView;



    @Override
    public void onBackPressed()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        FSView.stopMusic();
                        Intent startMain = new Intent(Intent.ACTION_MAIN);
                        startMain.addCategory(Intent.CATEGORY_HOME);
                        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(startMain);
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

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
        gameView.Pause();
    }


    //If the Activity is resumed make sure to resume the thread
    @Override
    protected void onResume()
    {
        super.onResume();
        gameView.Resume();
    }
}
