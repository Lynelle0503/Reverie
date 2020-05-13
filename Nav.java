package com.example.reverie_themeditationapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import androidx.viewpager.widget.ViewPager;

import android.location.Location;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class Nav extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle Toggle;
    private Toolbar toolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);
        Toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        toolbar = (Toolbar) findViewById(R.id.nav_action);
        setSupportActionBar(toolbar);

        drawerLayout.addDrawerListener(Toggle);
        Toggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



    }
    //Toggle button
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(Toggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    //Clicking the navigation menu
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
