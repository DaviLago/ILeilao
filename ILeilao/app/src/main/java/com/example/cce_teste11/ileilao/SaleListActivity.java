package com.example.cce_teste11.ileilao;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.cce_teste11.ileilao.DAO.SaleDao;
import com.example.cce_teste11.ileilao.Interface.ItemClickListener;
import com.example.cce_teste11.ileilao.Model.SaleModel;
import com.example.cce_teste11.ileilao.RecyclerViewAdapter.SaleListRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SaleListActivity extends AppCompatActivity {

    public static final String TAG = "SaleListActivity";
    private static final int CODE_REQUEST_RELOAD = 200;

    private Toolbar toolbar;
    private List<SaleModel> sales = new ArrayList<>();
    private SaleDao saleDao;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: started.");
        setContentView(R.layout.activity_sale_list);
        //DAO
        saleDao = new SaleDao(SaleListActivity.this);
        //Toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //Recycler view
        recyclerView = findViewById(R.id.recycler_view);
        //List of sales
        getSales();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_itens_toolbar_sale_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item1:
                Intent intent = new Intent(SaleListActivity.this, ProductListActivity.class);
                startActivityForResult(intent, CODE_REQUEST_RELOAD);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void getSales(){
        sales = saleDao.findAllSale();
        initRecyclerView();
    }

    private void initRecyclerView(){
        Log.d(TAG, "initRecyclerView: init recyclerView.");
        SaleListRecyclerView adapter = new SaleListRecyclerView(SaleListActivity.this, sales);
        adapter.setOnClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Log.d(TAG, "Elemento " + position + " clicado. +++++++++++++++++++++++++");
                Intent intent = new Intent(SaleListActivity.this, SaleDetailActivity.class);
                intent.putExtra("prod_id", sales.get(position).getProduct().getId());
                intent.putExtra("prod_name", sales.get(position).getProduct().getProd_name());
                intent.putExtra("prod_description", sales.get(position).getProduct().getProd_description());
                intent.putExtra("prod_seller", sales.get(position).getProduct().getSeller().getEmail());
                intent.putExtra("prod_status", sales.get(position).getProduct().getStatus());
                intent.putExtra("sale_status", sales.get(position).getStatus());
                intent.putExtra("sale_min_value", sales.get(position).getMin_value());
                intent.putExtra("sale_id", sales.get(position).getId());
                startActivityForResult(intent, CODE_REQUEST_RELOAD);
            }
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(SaleListActivity.this));

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CODE_REQUEST_RELOAD){
            if(resultCode == RESULT_CANCELED){
                getSales();
            }
        }
    }

}
