package com.example.medical.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medical.R;
import com.example.medical.models.CategoryModel;

import java.util.ArrayList;

public class ShoppingAdapter extends RecyclerView.Adapter<ShoppingAdapter.ViewHolder> {

    Context mContext;
    ArrayList<CategoryModel.Item> itemArrayList;

    public ShoppingAdapter(Context mContext, ArrayList<CategoryModel.Item> itemArrayList) {
        this.mContext = mContext;
        this.itemArrayList = itemArrayList;
    }

    @NonNull
    @Override
    public ShoppingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_shopping, parent, false);
        return new ShoppingAdapter.ViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull ShoppingAdapter.ViewHolder holder, int position) {

        holder.mTextCatName.setText(itemArrayList.get(position).getCatName());

        SubCategoryAdapter mAdapter = new SubCategoryAdapter(mContext,itemArrayList.get(position).getSubCategory());
        holder.mRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 2));
        holder.mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public int getItemCount() {
        return itemArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTextCatName;
        RecyclerView mRecyclerView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mRecyclerView = itemView.findViewById(R.id.recycler_view);
            mTextCatName = itemView.findViewById(R.id.text_category);
        }
    }
}
