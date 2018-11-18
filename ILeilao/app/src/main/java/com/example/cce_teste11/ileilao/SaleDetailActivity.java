package com.example.cce_teste11.ileilao;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.cce_teste11.ileilao.Model.UserModel;

public class SaleDetailActivity extends AppCompatActivity {

    private Toolbar toolbar;

    TextView prod_name, prod_description, prod_seller, status, sale, min_value;
    Long prod_id, sale_id;
    Boolean prod_status;
    Button edit_btn;
    Intent intent;
    Double min;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale_detail);

        //Toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Intent
        intent = getIntent();
        //Product Id
        prod_id = intent.getLongExtra("prod_id", -1);

        //Name
        prod_name = findViewById(R.id.name);
        prod_name.setText(intent.getStringExtra("prod_name"));

        //Description
        prod_description = findViewById(R.id.description);
        prod_description.setText(intent.getStringExtra("prod_description"));

        //Seller
        prod_seller = findViewById(R.id.prod_seller);
        prod_seller.setText(intent.getStringExtra("prod_seller"));

        //Min value
        min_value = findViewById(R.id.min_value);
        min = intent.getDoubleExtra("sale_min_value", 0);
        if(min.compareTo(new Double(0)) == 0 )
            min_value.setText("R$ Indefinido");
        else
            min_value.setText("R$ " + min);

        //Status
        status = findViewById(R.id.status);
        prod_status = intent.getBooleanExtra("prod_status", false);
        sale_id = intent.getLongExtra("sale_id", -1);
        if(sale_id.compareTo(-1L) == 0 && prod_status)
            status.setText("Disponível para leilão");
        else if(sale_id.compareTo(-1L) != 0 && prod_status)
            status.setText("Leiloando");
        else
            status.setText("Leiloado");

        edit_btn = findViewById(R.id.edit_btn);
        if(UserModel.getLogged_user().getType().compareTo("leiloeiro") == 0)
            edit_btn.setVisibility(View.VISIBLE);
        else
            edit_btn.setVisibility(View.INVISIBLE);

        edit_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intentEdit = new Intent(SaleDetailActivity.this, EditSaleActivity.class);
                intentEdit.putExtra("prod_name", prod_name.getText().toString());
                intentEdit.putExtra("prod_description", prod_description.getText().toString());
                intentEdit.putExtra("prod_seller", prod_seller.getText().toString());
                intentEdit.putExtra("sale_status", status.getText().toString());
                intentEdit.putExtra("sale_min_value", min);
                intentEdit.putExtra("prod_id", prod_id);
                startActivity(intentEdit);
            }
        });
    }
}
