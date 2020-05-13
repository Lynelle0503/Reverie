package com.example.reverie_themeditationapplication;

import android.graphics.drawable.AnimationDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AudioPage extends AppCompatActivity {
    RelativeLayout Rlayout;
    AnimationDrawable animationDrawable;


    private Runnable runnable;
    private Handler handler;
    private long Backpressedtime;

    MediaPlayer mediaPlayer;
    ArrayAdapter<String> listadapter;
    int pause;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_page);

        //initializing variables
        final Button Play = findViewById(R.id.playbtn);
        final Button Pause = findViewById(R.id.pausebtn);
        final Button Stop = findViewById(R.id.stopbtn);
        final TextView title = findViewById(R.id.songtitle);

        SearchView Search = (SearchView) findViewById(R.id.search);


        handler = new Handler();

        //animation for the background color change
        Rlayout = (RelativeLayout) findViewById(R.id.audiobg);
        animationDrawable = (AnimationDrawable) Rlayout.getBackground();
        animationDrawable.setEnterFadeDuration(4500);
        animationDrawable.setExitFadeDuration(4500);
        animationDrawable.start();


        ListView AudioList = findViewById(R.id.music);
        final String[] songList = {"Body Meditation","Mindful Yoga","Letting Go","Mountain Meditation","Mindfulness Meditation","Body Scan","Sleep Booster","Meditation","Switch off- Sleep"};
        final int[] resID = {R.raw.body_meditation,R.raw.gentle_mindful_yoga,R.raw.letting_go,R.raw.mindfulness_mountain_meditation,R.raw.mindfulness_practice,R.raw.minute_body_scan, R.raw.off_with_sleep,R.raw.sitting_meditation, R.raw.switching_off_with_sleep};
        final ArrayAdapter<String> listadapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, songList);
        AudioList.setAdapter(listadapter);

        Search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                listadapter.getFilter().filter(newText);
                return false;
            }
        });

        AudioList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(),resID[position]);
                title.setText(songList[position]);

                Play.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pause = mediaPlayer.getCurrentPosition();
                        mediaPlayer.start();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                            mediaPlayer.clearOnMediaTimeDiscontinuityListener();
                        }

                        if(mediaPlayer.isPlaying()){
                            mediaPlayer.pause();
                            pause = mediaPlayer.getCurrentPosition();
                            mediaPlayer.start();
                        }

                    }
                });
                Pause.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mediaPlayer.isPlaying()){
                            pause = mediaPlayer.getCurrentPosition();
                            mediaPlayer.pause();
                        }
                    }
                });
                Stop.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pause = 0;
                        mediaPlayer.seekTo(pause);
                        mediaPlayer.pause();
                    }
                });

            }
        });


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
