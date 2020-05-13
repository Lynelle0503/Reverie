package com.example.reverie_themeditationapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ChangeInfo extends AppCompatActivity {
    Button Update, Delete;
    EditText Email, Password;

    Database_Helper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_info);

        Update = (Button) findViewById(R.id.updatebtn);
        Delete = (Button) findViewById(R.id.deletebtn);
        Email = (EditText) findViewById(R.id.emailupdate);
        Password = (EditText) findViewById(R.id.Pswdupdate);
        db = new Database_Helper(this);

        final String email = Email.getText().toString();
        final String password = Password.getText().toString();
    Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (email == " " || password == " ") {
                    Toast.makeText(getApplicationContext(), "Enter Email ID", Toast.LENGTH_LONG).show();
                } else {
                    Boolean update = db.updatedb(email, password);
                    if (update == true) {
                        Toast.makeText(getApplicationContext(), "Successfully Updated Record", Toast.LENGTH_LONG).show();
                        Intent home = new Intent(ChangeInfo.this, LogIn.class);
                        home.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(home);
                    } else {
                        Toast.makeText(getApplicationContext(), "Record not found", Toast.LENGTH_LONG).show();
                    }

                }
            }
        });


        Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (email == " " || password == " ") {
                    Toast.makeText(getApplicationContext(), "Enter Email ID", Toast.LENGTH_LONG).show();
                } else {
                    Boolean delete = db.deletedb(email, password);
                    if (delete == true) {
                        Toast.makeText(getApplicationContext(), "Successfully Deleted Record", Toast.LENGTH_LONG).show();
                        Intent home = new Intent(ChangeInfo.this, LogIn.class);
                        home.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(home);
                    } else {
                        Toast.makeText(getApplicationContext(), "Record Not found", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }
}
