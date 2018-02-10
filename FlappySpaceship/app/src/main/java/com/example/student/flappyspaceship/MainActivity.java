package com.example.student.flappyspaceship;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends Activity
implements View.OnClickListener
{
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

        //Reference to the button in UI layout
        final ImageButton buttonPlay = findViewById(R.id.buttonPlay);

        //Listen for clicks
        buttonPlay.setOnClickListener(this);
    }
}