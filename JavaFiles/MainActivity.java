package com.example.reverie_themeditationapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ImageView Logo;


    private long Backpressedtime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Logo Animation
        Logo = (ImageView) findViewById(R.id.logoimage);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.logo_animation);
        Logo.startAnimation(animation);

        //Walkthrough code
        final Intent i = new Intent(this, WalkThrough.class);

        Thread timer = new Thread(){
            public void run (){
                try {
                    sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally {
                    startActivity(i);
                    finish();
                }
            }
        };
        timer.start();
    }


    //Back press code
    @Override
    public void onBackPressed(){
        if(Backpressedtime + 2000 >System.currentTimeMillis()){
            super.onBackPressed();
            return;
        }
        else{
            Toast.makeText(getBaseContext(), "Press again to exit", Toast.LENGTH_LONG).show();
        }
        Backpressedtime = System.currentTimeMillis();
    }
}
