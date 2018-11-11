package com.example.cce_teste11.ileilao.DataBaseSQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.cce_teste11.ileilao.UserModel;

public class DataBaseHelper extends SQLiteOpenHelper {

    //DataBase Name
    public final static String DATABASE_NAME = "ILeilao_DB";
    //User Table
    public final static String USER_TABLE_NAME = "users";
    public final static String USER_COL_NAME = "name";
    public final static String USER_COL_EMAIL = "email";
    public final static String USER_COL_PASSWORD = "password";
    public final static String USER_COL_TYPE = "type";
    //---------
    // Create User Table
    public final static String CREATE_USER_TABLE = "CREATE TABLE " + USER_TABLE_NAME +
            "("+ USER_COL_EMAIL + " TEXT PRIMARY KEY," +
                 USER_COL_NAME + " TEXT NOT NULL, " +
                 USER_COL_PASSWORD + " TEXT NOT NULL, " +
                 USER_COL_TYPE + " TEXT NOT NULL);";
    //---------

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE_NAME);
        onCreate(db);
    }

    public boolean insertUser(UserModel user){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put(USER_COL_NAME, user.getName());
        content.put(USER_COL_EMAIL, user.getEmail());
        content.put(USER_COL_PASSWORD, user.getPassword());
        content.put(USER_COL_TYPE, user.getType());
        Long result = db.insert(USER_TABLE_NAME, null, content);
        if(result == -1)
            return false;
        return true;
    }

}
