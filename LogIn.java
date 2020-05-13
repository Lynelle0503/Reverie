package com.example.reverie_themeditationapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LogIn extends AppCompatActivity {

    Button SignUp;
    Button LogIn;
    EditText e, p;


    static Database_Helper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        db = new Database_Helper(this);
        LogIn = (Button) findViewById(R.id.Loginbutton);
        SignUp = (Button) findViewById(R.id.Signbutton);
        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sign = new Intent(LogIn.this, SignUp.class);
                startActivity(sign);
            }
        });

        e = (EditText) findViewById(R.id.EmailID);
        p = (EditText) findViewById(R.id.Password);

        LogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = e.getText().toString();
                String pass = p.getText().toString();
                Boolean emailpass = db.emailpass(email, pass);
                                    if (emailpass == true) {
                        Toast.makeText(getApplicationContext(), "Successfully Logged In", Toast.LENGTH_LONG).show();
                        Intent home = new Intent(LogIn.this, Homepage.class);
                        startActivity(home);
                    } else {
                        Toast.makeText(getApplicationContext(), "Wrong Email or Password", Toast.LENGTH_LONG).show();
                    }
                }

        });

    }

    /*public void Login(View v){
        String email = e.getText().toString();
        String pass = p.getText().toString();
        Cursor c = myDB.Logincheck(email);
        c.moveToFirst();
        if(c == null){
            Toast.makeText(LogIn.this, "Record does not exist", Toast.LENGTH_LONG).show();
        }else{
            String name2 = c.getString(0);
            String pass2 = c.getString(1);
            Toast.makeText(LogIn.this, "Success", Toast.LENGTH_LONG).show();
            Intent home = new Intent(LogIn.this, Homepage.class);
            startActivity(home);
        }
    }*/
}
