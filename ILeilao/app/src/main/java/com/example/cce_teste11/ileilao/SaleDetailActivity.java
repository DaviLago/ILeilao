package com.example.cce_teste11.ileilao;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cce_teste11.ileilao.DAO.LanceDao;
import com.example.cce_teste11.ileilao.DAO.ProductDao;
import com.example.cce_teste11.ileilao.DAO.SaleDao;
import com.example.cce_teste11.ileilao.Model.LanceModel;
import com.example.cce_teste11.ileilao.Model.ProductModel;
import com.example.cce_teste11.ileilao.Model.SaleModel;
import com.example.cce_teste11.ileilao.Model.UserModel;

public class SaleDetailActivity extends AppCompatActivity {

    private static final int CODE_REQUEST_EDIT = 1;
    private static final int CODE_REQUEST_RELOAD = 200;

    private Toolbar toolbar;

    TextView prod_name, prod_description, prod_seller, status, sale, min_value, lance_view, maior_lance;
    EditText lance;
    TextInputLayout lance_title;
    Long prod_id, sale_id;
    Boolean prod_status;
    Button edit_btn, lance_btn;
    Intent intent, intentWinner;
    Double min;

    LanceDao lanceDao;
    SaleDao saleDao;
    ProductDao productDao;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale_detail);

        lanceDao = new LanceDao(SaleDetailActivity.this);
        saleDao = new SaleDao(SaleDetailActivity.this);
        productDao = new ProductDao(SaleDetailActivity.this);

        //Toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Intent
        intent = getIntent();
        //Product Id
        prod_id = intent.getLongExtra("prod_id", -1);

        prod_name = findViewById(R.id.name);
        prod_description = findViewById(R.id.description);
        prod_seller = findViewById(R.id.prod_seller);
        min_value = findViewById(R.id.min_value);
        status = findViewById(R.id.status);
        edit_btn = findViewById(R.id.edit_btn);
        maior_lance = findViewById(R.id.maior_lance);
        lance = findViewById(R.id.lance);
        lance_btn = findViewById(R.id.lance_btn);
        lance_title = findViewById(R.id.lance_title);
        lance_view = findViewById(R.id.lance_view);

        create();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CODE_REQUEST_EDIT){
            if(resultCode == RESULT_OK)
                min_value.setText("R$ " + data.getDoubleExtra("sale_min_value", -1));
        }
        else if(requestCode == CODE_REQUEST_RELOAD){
            if(resultCode == RESULT_CANCELED){
                //reload
                ProductModel product = productDao.findProductById(prod_id);
                SaleModel sale = saleDao.findSaleByProdId(prod_id);
                intent.putExtra("prod_name", product.getProd_name());
                intent.putExtra("prod_description", product.getProd_description());
                intent.putExtra("prod_seller", product.getSeller().getEmail());
                intent.putExtra("prod_status", product.getStatus());
                intent.putExtra("sale_status", sale.getStatus());
                intent.putExtra("sale_min_value", sale.getMin_value());
                intent.putExtra("sale_id", sale.getId());
                create();
            }
        }

    }

    private void create(){
        //Name
        prod_name.setText(intent.getStringExtra("prod_name"));

        //Description
        prod_description.setText(intent.getStringExtra("prod_description"));

        //Seller
        prod_seller.setText(intent.getStringExtra("prod_seller"));

        //Min value
        min = intent.getDoubleExtra("sale_min_value", 0);
        if(min.compareTo(new Double(0)) == 0 )
            min_value.setText("R$ Indefinido");
        else
            min_value.setText("R$ " + min);

        //Status
        prod_status = intent.getBooleanExtra("prod_status", false);
        sale_id = intent.getLongExtra("sale_id", -1);
        if(sale_id.compareTo(-1L) != 0 && prod_status)
            status.setText("Leiloando");
        else {
            status.setText("Leiloado");
        }

        if(UserModel.getLogged_user().getType().compareTo(UserModel.LEILOEIRO) == 0 && prod_status) {
            edit_btn.setVisibility(View.VISIBLE);
        }
        else{
            edit_btn.setVisibility(View.INVISIBLE);
        }

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
                startActivityForResult(intentEdit, CODE_REQUEST_RELOAD);
            }
        });


        if(lanceDao.findAllLanceByProductId(prod_id).isEmpty())
            maior_lance.setVisibility(View.INVISIBLE);
        else
            maior_lance.setVisibility(View.VISIBLE);
        maior_lance.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                intentWinner = intent;
                intentWinner.setClass(SaleDetailActivity.this, SaleWinner.class);
                startActivity(intentWinner);
            }
        });

        if(UserModel.getLogged_user().getType().compareTo(UserModel.LEILOEIRO) == 0 || !prod_status){
            lance.setVisibility(View.INVISIBLE);
            lance_btn.setVisibility(View.INVISIBLE);
            lance_title.setVisibility(View.INVISIBLE);
        }
        else {
            lance.setVisibility(View.VISIBLE);
            lance_btn.setVisibility(View.VISIBLE);
            lance_title.setVisibility(View.VISIBLE);
            lance_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Double value = Double.parseDouble(lance.getText().toString().isEmpty() ? "0" : lance.getText().toString());
                    if(value.compareTo(new Double(0)) == 0)
                        Toast.makeText(SaleDetailActivity.this, "Digite um valor", Toast.LENGTH_SHORT).show();
                    else {
                        UserModel user = UserModel.getLogged_user();
                        ProductModel product = new ProductModel(prod_id);

                        if (value < min)
                            Toast.makeText(SaleDetailActivity.this, "Valor inferior ou igual ao valor mínimo", Toast.LENGTH_SHORT).show();
                        else if (lanceDao.isValidLance(value, prod_id)){
                            Boolean result = lanceDao.insert(new LanceModel(value, product, user));
                            if (result.compareTo(true) == 0) {
                                Toast.makeText(SaleDetailActivity.this, "Lance aceito", Toast.LENGTH_SHORT).show();
                                maior_lance.setVisibility(View.VISIBLE);
                            }
                            else
                                Toast.makeText(SaleDetailActivity.this, "Erro ao efetuar lance", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(SaleDetailActivity.this, "Lance inferior ou igual ao maior lance atual", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
        }

        lance_view.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SaleDetailActivity.this, LanceListActivity.class);
                intent.putExtra("prod_id", prod_id);
                startActivity(intent);
            }
        });
    }

}
