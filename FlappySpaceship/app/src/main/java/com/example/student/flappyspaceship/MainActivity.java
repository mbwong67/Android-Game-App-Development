package com.example.student.flappyspaceship;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity
implements View.OnClickListener
{
    // If the player hits the back button, quit the app
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            return true;
        }
        return false;
    }



    @Override
    public void onClick(View view)
    {
        //Must be the "Play" button
        //Create new Intent object
        Intent i = new Intent(this, GameActivity.class);
        //Start GameActivity class via the Intent
        startActivity(i);
        //Shut activity down
        finish();
    }

    //Entry point in the game
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        //Set UI layout as the view
        setContentView(R.layout.activity_main);

        // Prepare to load fastest time
        SharedPreferences prefs;
        SharedPreferences.Editor editor;
        prefs = getSharedPreferences("HiScores", MODE_PRIVATE);

        //Reference to the button in UI layout
        final Button buttonPlay = (Button)findViewById(R.id.buttonPlay);

        // Get a reference to the TextView in our layout
        final TextView textFastestTime =
                (TextView)findViewById(R.id.textHighScore);

        //Listen for clicks
        buttonPlay.setOnClickListener(this);

        // Load fastest time
        // if not available our high score = 1000000
        long fastestTime = prefs.getLong("fastestTime", 1000000);
        // Put the high score in our TextView
        textFastestTime.setText("Fastest Time:" + fastestTime);
    }
}