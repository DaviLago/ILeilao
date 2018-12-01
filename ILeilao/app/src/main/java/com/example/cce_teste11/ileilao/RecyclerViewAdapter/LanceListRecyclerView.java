package com.example.cce_teste11.ileilao.RecyclerViewAdapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.cce_teste11.ileilao.Model.LanceModel;
import com.example.cce_teste11.ileilao.Model.SaleModel;
import com.example.cce_teste11.ileilao.R;
import com.example.cce_teste11.ileilao.SaleDetailActivity;

import java.util.List;

public class LanceListRecyclerView extends RecyclerView.Adapter<LanceListRecyclerView.ViewHolder> {

    public static final String TAG = "LanceListRecyclerView";

    private List<LanceModel> lances;
    private Context context;

    public LanceListRecyclerView(Context context, List<LanceModel> lances) {
        Log.d(TAG, "ProductListRecyclerView: constructor.");
        this.context = context;
        this.lances = lances;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Log.d(TAG, "onCreateViewHolder: called.");
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_lance_list, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Log.d(TAG, "onBindViewHolder: called.");
        viewHolder.name.setText(lances.get(i).getUser().getEmail());
        viewHolder.value.setText(lances.get(i).getValue().toString());
    }

    @Override
    public int getItemCount() {
        return lances.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout parentLayout;
        TextView name, value;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Log.d(TAG, "ViewHolder: constructor");
            name = itemView.findViewById(R.id.name);
            value = itemView.findViewById(R.id.value);
            parentLayout = itemView.findViewById(R.id.parentLayout);

        }
    }


}
