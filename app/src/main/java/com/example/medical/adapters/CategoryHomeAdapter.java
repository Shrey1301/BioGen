package com.example.medical.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medical.R;
import com.example.medical.SubcategoryActivity;
import com.example.medical.models.CategoryModel;

import java.util.ArrayList;

public class CategoryHomeAdapter extends RecyclerView.Adapter<CategoryHomeAdapter.ViewHolder> {

    Context mContext;
    ArrayList<CategoryModel.Item> itemArrayList;

    public CategoryHomeAdapter(Context mContext, ArrayList<CategoryModel.Item> itemArrayList) {
        this.mContext = mContext;
        this.itemArrayList = itemArrayList;
    }

    @NonNull
    @Override
    public CategoryHomeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_home_category, parent, false);
        return new CategoryHomeAdapter.ViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryHomeAdapter.ViewHolder holder, int position) {

        holder.mTextCatName.setText(itemArrayList.get(position).getCatName());
        holder.mView.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, SubcategoryActivity.class);
            intent.putExtra("sub_cat_array", itemArrayList.get(position).getSubCategory());
            mContext.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return itemArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout mView;
        TextView mTextCatName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView.findViewById(R.id.view);
            mTextCatName = itemView.findViewById(R.id.category_name);
        }
    }
}
