package com.example.medical;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.medical.adapters.ProductListAdapter;
import com.example.medical.helper.SharedPref;
import com.example.medical.helpers.APIClient;
import com.example.medical.helpers.APIInterface;
import com.example.medical.models.CategoryModel;
import com.example.medical.models.ProductModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductListActivity extends AppCompatActivity {

    ImageView mImageBack;
    TextView mTextToolbarTitle, mTextNoData;
    RecyclerView mRecyclerView;
    Activity mActivity;
    ProductListAdapter mAdapter;

    String mCatName, mSubCatName;
    APIInterface apiInterface;
    ProgressDialog progressDialog;
    ArrayList<ProductModel.Item> itemArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        Window window = this.getWindow();

        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.white));
        mActivity = ProductListActivity.this;
        SharedPref.init(mActivity);

        apiInterface = APIClient.getClient().create(APIInterface.class);

        progressDialog = new ProgressDialog(mActivity);
        progressDialog.setMessage("Loading...");

        mDeclaration();
        getProducts();
    }

    private void getProducts() {
        progressDialog.show();
        ProductModel jsonObject = new ProductModel("GET","Products", mSubCatName, mCatName, "none");

        Call<ProductModel> call = apiInterface.getProducts(jsonObject);
        call.enqueue(new Callback<ProductModel>() {
            @Override
            public void onResponse(Call<ProductModel> call, Response<ProductModel> response) {
                progressDialog.hide();
                try {
                    if (response.code() == 200) {
                        Toast.makeText(mActivity, "Success", Toast.LENGTH_SHORT).show();
                        ProductModel newModel = response.body();
                        itemArrayList = newModel.getItems();
                        if (itemArrayList.size() > 0) {
                            setAdapter();
                            mRecyclerView.setVisibility(View.VISIBLE);
                            mTextNoData.setVisibility(View.GONE);
                        } else {
                            mRecyclerView.setVisibility(View.GONE);
                            mTextNoData.setVisibility(View.VISIBLE);
                        }

                    } else {
                        Toast.makeText(mActivity, "Failed", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ProductModel> call, Throwable t) {
                progressDialog.hide();
                call.cancel();
                Toast.makeText(mActivity, "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setAdapter() {
        mAdapter = new ProductListAdapter(mActivity, itemArrayList);
        mRecyclerView.setLayoutManager(new GridLayoutManager(mActivity, 2));
        mRecyclerView.setAdapter(mAdapter);
    }

    private void mDeclaration() {

        Intent intent = getIntent();
        mCatName = intent.getStringExtra("cat_name");
        mSubCatName = intent.getStringExtra("sub_cat");
        Log.e("cat_sub_cat", mCatName + " " + mSubCatName);

        mImageBack = findViewById(R.id.icon_back);
        mTextToolbarTitle = findViewById(R.id.toolbar_title);
        mRecyclerView = findViewById(R.id.recycler_view);
        mTextNoData = findViewById(R.id.no_data);


        mTextToolbarTitle.setText("Products");
        mImageBack.setOnClickListener(v -> onBackPressed());
    }
}