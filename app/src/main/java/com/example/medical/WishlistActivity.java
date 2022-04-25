package com.example.medical;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.medical.adapters.MyCartOrWishListAdapter;
import com.example.medical.helper.SharedPref;
import com.example.medical.helpers.APIClient;
import com.example.medical.helpers.APIInterface;
import com.example.medical.helpers.Constants;
import com.example.medical.models.CartModel;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WishlistActivity extends AppCompatActivity {

    ImageView mImageBack;
    TextView mTextToolbarTitle , mImageNoData;
    RecyclerView mRecyclerView;
    Activity mActivity;
    MyCartOrWishListAdapter mAdapter;

    APIInterface apiInterface;
    ProgressDialog progressDialog;
    ArrayList<CartModel.Item> itemArrayList = new ArrayList<>();
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_or_wishlist);

        Window window = this.getWindow();

        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.white));

        mActivity = WishlistActivity.this;
        SharedPref.init(mActivity);

        apiInterface = APIClient.getClient().create(APIInterface.class);

        progressDialog = new ProgressDialog(mActivity);
        progressDialog.setMessage("Loading...");
        mDeclaration();
        getCart();
    }

    private void mDeclaration() {
        mImageBack = findViewById(R.id.icon_back);
        mTextToolbarTitle = findViewById(R.id.toolbar_title);
        mRecyclerView = findViewById(R.id.recycler_view);
        mImageNoData = findViewById(R.id.no_data);

        mTextToolbarTitle.setText("Wish List");
        mImageBack.setOnClickListener(v -> onBackPressed());
    }

    private void getCart() {
        progressDialog.show();
        CartModel jsonObject = new CartModel("GET","Cart", SharedPref.readString(Constants.USER_ID,""), "CART");

        Call<CartModel> call = apiInterface.getCART(jsonObject);
        call.enqueue(new Callback<CartModel>() {
            @Override
            public void onResponse(Call<CartModel> call, Response<CartModel> response) {
                progressDialog.hide();
                try {
                    if (response.code() == 200) {
                        Toast.makeText(mActivity, "Success", Toast.LENGTH_SHORT).show();
                        CartModel newModel = response.body();
                        itemArrayList = newModel.getItems();
                        if (itemArrayList.size() > 0) {
                            setAdapter();
                            mRecyclerView.setVisibility(View.VISIBLE);
                            mImageNoData.setVisibility(View.GONE);
                        } else {
                            mRecyclerView.setVisibility(View.GONE);
                            mImageNoData.setVisibility(View.VISIBLE);
                        }

                    } else {
                        Toast.makeText(mActivity, "Failed", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<CartModel> call, Throwable t) {
                progressDialog.hide();
                call.cancel();
                Toast.makeText(mActivity, "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setAdapter() {
        mAdapter = new MyCartOrWishListAdapter(mActivity, itemArrayList) {
            @Override
            protected void removeItem(CartModel.Item item, int adapterPosition) {
                super.removeItem(item, adapterPosition);
                deleteFromCart(item, adapterPosition);
            }
        };
        mRecyclerView.setLayoutManager(new GridLayoutManager(mActivity, 2));
        mRecyclerView.setAdapter(mAdapter);
    }

    private void deleteFromCart(CartModel.Item item, int adapterPosition) {
        progressDialog.show();
        JsonObject jsonObject = new JsonObject();
        try {

            jsonObject.addProperty("httpMethod", "DELETE");
            jsonObject.addProperty("TableName", "Cart");
            jsonObject.addProperty("Cart_id", item.getCartId());

            Log.e("register", jsonObject.toString().replace("\\\\",""));
        } catch (Exception e) {
            e.printStackTrace();
        }

        Call<ResponseBody> call = apiInterface.apiCall(jsonObject);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressDialog.hide();
                try {
                    if (response.code() == 200) {
                        Toast.makeText(mActivity, "Success", Toast.LENGTH_SHORT).show();
                        itemArrayList.remove(adapterPosition);
                        mAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(mActivity, "Failed", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressDialog.hide();
                call.cancel();
                Toast.makeText(mActivity, "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}