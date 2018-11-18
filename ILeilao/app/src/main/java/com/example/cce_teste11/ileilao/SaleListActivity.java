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
import android.widget.Toast;

import com.example.cce_teste11.ileilao.DAO.SaleDao;
import com.example.cce_teste11.ileilao.Model.SaleModel;
import com.example.cce_teste11.ileilao.RecyclerViewAdapter.SaleListRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SaleListActivity extends AppCompatActivity {

    public static final String TAG = "SaleListActivity";

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
                startActivity(intent);
                return true;
            case R.id.item2:
                Toast.makeText(SaleListActivity.this, "Item 2", Toast.LENGTH_SHORT).show();
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
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(SaleListActivity.this));
    }

}
