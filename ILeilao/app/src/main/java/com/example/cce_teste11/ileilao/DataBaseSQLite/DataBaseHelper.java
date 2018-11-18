package com.example.cce_teste11.ileilao.DataBaseSQLite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {

    //DataBase Name
    public final static String DATABASE_NAME = "ILeilao_DB";

    //User Table ---
    public final static String USER_TABLE_NAME = "users";
    public final static String USER_COL_NAME = "name";
    public final static String USER_COL_EMAIL_ID = "email";
    public final static String USER_COL_PASSWORD = "password";
    public final static String USER_COL_TYPE = "type";

    //Sale Table ---
    public final static String SALE_TABLE_NAME = "sales";
    public final static String SALE_COL_ID = "id";
    public final static String SALE_COL_MIN_VALUE = "min_value";
    public final static String SALE_COL_PASSWORD = "password";
    public final static String SALE_COL_STATUS = "status";
    public final static String SALE_COL_PROD_ID = "product_id";
//    public final static String SALE_COL_SELLER_ID = "seller_id";

    //Product Table ---
    public final static String PROD_TABLE_NAME = "product";
    public final static String PROD_COL_ID = "id";
    public final static String PROD_COL_NAME = "name";
    public final static String PROD_COL_DESCRIPTION = "description";
    public final static String PROD_COL_STATUS = "status";
    public final static String PROD_COL_SELLER_ID = "seller_id";
//    public final static String PROD_COL_SALE_ID = "sale_id";

    // Create User Table
    public final static String CREATE_USER_TABLE = "CREATE TABLE " + USER_TABLE_NAME +
            "(" + USER_COL_EMAIL_ID + " VARCHAR(25) PRIMARY KEY, " +
            USER_COL_NAME + " VARCHAR(25) NOT NULL, " +
            USER_COL_PASSWORD + " VARCHAR(25) NOT NULL, " +
            USER_COL_TYPE + " VARCHAR(10) NOT NULL);";
    //-----------

    // Create Sale Model Table
    public final static String CREATE_SALE_TABLE = "CREATE TABLE " + SALE_TABLE_NAME +
            "(" + SALE_COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            SALE_COL_MIN_VALUE + " DOUBLE(25, 2) NOT NULL, " +
            SALE_COL_PASSWORD + " VARCHAR(25) NOT NULL, " +
            SALE_COL_STATUS + " BOOLEAN NOT NULL, " +
            SALE_COL_PROD_ID + " INTEGER NOT NULL, " +
//            SALE_COL_SELLER_ID + " VARCHAR(25) NOT NULL, " +
            "FOREIGN KEY (" + SALE_COL_PROD_ID + ") REFERENCES " + PROD_TABLE_NAME + "(" + PROD_COL_ID + "));";
//            "FOREIGN KEY (" + SALE_COL_SELLER_ID + ") REFERENCES " + USER_TABLE_NAME + "(" + USER_COL_EMAIL_ID + "));";
    //-----------

    // Create Product Model Table
    public final static String CREATE_PROD_TABLE = "CREATE TABLE " + PROD_TABLE_NAME +
            "(" + PROD_COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            PROD_COL_NAME + " VARCHAR(25) NOT NULL, " +
            PROD_COL_DESCRIPTION + " VARCHAR(25) NOT NULL, " +
            PROD_COL_STATUS + " BOOLEAN NOT NULL, " +
            PROD_COL_SELLER_ID + " VARCHAR(25) NOT NULL, " +
//            PROD_COL_SALE_ID + " VARCHAR(25), " +
            "FOREIGN KEY (" + PROD_COL_SELLER_ID + ") REFERENCES " + USER_TABLE_NAME + "(" + USER_COL_EMAIL_ID + "));";
//            "FOREIGN KEY (" + PROD_COL_SALE_ID + ") REFERENCES " + SALE_TABLE_NAME + "(" + SALE_COL_ID + "));";
    //-----------

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 9);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_SALE_TABLE);
        db.execSQL(CREATE_PROD_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + PROD_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + SALE_TABLE_NAME);
        onCreate(db);
    }

}
