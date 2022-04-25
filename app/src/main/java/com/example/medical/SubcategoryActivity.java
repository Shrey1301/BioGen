package com.example.medical;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.medical.adapters.SubCategoryAdapter;
import com.example.medical.models.CategoryModel;

import java.util.ArrayList;

public class SubcategoryActivity extends AppCompatActivity {

    ImageView mImageBack;
    TextView mTextToolbarTitle;
    RecyclerView mRecyclerView;
    Activity mActivity;
    SubCategoryAdapter mAdapter;
    ArrayList<CategoryModel.Item.SubCategory> subCategoryArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subcategory);

        Window window = this.getWindow();

        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.white));

        mActivity = SubcategoryActivity.this;
        mDeclaration();
    }

    private void mDeclaration() {

        Intent intent = getIntent();
        subCategoryArrayList = (ArrayList<CategoryModel.Item.SubCategory>) intent.getSerializableExtra("sub_cat_array");

        mImageBack = findViewById(R.id.icon_back);
        mTextToolbarTitle = findViewById(R.id.toolbar_title);
        mRecyclerView = findViewById(R.id.recycler_view);

        mAdapter = new SubCategoryAdapter(mActivity, subCategoryArrayList);
        mRecyclerView.setLayoutManager(new GridLayoutManager(mActivity, 2));
        mRecyclerView.setAdapter(mAdapter);

        mTextToolbarTitle.setText("Categories");
        mImageBack.setOnClickListener(v -> onBackPressed());
    }
}