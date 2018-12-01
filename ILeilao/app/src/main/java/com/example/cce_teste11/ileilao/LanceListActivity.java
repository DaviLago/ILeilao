package com.example.cce_teste11.ileilao;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.cce_teste11.ileilao.DAO.LanceDao;
import com.example.cce_teste11.ileilao.Model.LanceModel;
import com.example.cce_teste11.ileilao.RecyclerViewAdapter.LanceListRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class LanceListActivity extends AppCompatActivity {

    public static final String TAG = "SaleListActivity";

    private List<LanceModel> lances = new ArrayList<>();
    private LanceDao lanceDao;
    private RecyclerView recyclerView;
    private Intent intent;
    TextView lance_info;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: started.");
        setContentView(R.layout.activity_lance_list);
        intent = getIntent();
        //DAO
        lanceDao = new LanceDao(LanceListActivity.this);
        //Recycler view
        recyclerView = findViewById(R.id.recycler_view);
        //Info caso não haja lances
        lance_info = findViewById(R.id.lance_info);
        //List of sales
        getLances();
    }

    private void getLances(){

        lances = lanceDao.findAllLanceByProductId(intent.getLongExtra("prod_id", -1));
        if(lances.size() == 0) {
            lance_info.setText("Não há lances...");
        }
        else{
            lance_info.setVisibility(View.INVISIBLE);
        }
        initRecyclerView();
    }

    private void initRecyclerView(){
        Log.d(TAG, "initRecyclerView: init recyclerView.");
        LanceListRecyclerView adapter = new LanceListRecyclerView(LanceListActivity.this, lances);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(LanceListActivity.this));
    }

}
