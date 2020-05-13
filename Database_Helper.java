package com.example.reverie_themeditationapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

public class Database_Helper extends SQLiteOpenHelper {
    private static final String DBNAME="Register";
    private static final int VERSION = 1;
    private static final String TABLE_NAME = "USER";
    private static final String COL_EMAIL = "EMAIL";
    private static final String COL_NAME = "NAME";
    private static final String COL_PASS = "PASS";

    SQLiteDatabase sqLiteDatabase;
    Context c;
    public Database_Helper(Context context) {
        super(context, DBNAME, null, VERSION);
        c = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try{
            String create_table = "CREATE TABLE "+ TABLE_NAME +"(" + COL_EMAIL + " TEXT PRIMARY KEY,"+ COL_NAME +" TEXT,"+ COL_PASS +" TEXT);";
            db.execSQL(create_table);
            Toast.makeText(c,"Table Created Successfully", Toast.LENGTH_LONG).show();
            db.close();
        }catch (Exception e){
            Log.e("DB","Table Creation Error", e);
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
   public boolean InsertReg(String email, String name, String pass){
       sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_EMAIL,email);
        values.put(COL_NAME,name);
        values.put(COL_PASS,pass);


        long ins = sqLiteDatabase.insert(TABLE_NAME,null, values);
        sqLiteDatabase.close();
        if (ins ==-1) return false;
        else return true;
    }
 public Boolean checkemail(String email){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from " + TABLE_NAME + " where email=?", new String[]{email});
        if (cursor.getCount()>0) return false;
        else return true    ;
    }
    public Boolean emailpass(String email, String pass){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from "+ TABLE_NAME + " where email = ? and pass = ?", new String[] {email, pass});
        if (cursor.getCount()>0) return true;
        else return false;
    }

    public boolean updatedb(String email, String passup){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE "+TABLE_NAME +" SET pass= '" + passup+ "' where email='" + email + "';");
        db.close();
        return true;
    }

    public boolean deletedb(String email, String pass){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME,"email='"+email + "';",null);
        return true;
    }

    /*public boolean insertdata(String name, String email, String pass){

        try{
            String qry= "insert into Register values('"+name+"',"+email+"',"+pass+"')";
            SQLiteDatabase db = getWritableDatabase();
            db.execSQL(qry);
            Toast.makeText(c, name+ "Registered Successfully", Toast.LENGTH_LONG).show();
            return true;
        }catch(Exception e){
            Log.e("DB", "Record Insertion failed", e);
            return false;
        }
    }
    public Cursor Logincheck(String email){
        try{
            String qry = "Select Name, Password from Register where EMAIL='"+email+"'";
            SQLiteDatabase db = getWritableDatabase();
            Cursor c = db.rawQuery(qry, null);
            return c;
        }catch(Exception e){
            Log.e("DB", "Record Insertion failed", e);
            return null;
        }
    }*/

}
