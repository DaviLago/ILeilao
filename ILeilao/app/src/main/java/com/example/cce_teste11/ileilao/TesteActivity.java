package com.example.cce_teste11.ileilao;

import android.content.ContentValues;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.cce_teste11.ileilao.DAO.LanceDao;
import com.example.cce_teste11.ileilao.DAO.ProductDao;
import com.example.cce_teste11.ileilao.DAO.SaleDao;
import com.example.cce_teste11.ileilao.DAO.UserDao;
import com.example.cce_teste11.ileilao.DataBaseSQLite.DataBaseHelper;
import com.example.cce_teste11.ileilao.Model.LanceModel;
import com.example.cce_teste11.ileilao.Model.ProductModel;
import com.example.cce_teste11.ileilao.Model.SaleModel;
import com.example.cce_teste11.ileilao.Model.UserModel;

import java.util.List;

public class TesteActivity extends AppCompatActivity {

    Button insert_btn, run_btn;
    UserDao userDao;
    SaleDao saleDao;
    LanceDao lanceDao;
    ProductDao productDao;
    DataBaseHelper db;
    List<UserModel> users;
    List<ProductModel> products;
    List<SaleModel> sales;
    List<LanceModel> lances;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teste);

        db = new DataBaseHelper(TesteActivity.this);

        userDao = new UserDao(TesteActivity.this);
        productDao = new ProductDao(TesteActivity.this);
        saleDao = new SaleDao(TesteActivity.this);
        lanceDao = new LanceDao(TesteActivity.this);

        insert_btn = findViewById(R.id.insert);
        insert_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
//                insertTestsUser();
//                insertTestsProduct();
//                insertTestsSale();
//                insertTestsLance();
            }
        });

        run_btn = findViewById(R.id.run);
        run_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
