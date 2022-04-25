package com.example.medical;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.medical.adapters.MyOrderAdapter;
import com.example.medical.adapters.ProductListAdapter;
import com.example.medical.adapters.ShoppingAdapter;
import com.example.medical.helper.SharedPref;
import com.example.medical.helpers.APIClient;
import com.example.medical.helpers.APIInterface;
import com.example.medical.helpers.Constants;
import com.example.medical.models.CartModel;
import com.example.medical.models.OrderModel;
import com.example.medical.models.ProductModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyOrderActivity extends AppCompatActivity {

    Activity mActivity;
    RecyclerView mRecyclerView;
    MyOrderAdapter mAdapter;
    ImageView mImageBack;
    TextView mTextToolbarTitle, mTextNoData;

    APIInterface apiInterface;
    ProgressDialog progressDialog;
    ArrayList<OrderModel.Item> itemArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);

        Window window = this.getWindow();

        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.white));

        mActivity = MyOrderActivity.this;

        SharedPref.init(getApplicationContext());

        apiInterface = APIClient.getClient().create(APIInterface.class);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");

        mDeclaration();
        getMyOrders();
    }

    private void getMyOrders() {
        progressDialog.show();
        OrderModel jsonObject = new OrderModel("GET","Orders", SharedPref.readString(Constants.USER_ID,""));

        Call<OrderModel> call = apiInterface.getOrders(jsonObject);
        call.enqueue(new Callback<OrderModel>() {
            @Override
            public void onResponse(Call<OrderModel> call, Response<OrderModel> response) {
                progressDialog.hide();
                try {
                    if (response.code() == 200) {
                        Toast.makeText(mActivity, "Success", Toast.LENGTH_SHORT).show();
                        OrderModel newModel = response.body();
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
            public void onFailure(Call<OrderModel> call, Throwable t) {
                progressDialog.hide();
                call.cancel();
                Toast.makeText(mActivity, "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setAdapter() {
        mAdapter = new MyOrderAdapter(mActivity, itemArrayList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(mAdapter);
    }

    private void mDeclaration() {
        mImageBack = findViewById(R.id.icon_back);
        mTextToolbarTitle = findViewById(R.id.toolbar_title);
        mRecyclerView = findViewById(R.id.recycler_view);
        mTextNoData = findViewById(R.id.no_data);

        mImageBack.setOnClickListener(v -> {
        onBackPressed();
        });

        mTextToolbarTitle.setText("My Orders");
    }
}