package com.example.reverie_themeditationapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class SignUp extends AppCompatActivity {
    EditText n,e,p,c;
    Button Capture;
    ImageView Image;
    private static final int PERMISSION_CODE = 1000;
    private static final int IMAGE_CAPTURE_CODE = 1001;
    Uri image_uri;
    static Database_Helper myDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //Camera capture
        Capture = (Button) findViewById(R.id.Capture);
        Image = (ImageView) findViewById(R.id.ProfilePic);
        Capture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"You took a picture", Toast.LENGTH_LONG).show();
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if(checkSelfPermission(Manifest.permission.CAMERA)== PackageManager.PERMISSION_DENIED || checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)==PackageManager.PERMISSION_DENIED){
                        String[] permission = {Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        requestPermissions(permission,PERMISSION_CODE);
                    }
                    else{
                        openCamera();
                    }
                }
                else{
                    openCamera();
                }
            }
        });

        //Database Connection
        //final Database_Helper dbHelper = new Database_Helper(this);
        myDB = new Database_Helper(this);
        n = (EditText) findViewById(R.id.Username);
        e = (EditText) findViewById(R.id.Email);
        p = (EditText) findViewById(R.id.Pswd);
        c = (EditText) findViewById(R.id.ConfPswd);

        final Button Signup = (Button) findViewById(R.id.Sign);
        Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s1 = n.getText().toString();    //name
                String s2 = e.getText().toString();    //email
                String s3 = p.getText().toString();    //pswd
                String s4 = c.getText().toString();    //confpswd
                if (s1.equals("") || s2.equals("")|| s3.equals("") || s4.equals("")){
                    Toast.makeText(getApplicationContext(), "Fields are empty", Toast.LENGTH_LONG).show();
                }else{
                    
                        if (s3.equals(s4)) {
                            Boolean checkmail = myDB.checkemail(s1);
                            if (checkmail == true) {
                                Boolean insert = myDB.InsertReg(s2, s1, s3);
                                if (insert == true) {
                                    Toast.makeText(getApplicationContext(), "Registered Successfully", Toast.LENGTH_LONG).show();
                                    Intent log = new Intent(SignUp.this, LogIn.class);
                                    startActivity(log);
                                }
                            } else {
                                Toast.makeText(getApplicationContext(), "Email already exists", Toast.LENGTH_LONG).show();
                                Intent log = new Intent(SignUp.this, LogIn.class);
                                startActivity(log);
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "Passwords don't match", Toast.LENGTH_LONG).show();

                        }                }

            }
        });
    }

    private void openCamera() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.Images.Media.TITLE,"New Picture");
        contentValues.put(MediaStore.Images.Media.DESCRIPTION,"From the camera");
        image_uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,contentValues);
        Intent picture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        picture.putExtra(MediaStore.EXTRA_OUTPUT,image_uri);
        startActivityForResult(picture, IMAGE_CAPTURE_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case PERMISSION_CODE:{
                if(grantResults.length > 0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    openCamera();
                }
                else{
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Image.setImageURI(image_uri);
        }
    }

   /* public void Signup(View v) {
        String s1 = n.getText().toString();    //name
        String s2 = e.getText().toString();    //email
        String s3 = p.getText().toString();    //pswd
        String s4 = c.getText().toString();
        if (s1.equals("") || s2.equals("") || s3.equals("") || s4.equals("")) {
            Toast.makeText(getApplicationContext(), "Fields are empty", Toast.LENGTH_LONG).show();
        } else {

            if (s3.equals(s4)) {
                boolean result = SignUp.myDB.insertdata(s1, s2, s3);
                if(result){
                    Toast.makeText(getApplicationContext(), "Registered Successfully", Toast.LENGTH_LONG).show();
                    Intent log = new Intent(SignUp.this, LogIn.class);
                    startActivity(log);
                }else{
                    AlertDialog.Builder ad = new AlertDialog.Builder(this);
                    ad.setMessage("Wrong Input");
                    ad.show();
                }
            }
        }
    }*/

}
