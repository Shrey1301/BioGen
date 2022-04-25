package com.example.medical.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medical.ProductDetailActivity;
import com.example.medical.R;
import com.example.medical.models.HomeModel;
import com.example.medical.models.ProductModel;

import java.util.ArrayList;

public class FeaturedAdapter extends RecyclerView.Adapter<FeaturedAdapter.ViewHolder> {

    Context mContext;
    ArrayList<ProductModel.Item> items;

    public FeaturedAdapter(Context mContext, ArrayList<ProductModel.Item> mArray) {
        this.mContext = mContext;
        this.items = mArray;
    }

    @NonNull
    @Override
    public FeaturedAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_home,parent, false);
        return new FeaturedAdapter.ViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull FeaturedAdapter.ViewHolder holder, int position) {
        ProductModel.Item model = items.get(position);

        holder.mTextItemName.setText(model.getName());
        holder.mTextItemDesc.setText(model.getProductDesc());
        holder.mTextItemPrice.setText(model.getProductPrice());

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
        TextView mTextItemName, mTextItemDesc, mTextItemPrice;
        CardView mView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextItemName = itemView.findViewById(R.id.item_name);
            mTextItemDesc = itemView.findViewById(R.id.item_desc);
            mTextItemPrice = itemView.findViewById(R.id.item_price);

            mView = itemView.findViewById(R.id.view);
        }
    }
}
