package com.example.cce_teste11.ileilao.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.cce_teste11.ileilao.DataBaseSQLite.DataBaseHelper;
import com.example.cce_teste11.ileilao.Model.ProductModel;
import com.example.cce_teste11.ileilao.Model.UserModel;

import java.util.ArrayList;
import java.util.List;

public class UserDao {

    //DataBase Instance
    DataBaseHelper db;

    public UserDao(Context context) {
        //Creating DataBase Object
        db = new DataBaseHelper(context);
    }

    public boolean insertUser(UserModel user){
        ContentValues content = new ContentValues();
        content.put(DataBaseHelper.USER_COL_NAME, user.getName());
        content.put(DataBaseHelper.USER_COL_EMAIL_ID, user.getEmail());
        content.put(DataBaseHelper.USER_COL_PASSWORD, user.getPassword());
        content.put(DataBaseHelper.USER_COL_TYPE, user.getType());
        Long result = db.getWritableDatabase().insert(DataBaseHelper.USER_TABLE_NAME, null, content);
        if(result == -1)
            return false;
        return true;
    }

    public boolean login(UserModel user){
        Cursor cursor = db.getWritableDatabase().rawQuery( "SELECT * FROM " + DataBaseHelper.USER_TABLE_NAME +
                " WHERE " + DataBaseHelper.USER_COL_EMAIL_ID + " = '" + user.getEmail() +
                "' AND " + DataBaseHelper.USER_COL_PASSWORD + " = '" + user.getPassword() +
                "' AND " + DataBaseHelper.USER_COL_TYPE + " = '" + user.getType() + "'", null);

        if(cursor.getCount() == 1) {
            UserModel.setLogged_user(getUserModel(cursor));
            return true;
        }
        else
            return false;

    }

    public UserModel findUserByEmailAndPassword(String email, String password){
        Cursor cursor = db.getWritableDatabase().rawQuery( "SELECT * FROM " + DataBaseHelper.USER_TABLE_NAME +
                " WHERE " + DataBaseHelper.USER_COL_EMAIL_ID + " = '" + email +
                "' AND " + DataBaseHelper.USER_COL_PASSWORD + " = '" + password + "'", null);

        if(cursor.getCount() == 1) {
            return getUserModel(cursor);
        }
        else
            return null;

    }

    public UserModel findUserByEmail(String email){
        Cursor cursor = db.getWritableDatabase().rawQuery( "SELECT * FROM " + DataBaseHelper.USER_TABLE_NAME +
                " WHERE " + DataBaseHelper.USER_COL_EMAIL_ID + " = '" + email + "'", null);

        if(cursor.getCount() == 1)
            return getUserModel(cursor);
        else
            return null;

    }

    public List<UserModel> findAllUser(){
        Cursor cursor = db.getWritableDatabase().rawQuery( "SELECT * FROM " + DataBaseHelper.USER_TABLE_NAME, null);

        if(cursor.getCount() == 1)
            return getUserModelList(cursor);
        else
            return null;

    }

    private UserModel getUserModel(Cursor cursor){
        cursor.moveToFirst();
        UserModel user = new UserModel(cursor.getString(1), cursor.getString(0),
                cursor.getString(2), cursor.getString(3));
        return user;
    }

    private List<UserModel> getUserModelList(Cursor cursor){
        List<UserModel> users = new ArrayList<>();
        for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {

            users.add(new UserModel(cursor.getString(1), cursor.getString(0),
                    cursor.getString(2), cursor.getString(3)));
        }
        return users;
    }

}
