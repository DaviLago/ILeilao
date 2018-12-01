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
import android.view.View;
import android.widget.Button;

import com.example.cce_teste11.ileilao.DAO.ProductDao;
import com.example.cce_teste11.ileilao.DAO.SaleDao;
import com.example.cce_teste11.ileilao.Interface.ItemClickListener;
import com.example.cce_teste11.ileilao.Model.ProductModel;
import com.example.cce_teste11.ileilao.Model.SaleModel;
import com.example.cce_teste11.ileilao.RecyclerViewAdapter.ProductListRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ProductListActivity extends AppCompatActivity {

    private static final String TAG = "ProductListActivity";
    private static final int CODE_REQUEST_RELOAD = 200;

    private ProductDao productDao;
    private SaleDao saleDao;
    private Toolbar toolbar;
    private List<ProductModel> products = new ArrayList<>();
    RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: started.");
        setContentView(R.layout.activity_product_list);
        //DAO
        productDao = new ProductDao(ProductListActivity.this);
        saleDao = new SaleDao(ProductListActivity.this);
        //Toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //Recycler view
        recyclerView = findViewById(R.id.recycler_view);
        //List of products
        getProducts();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_itens_toolbar_product_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.item1:
                Intent intent = new Intent(ProductListActivity.this, CreateProdActivity.class);
                startActivityForResult(intent, CODE_REQUEST_RELOAD);
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    private void getProducts(){
        products = productDao.findAllProduct();
        initRecyclerView();
    }

    private void initRecyclerView(){
        Log.d(TAG, "initRecyclerView: init recyclerView.");
        ProductListRecyclerView adapter = new ProductListRecyclerView(ProductListActivity.this, products);
        adapter.setOnClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Log.d(TAG, "Elemento " + position + " clicado. +++++++++++++++++++++++++");
                Intent intent = new Intent(ProductListActivity.this, ProductDetailActivity.class);
                intent.putExtra("prod_id", products.get(position).getId());
                intent.putExtra("prod_name", products.get(position).getProd_name());
                intent.putExtra("prod_description", products.get(position).getProd_description());
                intent.putExtra("prod_seller", products.get(position).getSeller().getEmail());
                intent.putExtra("prod_status", products.get(position).getStatus());

                SaleModel sale = saleDao.findSaleByProdId(products.get(position).getId());
                if(sale != null)
                    intent.putExtra("sale_id", sale.getId());
                startActivityForResult(intent, CODE_REQUEST_RELOAD);
            }
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(ProductListActivity.this));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CODE_REQUEST_RELOAD){
            if(resultCode == RESULT_CANCELED){
                getProducts();
            }
        }
    }

}
