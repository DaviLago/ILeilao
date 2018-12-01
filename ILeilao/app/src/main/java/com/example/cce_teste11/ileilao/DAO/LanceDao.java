package com.example.cce_teste11.ileilao.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.provider.ContactsContract;

import com.example.cce_teste11.ileilao.DataBaseSQLite.DataBaseHelper;
import com.example.cce_teste11.ileilao.Model.LanceModel;
import com.example.cce_teste11.ileilao.Model.ProductModel;

import java.util.ArrayList;
import java.util.List;

public class LanceDao {

    //DataBase Instance
    DataBaseHelper db;
    UserDao userDao;

    public LanceDao(Context context) {
        db = new DataBaseHelper(context);
        userDao = new UserDao(context);
    }

    public boolean insert(LanceModel lance){
        ContentValues content = new ContentValues();
        content.put(DataBaseHelper.LANCE_COL_VALUE, lance.getValue());
        content.put(DataBaseHelper.LANCE_COL_PROD_ID, lance.getProduct().getId());
        content.put(DataBaseHelper.LANCE_COL_USER_ID, lance.getUser().getEmail());
        Long result = db.getWritableDatabase().insert(DataBaseHelper.LANCE_TABLE_NAME, null, content);
        if(result == -1)
            return false;
        return true;
    }

    public List<LanceModel> findAllLance(){
        Cursor cursor = db.getWritableDatabase().rawQuery( "SELECT * FROM " + DataBaseHelper.LANCE_TABLE_NAME, null);
        return getLanceList(cursor);
    }

    public Boolean isValidLance(Double value, Long prod_id){
        LanceModel lance = getMaxValue(prod_id);
        if(lance == null)
            return true;
        return value > lance.getValue();
    }

    public LanceModel getMaxValue(Long prod_id){
        Cursor cursor = db.getWritableDatabase().rawQuery( "SELECT * FROM " + DataBaseHelper.LANCE_TABLE_NAME +
                " WHERE " + DataBaseHelper.LANCE_COL_VALUE + " = (SELECT MAX(" + DataBaseHelper.LANCE_COL_VALUE + ") FROM " + DataBaseHelper.LANCE_TABLE_NAME + ")" +
                " AND " + DataBaseHelper.LANCE_COL_PROD_ID + " = '" + prod_id + "';", null);

        if(cursor.getCount() == 0)
            return null;
        return getLanceList(cursor).get(0);
    }

    public List<LanceModel> findAllLanceByProductId(Long prod_id){
        Cursor cursor = db.getWritableDatabase().rawQuery( "SELECT * FROM " + DataBaseHelper.LANCE_TABLE_NAME +
                " WHERE " + DataBaseHelper.LANCE_COL_PROD_ID + " = '" + prod_id + "';", null);
        return getLanceList(cursor);
    }

    private List<LanceModel> getLanceList(Cursor cursor){
        List<LanceModel> lances = new ArrayList<>();
        for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {

            lances.add(new LanceModel(Long.parseLong(cursor.getString(0)),
                    Double.parseDouble(cursor.getString(1)),
                    new ProductModel(Long.parseLong(cursor.getString(2))),
                    userDao.findUserByEmail(cursor.getString(3))));
        }
        return lances;
    }

}
