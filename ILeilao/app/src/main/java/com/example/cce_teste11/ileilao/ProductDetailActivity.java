package com.example.cce_teste11.ileilao;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.cce_teste11.ileilao.Model.UserModel;

public class ProductDetailActivity extends AppCompatActivity {

    private static final int CODE_REQUEST_EDIT = 1;
    private static final int CODE_REQUEST_SALE = 2;

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
                Intent intentSale = intent;
                intentSale.setClass(ProductDetailActivity.this, CreateSaleActivity.class);
                startActivityForResult(intentSale, CODE_REQUEST_SALE);
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
        if(prod_status && (UserModel.getLogged_user().getEmail().compareTo(intent.getStringExtra("prod_seller")) == 0 || UserModel.getLogged_user().getType().compareTo("leiloeiro") == 0))
            edit_btn.setVisibility(View.VISIBLE);
        else
            edit_btn.setVisibility(View.INVISIBLE);

        edit_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intentEdit = intent;
                intentEdit.setClass(ProductDetailActivity.this, EditProdActivity.class);
                startActivityForResult(intentEdit, CODE_REQUEST_EDIT);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CODE_REQUEST_EDIT){
            if(resultCode == RESULT_OK){
                prod_name.setText(data.getStringExtra("prod_name"));
                intent.putExtra("prod_name", data.getStringExtra("prod_name"));
                prod_description.setText(data.getStringExtra("prod_description"));
                intent.putExtra("prod_description", data.getStringExtra("prod_description"));
                prod_seller.setText(data.getStringExtra("prod_seller"));
                intent.putExtra("prod_seller", data.getStringExtra("prod_seller"));
            }
        }
        else if(requestCode == CODE_REQUEST_SALE){
            if(resultCode == RESULT_OK){
                status.setText("Leiloando");
                sale.setVisibility(View.INVISIBLE);
            }
        }

    }
}
