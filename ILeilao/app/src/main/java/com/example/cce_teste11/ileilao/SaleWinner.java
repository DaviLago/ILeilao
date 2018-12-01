package com.example.cce_teste11.ileilao;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.cce_teste11.ileilao.DAO.LanceDao;
import com.example.cce_teste11.ileilao.Model.LanceModel;

public class SaleWinner extends AppCompatActivity {

    TextView seller_name, seller_email, lance_value;

    Intent intent;
    LanceDao lanceDao;
    LanceModel lance;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale_winner);

        intent = getIntent();

        lanceDao = new LanceDao(SaleWinner.this);
        lance = lanceDao.getMaxValue(intent.getLongExtra("prod_id", -1));

        if(lance != null) {
            seller_name = findViewById(R.id.seller_name);
            seller_name.setText(lance.getUser().getName());
            seller_email = findViewById(R.id.seller_email);
            seller_email.setText(lance.getUser().getEmail());
            lance_value = findViewById(R.id.lance_value);
            lance_value.setText(lance.getValue().toString());
        }
        else{
            seller_name = findViewById(R.id.seller_name);
            seller_name.setText("Não há lances, portanto o produto não foi leiloado");

            findViewById(R.id.seller_name_title).setVisibility(View.INVISIBLE);
            findViewById(R.id.seller_email).setVisibility(View.INVISIBLE);
            findViewById(R.id.seller_email_title).setVisibility(View.INVISIBLE);
            findViewById(R.id.lance_value).setVisibility(View.INVISIBLE);
            findViewById(R.id.lance_value_title).setVisibility(View.INVISIBLE);


        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        setResult(RESULT_OK, intent);
        finish();
    }
}
