package com.example.reverie_themeditationapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.ShareActionProvider;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

public class Feedbackpage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle Toggle;
    private Toolbar toolbar;

    private ShareActionProvider share;

    private EditText To, Subjecttext, Messagetext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedbackpage);
        drawerLayout = (DrawerLayout) findViewById(R.id.feedbacklayout);
        Toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        toolbar = (Toolbar) findViewById(R.id.nav_action);


        setSupportActionBar(toolbar);

        drawerLayout.addDrawerListener(Toggle);
        Toggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        final RatingBar rate = (RatingBar) findViewById(R.id.rate_bar);
        Button submit_rate = (Button) findViewById(R.id.but_rate);

        submit_rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Your rating is " + rate.getRating(), Toast.LENGTH_LONG).show();
            }
        });
        final Button sendemail = (Button) findViewById(R.id.buttonSend);
        sendemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMail();
            }
        });



    }
    private void sendMail() {
        To = (EditText) findViewById(R.id.edit_text_to);
        Subjecttext = (EditText) findViewById(R.id.edit_text_subject);
        Messagetext = (EditText)findViewById(R.id.edit_text_message);

        String recipientList = To.getText().toString().trim();
        String[] recipients = recipientList.split(",");

        String Subject = Subjecttext.getText().toString().trim();
        String Message = Messagetext.getText().toString().trim();

        Intent mail = new Intent(Intent.ACTION_SEND);
        mail.putExtra(Intent.EXTRA_EMAIL,recipients);
        mail.putExtra(Intent.EXTRA_SUBJECT,Subject);
        mail.putExtra(Intent.EXTRA_TEXT,Message);

        mail.setType("message/rfc822");
        startActivity(Intent.createChooser(mail,"Choose an email client"));

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
                startActivity(home);
                break;
            case R.id.setting:
                Intent policy = new Intent(this,Settingpage.class);
                policy.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(policy);
                break;
            case R.id.feedback:
                Intent feed = new Intent(this,Feedbackpage.class);
                startActivity(feed);
                break;
            case R.id.share:
                Intent shareit = new Intent(Intent.ACTION_SEND);
                shareit.setType("text/plain");
                shareit.putExtra(Intent.EXTRA_TEXT, "Share application");   //message that is shared.
                startActivity(Intent.createChooser(shareit,"Share via."));
                break;
            case R.id.logout:
                Intent back = new Intent(this, LogIn.class);
                startActivity(back);
                finish();
                Toast.makeText(this, "Successfully Logged out", Toast.LENGTH_LONG).show();

        }
        return false;
    }
}
