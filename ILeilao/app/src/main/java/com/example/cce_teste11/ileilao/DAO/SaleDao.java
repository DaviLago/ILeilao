package com.example.cce_teste11.ileilao.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.cce_teste11.ileilao.DataBaseSQLite.DataBaseHelper;
import com.example.cce_teste11.ileilao.Model.SaleModel;

import java.util.ArrayList;
import java.util.List;

public class SaleDao {

    //DataBase Instance
    DataBaseHelper db;
    ProductDao productDao;

    public SaleDao(Context context) {
        //Creating DataBase Object
        db = new DataBaseHelper(context);
        productDao = new ProductDao(context);
    }

    public boolean insertSale(SaleModel sale){
        ContentValues content = new ContentValues();
        content.put(DataBaseHelper.SALE_COL_MIN_VALUE, sale.getMin_value());
        content.put(DataBaseHelper.SALE_COL_PASSWORD, sale.getPassword());
        content.put(DataBaseHelper.SALE_COL_STATUS, new Boolean(true));
        content.put(DataBaseHelper.SALE_COL_PROD_ID, sale.getProduct().getId());
//        content.put(DataBaseHelper.SALE_COL_SELLER_ID, sale.getSeller().getEmail());

        Long result = db.getWritableDatabase().insert(DataBaseHelper.SALE_TABLE_NAME, null, content);

        if(result == -1)
            return false;
//      productDao.updateProductSaleById(sale.getProduct().getId(), findSaleByProdId(sale.getProduct().getId()).getId());
        return true;
    }

    public boolean update(SaleModel sale){
        ContentValues content = new ContentValues();
        content.put(DataBaseHelper.SALE_COL_MIN_VALUE, sale.getMin_value());
        Integer result = db.getWritableDatabase().update(DataBaseHelper.SALE_TABLE_NAME, content,
                DataBaseHelper.SALE_COL_ID + " = ?", new String[]{sale.getId().toString()});
        return result > 0;
    }

    public SaleModel findSaleByProdId(Long prod_id){
        Cursor cursor = db.getWritableDatabase().rawQuery( "SELECT * FROM " + DataBaseHelper.SALE_TABLE_NAME +
                " WHERE " + DataBaseHelper.SALE_COL_PROD_ID + " = '" + prod_id + "';", null);

        if(cursor.getCount() == 1) {
            cursor.moveToFirst();
            return new SaleModel(Long.parseLong(cursor.getString(0)),
                    Double.parseDouble(cursor.getString(1)),
                    cursor.getString(2),
                    cursor.getString(3).compareTo("1") == 0 ? true : false);
        }
        else
            return null;
    }

    public List<SaleModel> findAllSale(){
        Cursor cursor = db.getWritableDatabase().rawQuery( "SELECT * FROM " + DataBaseHelper.SALE_TABLE_NAME, null);
        return getSaleList(cursor);
    }

    private List<SaleModel> getSaleList(Cursor cursor){
        List<SaleModel> sales = new ArrayList<>();
        for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {

            sales.add(new SaleModel(Long.parseLong(cursor.getString(0)),
                    Double.parseDouble(cursor.getString(1)),
                    cursor.getString(2),
                    cursor.getString(3).compareTo("1") == 0 ? true : false,
                    productDao.findProductById(Long.parseLong(cursor.getString(4)))));
//                    userDao.findUserByEmail(cursor.getString(5))));
        }
        return sales;
    }

}
