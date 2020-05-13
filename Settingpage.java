package com.example.reverie_themeditationapplication;

import androidx.annotation.Size;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;


import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ShareActionProvider;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class Settingpage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private long Backpressedtime;

    Button Newac, Chgac, About;


    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle Toggle;
    private Toolbar toolbar;
    private ShareActionProvider share;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settingpage);


        Newac = (Button) findViewById(R.id.newac);
        Newac.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        //Login page
        Toast.makeText(Settingpage.this, "You will be redirected to the LogIn page", Toast.LENGTH_LONG).show();
        Intent ac = new Intent(Settingpage.this, LogIn.class);
        startActivity(ac);
         }
        });
        Chgac = (Button) findViewById(R.id.changeac);
        Chgac.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        //Login page
        Intent change = new Intent(Settingpage.this, ChangeInfo.class);
        startActivity(change);

        Toast.makeText(Settingpage.this, "You will be redirected to the update page", Toast.LENGTH_LONG).show();
    }
        });

        About = (Button) findViewById(R.id.about);
        About.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        openDialogpolicy();
    }
});



        //NavigationBar
        drawerLayout = (DrawerLayout) findViewById(R.id.setting);
        Toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        toolbar = (Toolbar) findViewById(R.id.nav_action);
        setSupportActionBar(toolbar);
        drawerLayout.addDrawerListener(Toggle);
        Toggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }



private void openDialogpolicy() {
    Dialogue dialog = new Dialogue();
    dialog.show(getSupportFragmentManager(),"example dialog");
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

    //Toggle button
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(Toggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected( MenuItem menuItem) {
        int id = menuItem.getItemId();
        switch (id){
            case R.id.account:
                Intent prof = new Intent(this,Account.class);
                startActivity(prof);
                break;
            case R.id.home:
                Intent home = new Intent(this, Homepage.class);
                home.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(home);
                break;
            case R.id.setting:
                Intent policy = new Intent(this,Settingpage.class);
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