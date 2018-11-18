package com.example.cce_teste11.ileilao.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.cce_teste11.ileilao.DataBaseSQLite.DataBaseHelper;
import com.example.cce_teste11.ileilao.Model.ProductModel;
import com.example.cce_teste11.ileilao.Model.SaleModel;

import java.util.ArrayList;
import java.util.List;

public class ProductDao {

    //DataBase Instance
    DataBaseHelper db;
    UserDao userDao;

    public ProductDao(Context context) {
        //Creating DataBase Object
        db = new DataBaseHelper(context);
        userDao = new UserDao(context);
    }

    public boolean insertProduct(ProductModel prod){
        ContentValues content = new ContentValues();
        content.put(DataBaseHelper.PROD_COL_NAME, prod.getProd_name());
        content.put(DataBaseHelper.PROD_COL_DESCRIPTION, prod.getProd_description());
        content.put(DataBaseHelper.PROD_COL_SELLER_ID, prod.getSeller().getEmail());
        content.put(DataBaseHelper.PROD_COL_STATUS, new Boolean(true));
        Long result = db.getWritableDatabase().insert(DataBaseHelper.PROD_TABLE_NAME, null, content);
        if(result == -1)
            return false;
        return true;
    }

    public List<ProductModel> findAllProduct(){
        Cursor cursor = db.getWritableDatabase().rawQuery( "SELECT * FROM " + DataBaseHelper.PROD_TABLE_NAME, null);
        return getProductList(cursor);
    }

    public ProductModel findProductById(Long prod_id){
        Cursor cursor = db.getWritableDatabase().rawQuery( "SELECT * FROM " + DataBaseHelper.PROD_TABLE_NAME +
                " WHERE " + DataBaseHelper.PROD_COL_ID + " = '" + prod_id + "'", null);

        if(cursor.getCount() == 1)
            return getProductList(cursor).get(0);
        else
            return null;
    }

    private List<ProductModel> getProductList(Cursor cursor){
        List<ProductModel> products = new ArrayList<>();
        for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {

            products.add(new ProductModel(Long.parseLong(cursor.getString(0)),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3).compareTo("1") == 0 ? true : false,
                    userDao.findUserByEmail(cursor.getString(4))));
        }
        return products;
    }

}
