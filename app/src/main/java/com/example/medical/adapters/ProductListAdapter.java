package com.example.medical.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medical.ProductDetailActivity;
import com.example.medical.R;
import com.example.medical.models.ProductModel;

import java.util.ArrayList;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ViewHolder> {

    Context mContext;
    ArrayList<ProductModel.Item> items;

    public ProductListAdapter(Context mContext, ArrayList<ProductModel.Item> items) {
        this.mContext = mContext;
        this.items = items;
    }

    @NonNull
    @Override
    public ProductListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_prductlist, parent, false);
        return new ProductListAdapter.ViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductListAdapter.ViewHolder holder, int position) {

        holder.mTextItemName.setText(items.get(position).getName());
        holder.mTextDesc.setText(items.get(position).getProductDesc());
        holder.mTextPrice.setText(items.get(position).getProductPrice());


        holder.mView.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, ProductDetailActivity.class);
            intent.putExtra("model", items.get(position));
            mContext.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CardView mView;
        ImageView mImageProduct;
        TextView mTextItemName, mTextDesc, mTextPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mView = itemView.findViewById(R.id.view);
            mImageProduct = itemView.findViewById(R.id.item_image);
            mTextItemName = itemView.findViewById(R.id.item_name);
            mTextDesc = itemView.findViewById(R.id.item_desc);
            mTextPrice = itemView.findViewById(R.id.item_price);
        }
    }
}
