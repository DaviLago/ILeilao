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
import com.example.cce_teste11.ileilao.Model.ProductModel;
import com.example.cce_teste11.ileilao.Model.SaleModel;

public class CreateSaleActivity extends AppCompatActivity {

    TextView prod_name, prod_description, prod_seller, status;
    EditText min_value, password;
    Button save;
    Long prod_id, sale_id;
    String seller_id;
    Boolean prod_status;
    Intent intent;

    SaleDao saleDao;
    ProductDao productDao;

    SaleModel sale;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_sale);

        saleDao = new SaleDao(CreateSaleActivity.this);
        productDao = new ProductDao(CreateSaleActivity.this);

        intent = getIntent();
        prod_id = intent.getLongExtra("prod_id", -1);

        prod_name = findViewById(R.id.name);
        prod_name.setText(intent.getStringExtra("prod_name"));

        prod_description = findViewById(R.id.description);
        prod_description.setText(intent.getStringExtra("prod_description"));

        prod_seller = findViewById(R.id.prod_seller);
        prod_seller.setText(intent.getStringExtra("prod_seller"));

        seller_id = intent.getStringExtra("prod_seller");
        prod_status = intent.getBooleanExtra("prod_status", false);

        status = findViewById(R.id.status);
        prod_status = intent.getBooleanExtra("prod_status", false);
        sale_id = intent.getLongExtra("sale_id", -1);
        if(sale_id.compareTo(-1L) == 0 && prod_status)
            status.setText("Disponível para leilão");
        else if(sale_id.compareTo(-1L) != 0 && prod_status)
            status.setText("Leiloando");
        else
            status.setText("Leiloado");

        min_value = findViewById(R.id.min_value);
        password = findViewById(R.id.password);
        save = findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(min_value.getText().toString().isEmpty() || password.getText().toString().isEmpty()){
                    Toast.makeText(CreateSaleActivity.this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
                }
                else{
                    sale = new SaleModel(Double.parseDouble(min_value.getText().toString()), password.getText().toString(), new Boolean(true), new ProductModel(prod_id));
                    Boolean result = saleDao.insertSale(sale);
                    if (result) {
                        Toast.makeText(CreateSaleActivity.this, "Leilão iniciado", Toast.LENGTH_SHORT).show();
                        setResult(Activity.RESULT_OK, intent);
                        finish();
                    } else
                        Toast.makeText(CreateSaleActivity.this, "Erro ao criar leilão", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
