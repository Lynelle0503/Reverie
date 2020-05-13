package com.example.reverie_themeditationapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Size;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.widget.ShareActionProvider;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.hitomi.cmlibrary.CircleMenu;
import com.hitomi.cmlibrary.OnMenuSelectedListener;

public class Homepage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle Toggle;
    private Toolbar toolbar;
    private ShareActionProvider share;
    private long Backpressedtime;
    String arrayName [] = {"Meditate","Anxious","Morning routine", "Sleepy" };

    private int STORAGE_PERMISSION_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        drawerLayout = (DrawerLayout) findViewById(R.id.homelayout);
        Toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        toolbar = (Toolbar) findViewById(R.id.nav_action);
        setSupportActionBar(toolbar);

        CircleMenu circleMenu = (CircleMenu) findViewById(R.id.circle);
        circleMenu.setMainMenu(Color.parseColor("#033C67"),R.drawable.icon,R.drawable.icon)
            .addSubMenu(Color.parseColor("#033C67"),R.drawable.buttontop)
                .addSubMenu(Color.parseColor("#033C67"),R.drawable.buttonright)
                .addSubMenu(Color.parseColor("#033C67"),R.drawable.buttondown)
                .addSubMenu(Color.parseColor("#033C67"),R.drawable.buttonleft)
            .setOnMenuSelectedListener(new OnMenuSelectedListener() {
                @Override
                public void onMenuSelected(final int index) {

                    new android.os.Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            String ch = arrayName[index];
                            Intent audio = new Intent(Homepage.this,AudioPage.class);
                            startActivity(audio);
                        }
                    }, 800);
                    Toast.makeText(Homepage.this, "You selected "+arrayName[index], Toast.LENGTH_LONG).show();

                }
            });

        drawerLayout.addDrawerListener(Toggle);
        Toggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if(ContextCompat.checkSelfPermission(Homepage.this, Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED){
            Toast.makeText(Homepage.this, "You have already granted permission for GPS", Toast.LENGTH_LONG).show();
        }else{
            requestStoragePermission();
        }


    }

    private void requestStoragePermission() {
        if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)){
            new AlertDialog.Builder(this)
                    .setTitle("Permission Needed")
                    .setMessage("Permission requested")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(Homepage.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},STORAGE_PERMISSION_CODE);
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create().show();
        }else{
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},STORAGE_PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==STORAGE_PERMISSION_CODE){
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "PERMISSION GRANTED", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "PERMISSION DENIED", Toast.LENGTH_SHORT).show();
            }
        }
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