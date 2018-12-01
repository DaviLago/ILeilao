package com.example.cce_teste11.ileilao.RecyclerViewAdapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.cce_teste11.ileilao.DAO.SaleDao;
import com.example.cce_teste11.ileilao.Interface.ItemClickListener;
import com.example.cce_teste11.ileilao.Model.ProductModel;
import com.example.cce_teste11.ileilao.R;

import java.util.List;

public class ProductListRecyclerView extends RecyclerView.Adapter<ProductListRecyclerView.ViewHolder> {

    private static final String TAG = "ProductListRecyclerView";

    private List<ProductModel> products;
    private Context context;
    private SaleDao saleDao;
    private ItemClickListener itemClickListener;

    public ProductListRecyclerView(Context context, List<ProductModel> products) {
        Log.d(TAG, "ProductListRecyclerView: constructor.");
        this.context = context;
        this.products = products;
        saleDao = new SaleDao(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Log.d(TAG, "onCreateViewHolder: called.");
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_product_list, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Log.d(TAG, "onBindViewHolder: called.");
        viewHolder.name.setText(products.get(i).getProd_name());
        viewHolder.description.setText(products.get(i).getProd_description());
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public void setOnClickListener(ItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        RelativeLayout parentLayout;
        TextView name, description;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Log.d(TAG, "ViewHolder: constructor");
            name = itemView.findViewById(R.id.name);
            description = itemView.findViewById(R.id.description);
            parentLayout = itemView.findViewById(R.id.parentLayout);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(itemClickListener != null){
                itemClickListener.onItemClick(getAdapterPosition());
            }
        }
    }

}
