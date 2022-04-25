package com.example.medical.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medical.R;
import com.example.medical.models.CartModel;

import java.util.ArrayList;

public class MyCartOrWishListAdapter extends RecyclerView.Adapter<MyCartOrWishListAdapter.ViewHolder> {

    Context mContext;
    ArrayList<CartModel.Item> itemArrayList;

    public MyCartOrWishListAdapter(Context mContext, ArrayList<CartModel.Item> itemArrayList) {
        this.mContext = mContext;
        this.itemArrayList = itemArrayList;
    }

    @NonNull
    @Override
    public MyCartOrWishListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_cart, parent, false);
        return new MyCartOrWishListAdapter.ViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyCartOrWishListAdapter.ViewHolder holder, int position) {

        /**
         * Demo Code For Image Loading With Disk Image Caching
         */
        /*Glide.with(mContext)
                .load(itemArrayList.get(position).getProductImage())
                .apply(new RequestOptions()
                .placeholder(R.drawable.demo_item)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC))
                .into(holder.mImageProduct);*/

        holder.mTextItemName.setText(itemArrayList.get(position).getName());
        holder.mTextPrice.setText(itemArrayList.get(position).getProductPrice());

        holder.mImageRemove.setOnClickListener(v -> {
            removeItem(itemArrayList.get(position), holder.getAdapterPosition());
        });

    }

    protected void removeItem(CartModel.Item item, int adapterPosition) {
    }

    @Override
    public int getItemCount() {
        return itemArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView mImageProduct, mImageRemove;
        TextView mTextItemName, mTextPrice;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mImageProduct = itemView.findViewById(R.id.item_image);
            mImageRemove = itemView.findViewById(R.id.item_remove);
            mTextItemName = itemView.findViewById(R.id.item_name);
            mTextPrice = itemView.findViewById(R.id.item_price);
        }
    }
}
