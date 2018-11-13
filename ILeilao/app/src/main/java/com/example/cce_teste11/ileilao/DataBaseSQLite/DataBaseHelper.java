package com.example.cce_teste11.ileilao.DataBaseSQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.cce_teste11.ileilao.Model.UserModel;

public class DataBaseHelper extends SQLiteOpenHelper {

    //DataBase Name
    public final static String DATABASE_NAME = "ILeilao_DB";

    //User Table ---
    public final static String USER_TABLE_NAME = "users";
    public final static String USER_COL_NAME = "name";
    public final static String USER_COL_EMAIL = "email";
    public final static String USER_COL_PASSWORD = "password";
    public final static String USER_COL_TYPE = "type";
    // Create User Table
    public final static String CREATE_USER_TABLE = "CREATE TABLE " + USER_TABLE_NAME +
            "(" + USER_COL_EMAIL + " VARCHAR(25) PRIMARY KEY, " +
                 USER_COL_NAME + " VARCHAR(25) NOT NULL, " +
                 USER_COL_PASSWORD + " VARCHAR(25) NOT NULL, " +
                 USER_COL_TYPE + " VARCHAR(10) NOT NULL);";
    //-----------

    //ProductModel Table ---
    public final static String PROD_TABLE_NAME = "products";
    public final static String PROD_COL_ID = "id";
    public final static String PROD_COL_NAME = "name";
    public final static String PROD_COL_DESCRIPTION = "description";
    public final static String PROD_COL_MIN_VALUE = "value";
    public final static String PROD_COL_PASSWORD = "password";
    public final static String PROD_COL_LEILOEIRO = "leiloeiro_id";
    // Create ProductModel Table
    public final static String CREATE_PROD_TABLE = "CREATE TABLE " + PROD_TABLE_NAME +
            "(" + PROD_COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            PROD_COL_NAME + " VARCHAR(25) NOT NULL, " +
            PROD_COL_DESCRIPTION + " VARCHAR(45), " +
            PROD_COL_MIN_VALUE + " DOUBLE(25, 2) NOT NULL, " +
            PROD_COL_PASSWORD + " VARCHAR(25) NOT NULL, " +
            PROD_COL_LEILOEIRO + " VARCHAR(25), " +
            "FOREIGN KEY (" + PROD_COL_LEILOEIRO + ") REFERENCES " + PROD_TABLE_NAME + "(" + USER_COL_EMAIL + "));";
    //-----------

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 3);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_PROD_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + PROD_TABLE_NAME);
        onCreate(db);
    }

}
