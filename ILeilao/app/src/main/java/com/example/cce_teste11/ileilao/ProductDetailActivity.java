package com.example.cce_teste11.ileilao;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.cce_teste11.ileilao.DataBaseSQLite.DataBaseHelper;
import com.example.cce_teste11.ileilao.Model.UserModel;

public class ProductDetailActivity extends AppCompatActivity {

    TextView prod_name, prod_description, prod_seller, status, sale;
    Long prod_id, sale_id;
    Boolean prod_status;
    Button edit_btn;
    Intent intent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        intent = getIntent();
        prod_id = intent.getLongExtra("prod_id", -1);

        prod_name = findViewById(R.id.name);
        prod_name.setText(intent.getStringExtra("prod_name"));

        prod_description = findViewById(R.id.description);
        prod_description.setText(intent.getStringExtra("prod_description"));

        prod_seller = findViewById(R.id.prod_seller);
        prod_seller.setText(intent.getStringExtra("prod_seller"));

        sale = findViewById(R.id.sale);
        sale.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                intent.setClass(ProductDetailActivity.this, CreateSaleActivity.class);
                startActivity(intent);
            }
        });


        status = findViewById(R.id.status);
        prod_status = intent.getBooleanExtra("prod_status", false);
        sale_id = intent.getLongExtra("sale_id", -1);
        if(sale_id.compareTo(-1L) == 0 && prod_status) {
            status.setText("Disponível para leilão");
            if(UserModel.getLogged_user().getType().compareTo(UserModel.PARTICIPANTE) == 0)
                sale.setVisibility(View.INVISIBLE);
            else
                sale.setVisibility(View.VISIBLE);
        }
        else if(sale_id.compareTo(-1L) != 0 && prod_status) {
            status.setText("Leiloando");
            sale.setVisibility(View.INVISIBLE);
        }
        else {
            status.setText("Leiloado");
            sale.setVisibility(View.INVISIBLE);
        }

        edit_btn = findViewById(R.id.edit_btn);
        if(UserModel.getLogged_user().getEmail().compareTo(intent.getStringExtra("prod_seller")) == 0)
            edit_btn.setVisibility(View.VISIBLE);
        else
            edit_btn.setVisibility(View.INVISIBLE);

        edit_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //TODO
            }
        });



    }

}
