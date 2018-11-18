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

import com.example.cce_teste11.ileilao.Model.SaleModel;
import com.example.cce_teste11.ileilao.R;
import com.example.cce_teste11.ileilao.SaleDetailActivity;

import java.util.List;

public class SaleListRecyclerView extends RecyclerView.Adapter<SaleListRecyclerView.ViewHolder> {

    public static final String TAG = "SaleListRecyclerVie";

    private List<SaleModel> sales;
    private Context context;

    public SaleListRecyclerView(Context context, List<SaleModel> sales) {
        Log.d(TAG, "ProductListRecyclerView: constructor.");
        this.context = context;
        this.sales = sales;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Log.d(TAG, "onCreateViewHolder: called.");
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_sale_list, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Log.d(TAG, "onBindViewHolder: called.");
        viewHolder.name.setText(sales.get(i).getProduct().getProd_name());
        viewHolder.description.setText(sales.get(i).getProduct().getProd_description());
        viewHolder.min_value.setText(sales.get(i).getMin_value().toString());

        setSaleOnClickListener(viewHolder, i);
    }

    private void setSaleOnClickListener(@NonNull SaleListRecyclerView.ViewHolder viewHolder, final int i) {
        viewHolder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SaleDetailActivity.class);
                intent.putExtra("prod_id", sales.get(i).getProduct().getId());
                intent.putExtra("prod_name", sales.get(i).getProduct().getProd_name());
                intent.putExtra("prod_description", sales.get(i).getProduct().getProd_description());
                intent.putExtra("prod_seller", sales.get(i).getProduct().getSeller().getEmail());
                intent.putExtra("prod_status", sales.get(i).getProduct().getStatus());
                intent.putExtra("sale_status", sales.get(i).getStatus());
                intent.putExtra("sale_min_value", sales.get(i).getMin_value());
                intent.putExtra("sale_id", sales.get(i).getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return sales.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout parentLayout;
        TextView name, description, min_value;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Log.d(TAG, "ViewHolder: constructor");
            name = itemView.findViewById(R.id.name);
            description = itemView.findViewById(R.id.description);
            min_value = itemView.findViewById(R.id.min_value);
            parentLayout = itemView.findViewById(R.id.parentLayout);

        }
    }


}
