package com.example.cce_teste11.ileilao;

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
import com.example.cce_teste11.ileilao.Model.ProductModel;
import com.example.cce_teste11.ileilao.Model.SaleModel;

public class EditSaleActivity extends AppCompatActivity {

    TextView prod_name, prod_description, prod_seller, status;
    EditText min_value, password;
    Button save;
    Long prod_id;
    String seller_id;
    Boolean prod_status;
    Intent intent;

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

        prod_name = findViewById(R.id.name);
        prod_name.setText(intent.getStringExtra("prod_name"));

        prod_description = findViewById(R.id.description);
        prod_description.setText(intent.getStringExtra("prod_description"));

        prod_seller = findViewById(R.id.prod_seller);
        prod_seller.setText(intent.getStringExtra("prod_seller"));

        seller_id = intent.getStringExtra("prod_seller");
        prod_status = intent.getBooleanExtra("prod_status", false);

        status = findViewById(R.id.status);
        status.setText(intent.getStringExtra("sale_status"));

        //Min value
        min_value = findViewById(R.id.min_value);
        Double value = intent.getDoubleExtra("sale_min_value", -1);
        min_value.setText(value.toString());

        password = findViewById(R.id.password);
        save = findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                prod_id = intent.getLongExtra("prod_id", -1);
                SaleModel sale = saleDao.findSaleByProdId(prod_id);
                if(sale.getPassword().compareTo(password.getText().toString()) == 0) {
                    sale.setMin_value(Double.parseDouble(min_value.getText().toString()));
                    saleDao.update(sale);
                }
                else
                    Toast.makeText(EditSaleActivity.this, "Senha incorreta", Toast.LENGTH_LONG).show();
            }
        });
    }

}