//                runTestsUser();
//                runTestsProduct();
//                runTestsSale();
                runTestsLance();
            }
        });
    }

    private Boolean insertTestsUser(){
        UserModel user = new UserModel("Teste", "teste@123", "123", "leiloeiro");
        ContentValues content = new ContentValues();
        content.put(DataBaseHelper.USER_COL_NAME, user.getName());
        content.put(DataBaseHelper.USER_COL_EMAIL_ID, user.getEmail());
        content.put(DataBaseHelper.USER_COL_PASSWORD, user.getPassword());
        content.put(DataBaseHelper.USER_COL_TYPE, user.getType());
        Long result = db.getWritableDatabase().insert(DataBaseHelper.USER_TABLE_NAME, null, content);
        if(result != -1)
            System.out.println("Insert 1 user ok +++++++++++++++++++++++++++++++++++");
        else {
            System.out.println("Erro Insert 1 user xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
            return false;
        }
        users = userDao.findAllUser();
        return true;
    }

    private Boolean insertTestsProduct(){
        users = userDao.findAllUser();
        ProductModel product = new ProductModel("Prod 001", "Prod description 001", new Boolean(true), users.get(0));
        ContentValues content = new ContentValues();
        content.put(DataBaseHelper.PROD_COL_NAME, product.getProd_name());
        content.put(DataBaseHelper.PROD_COL_DESCRIPTION, product.getProd_description());
        content.put(DataBaseHelper.PROD_COL_STATUS, product.getStatus());
        content.put(DataBaseHelper.PROD_COL_SELLER_ID, product.getSeller().getEmail());
        Long result = db.getWritableDatabase().insert(DataBaseHelper.PROD_TABLE_NAME, null, content);
        if(result != -1)
            System.out.println("Insert 1 product ok +++++++++++++++++++++++++++++++++++");
        else {
            System.out.println("Erro Insert 1 product xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
            return false;
        }
        products = productDao.findAllProduct();
        if(products.size() == 1)
            System.out.println("Products size: " + products.size() + " ++++++++++++++++++++++++++");
        else {
            System.out.println("Products size: " + products.size() + " xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
            return false;
        }
        return true;
    }

    private Boolean insertTestsSale(){
        users = userDao.findAllUser();
        products = productDao.findAllProduct();
        SaleModel sale = new SaleModel(new Double(12.5), "123", new Boolean(true), products.get(0));
        ContentValues content = new ContentValues();
        content.put(DataBaseHelper.SALE_COL_MIN_VALUE, sale.getMin_value());
        content.put(DataBaseHelper.SALE_COL_PASSWORD, sale.getPassword());
        content.put(DataBaseHelper.SALE_COL_STATUS, sale.getStatus());
        content.put(DataBaseHelper.SALE_COL_PROD_ID, sale.getProduct().getId());
        Long result = db.getWritableDatabase().insert(DataBaseHelper.SALE_TABLE_NAME, null, content);
        if(result != -1)
            System.out.println("Insert 1 sale ok +++++++++++++++++++++++++++++++++++");
        else {
            System.out.println("Erro Insert 1 sale xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
            return false;
        }
        sales = saleDao.findAllSale();
        return true;
    }

    private void runTestsUser(){
        System.out.println("runTestsUser #########################################################");
        UserModel user = userDao.findUserByEmail("teste@123");
        if(user.getName().compareTo("teste") == 0)
            System.out.println("NAME OK ++++++++++++++++++++++++++++ " + user.getName());
        if(user.getEmail().compareTo("teste@123") == 0)
            System.out.println("EMAIL OK ++++++++++++++++++++++++++++ " + user.getEmail());
        if(user.getPassword().compareTo("123") == 0)
            System.out.println("PASSWORD OK ++++++++++++++++++++++++++++ " + user.getPassword());
        if(user.getType().compareTo("leiloeiro") == 0)
            System.out.println("TYPE OK ++++++++++++++++++++++++++++ " + user.getType());
        System.out.println("runTestsUser #########################################################");
    }

    private void runTestsProduct(){
        products = productDao.findAllProduct();
        System.out.println("runTestsProduct #########################################################");
        System.out.println("PROD Id: " + products.get(0).getId());
        System.out.println("PROD NAME: " + products.get(0).getProd_name());
        System.out.println("PROD DESCRIPTION: " + products.get(0).getProd_description());
        System.out.println("PROD STATUS: " + products.get(0).getStatus());
        System.out.println("PROD USER EMAIL: " + products.get(0).getSeller().getEmail());

        if(products.get(0).getSeller().getName().compareTo("teste") == 0)
            System.out.println("USER NAME OK ++++++++++++++++++++++++++++ " + products.get(0).getSeller().getName());
        if(products.get(0).getSeller().getEmail().compareTo("teste@123") == 0)
            System.out.println("USER EMAIL OK ++++++++++++++++++++++++++++ " + products.get(0).getSeller().getEmail());
        if(products.get(0).getSeller().getPassword().compareTo("123") == 0)
            System.out.println("USER PASSWORD OK ++++++++++++++++++++++++++++ " + products.get(0).getSeller().getPassword());
        if(products.get(0).getSeller().getType().compareTo("leiloeiro") == 0)
            System.out.println("USER TYPE OK ++++++++++++++++++++++++++++ " + products.get(0).getSeller().getType());

        System.out.println("runTestsProduct #########################################################");
    }

    private void runTestsSale(){
        sales = saleDao.findAllSale();
        System.out.println("runTestsSale #########################################################");
        System.out.println("PRODUCT Id: " + sales.get(0).getProduct().getId());
        System.out.println("PRODUCT NAME: " + sales.get(0).getProduct().getProd_name());
        System.out.println("PRODUCT DESCRIPTION: " + sales.get(0).getProduct().getProd_description());
        System.out.println("PRODUCT STATUS: " + sales.get(0).getProduct().getStatus());
        System.out.println("PRODUCT USER EMAIL: " + sales.get(0).getProduct().getSeller().getEmail());

        System.out.println("SALE ID: " + sales.get(0).getId() + " ????????");
        System.out.println("SALE Min Value: " + sales.get(0).getMin_value() + " ????????");
        System.out.println("SALE Satus: " + sales.get(0).getStatus() + " ????????");
        System.out.println("SALE Password: " + sales.get(0).getPassword() + " ????????");

        System.out.println("runTestsSale #########################################################");
    }

    private void runTestsLance(){
        ProductModel product = productDao.findAllProduct().get(0);
        lances = lanceDao.findAllLanceByProductId(product.getId());
        LanceModel lance = lanceDao.getMaxValue(product.getId());

        System.out.println("runTestsLance #########################################################");
        System.out.println("LANCE Id: " + lances.get(0).getId());
        System.out.println("LANCE VALUE: " + lances.get(0).getValue());
        System.out.println("LANCE USER: " + lances.get(0).getUser().getEmail());
        System.out.println("#########################################################");
        System.out.println("LANCE Id: " + lance.getId());
        System.out.println("LANCE MAX VALUE: " + lance.getValue());
        System.out.println("LANCE USER: " + lance.getUser().getEmail());
        System.out.println(lanceDao.isValidLance(new Double(13.60), product.getId()) + " TRUE or FALSE");
        System.out.println("runTestsLance #########################################################");

    }

    private Boolean insertTestsLance(){
        ProductModel product = productDao.findAllProduct().get(0);
        UserModel user = userDao.findUserByEmail("teste@123");
        LanceModel lance  = new LanceModel(12.5, product, user);
        lanceDao.insert(lance);
        return true;
    }

}
