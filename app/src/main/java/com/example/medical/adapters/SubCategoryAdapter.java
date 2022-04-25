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

import com.example.medical.ProductListActivity;
import com.example.medical.R;
import com.example.medical.models.CategoryModel;

import java.util.ArrayList;
import java.util.Random;

public class SubCategoryAdapter extends RecyclerView.Adapter<SubCategoryAdapter.ViewHolder> {

    Context mContext;
    ArrayList<CategoryModel.Item.SubCategory> subCategoryArrayList;

    public SubCategoryAdapter(Context mContext, ArrayList<CategoryModel.Item.SubCategory> subCategoryArrayList) {
        this.mContext = mContext;
        this.subCategoryArrayList = subCategoryArrayList;
    }

    @NonNull
    @Override
    public SubCategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_subcategory, parent, false);
        return new SubCategoryAdapter.ViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull SubCategoryAdapter.ViewHolder holder, int position) {
//        int[] androidColors = mContext.getResources().getIntArray(R.array.androidcolors);
//        int randomAndroidColor = androidColors[new Random().nextInt(androidColors.length)];
//
//        holder.mView.setBackgroundColor(randomAndroidColor);

        holder.mTextSubCategory.setText(subCategoryArrayList.get(position).getSubCat());
        holder.mView.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, ProductListActivity.class);
            intent.putExtra("sub_cat", subCategoryArrayList.get(position).getSubCat());
            intent.putExtra("cat_name", subCategoryArrayList.get(position).getCatName());
            mContext.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return subCategoryArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        CardView mView;
        TextView mTextSubCategory;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mView = itemView.findViewById(R.id.view);
            mTextSubCategory = itemView.findViewById(R.id.text_sub_category);
        }
    }
}
