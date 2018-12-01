package com.example.cce_teste11.ileilao;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cce_teste11.ileilao.DAO.ProductDao;
import com.example.cce_teste11.ileilao.DAO.SaleDao;
import com.example.cce_teste11.ileilao.Model.SaleModel;

public class EditSaleActivity extends AppCompatActivity {

    private static final int CODE_REQUEST_RELOAD = 200;

    TextView prod_name, prod_description, prod_seller, status;
    EditText min_value, password;
    Button save, close_btn;
    Long prod_id;
    String seller_id;
    Boolean prod_status;
    Intent intent, intentWinner;

    SaleDao saleDao;
    ProductDao productDao;

    SaleModel sale;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_sale);

        saleDao = new SaleDao(EditSaleActivity.this);
        productDao = new ProductDao(EditSaleActivity.this);

        intent = getIntent();

        prod_id = intent.getLongExtra("prod_id", -1);
        prod_name = findViewById(R.id.name);
        prod_description = findViewById(R.id.description);
        prod_seller = findViewById(R.id.prod_seller);
        seller_id = intent.getStringExtra("prod_seller");
        status = findViewById(R.id.status);

        //Min value
        min_value = findViewById(R.id.min_value);

        password = findViewById(R.id.password);
        save = findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                SaleModel sale = saleDao.findSaleByProdId(prod_id);
                if(sale.getPassword().compareTo(password.getText().toString()) == 0) {
                    sale.setMin_value(Double.parseDouble(min_value.getText().toString()));
                    saleDao.update(sale);
                    Intent intent = new Intent();
                    intent.putExtra("sale_min_value", sale.getMin_value());
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                }
                else
                    Toast.makeText(EditSaleActivity.this, "Senha incorreta", Toast.LENGTH_LONG).show();
            }
        });

        close_btn = findViewById(R.id.close);
        close_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                SaleModel sale = saleDao.findSaleByProdId(prod_id);
                if(sale.getPassword().compareTo(password.getText().toString()) == 0) {
                    saleDao.encerrar(sale.getId());
                    productDao.encerrar(prod_id);
                    intentWinner = intent;
                    intentWinner.setClass(EditSaleActivity.this, SaleWinner.class);
                    startActivityForResult(intentWinner, CODE_REQUEST_RELOAD);
                }
                else
                    Toast.makeText(EditSaleActivity.this, "Senha incorreta", Toast.LENGTH_LONG).show();
            }
        });

        create();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CODE_REQUEST_RELOAD){
            if(resultCode == RESULT_CANCELED){
                finish();
            }
        }
    }

    private void create(){
        prod_name.setText(intent.getStringExtra("prod_name"));
        prod_description.setText(intent.getStringExtra("prod_description"));
        prod_seller.setText(intent.getStringExtra("prod_seller"));
        prod_status = intent.getBooleanExtra("prod_status", false);
        status.setText(intent.getStringExtra("sale_status"));
        Double value = intent.getDoubleExtra("sale_min_value", -1);
        min_value.setText(value.toString());
    }

}
