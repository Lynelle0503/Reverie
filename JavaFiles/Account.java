package com.example.reverie_themeditationapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ShareActionProvider;
import android.widget.TimePicker;
import android.widget.Toast;


import com.example.reverie_themeditationapplication.AlarmReceiver;
import com.example.reverie_themeditationapplication.Geolocation;
import com.example.reverie_themeditationapplication.R;
import com.google.android.material.navigation.NavigationView;

import java.util.Calendar;

public class Account extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener{
    Button Location;
    private int Notification = 1;
    //Navigation
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle Toggle;
    private Toolbar toolbar;
    private ShareActionProvider share;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profilepage);
        //GPS
        Location = (Button) findViewById(R.id.locationbutton);
        Location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Account.this, Geolocation.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.notify).setOnClickListener(Account.this);
        DatePicker datePicker = findViewById(R.id.date);

        int year = datePicker.getYear();
        int month = datePicker.getMonth();
        int day = datePicker.getDayOfMonth();
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH,day);

        drawerLayout = (DrawerLayout) findViewById(R.id.accountpage);
        Toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        toolbar = (Toolbar) findViewById(R.id.nav_action);
        setSupportActionBar(toolbar);

        drawerLayout.addDrawerListener(Toggle);
        Toggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



    }

    @Override
    public void onClick(View v) {
        TimePicker timePicker = findViewById(R.id.time);
        Intent intent = new Intent(Account.this, AlarmReceiver.class);
        intent.putExtra("notification id", Notification);

        PendingIntent alarmIntent = PendingIntent.getBroadcast(Account.this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);


        int hour = timePicker.getCurrentHour();
        int minute = timePicker.getCurrentMinute();

        Calendar startTime = Calendar.getInstance();
        startTime.set(Calendar.HOUR_OF_DAY, hour);
        startTime.set(Calendar.MINUTE, minute);
        startTime.set(Calendar.SECOND,0);
        long alarmStartTime = startTime.getTimeInMillis();

        alarmManager.set(AlarmManager.RTC_WAKEUP,alarmStartTime,alarmIntent);

        Toast.makeText(this, "Time is Set.", Toast.LENGTH_LONG).show();
    }

    //Toggle button
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(Toggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        switch (id){
            case R.id.account:
                Intent prof = new Intent(this, Account.class);
                prof.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(prof);
                break;
            case R.id.home:
                Intent home = new Intent(this, Homepage.class);
                home.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(home);
                break;
            case R.id.setting:
                Intent policy = new Intent(this,Settingpage.class);
                policy.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(policy);
                break;
            case R.id.feedback:
                Intent feed = new Intent(this,Feedbackpage.class);
                feed.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(feed);
                break;
            case R.id.share:
                Intent shareit = new Intent(Intent.ACTION_SEND);
                shareit.setType("text/plain");
                shareit.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                shareit.putExtra(Intent.EXTRA_TEXT, "Share application");   //message that is shared.
                startActivity(Intent.createChooser(shareit,"Share via."));
                break;
            case R.id.logout:
                Intent i = new Intent(this, LogIn.class);
// set the new task and clear flags
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                Toast.makeText(this, "Successfully Logged out", Toast.LENGTH_LONG).show();
        }
        return false;
    }
}
