package com.example.medical.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medical.CheckoutActivity;
import com.example.medical.R;
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

public class CartFragment extends Fragment {

    View mView;
    ImageView mImageBack;
    TextView mTextToolbarTitle, mCartTotal, mProcced;
    RecyclerView mRecyclerView;
    ImageView mImageNoData;
    Activity mActivity;
    MyCartOrWishListAdapter mAdapter;
    LinearLayout mLinearCheckoutOptions;

    APIInterface apiInterface;
    ProgressDialog progressDialog;
    ArrayList<CartModel.Item> itemArrayList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_cart, container, false);

        mActivity = getActivity();
        SharedPref.init(mActivity);

        apiInterface = APIClient.getClient().create(APIInterface.class);

        progressDialog = new ProgressDialog(mActivity);
        progressDialog.setMessage("Loading...");
        mDeclaration();
        getCart();
        return mView;
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
                            mLinearCheckoutOptions.setVisibility(View.VISIBLE);

                            int total = 0;
                            String TotalAmount;
                            for (int i = 0; i < itemArrayList.size(); i++) {
                                TotalAmount = itemArrayList.get(i).getProductPrice();
                                TotalAmount = TotalAmount.replaceAll("\\$","");
                                total += Integer.parseInt(TotalAmount);
                            }

                            mCartTotal.setText("Cart Total: $" + total);
                        } else {
                            mRecyclerView.setVisibility(View.GONE);
                            mImageNoData.setVisibility(View.VISIBLE);
                            mLinearCheckoutOptions.setVisibility(View.GONE);
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

                        int total = 0;
                        String TotalAmount;
                        for (int i = 0; i < itemArrayList.size(); i++) {
                            TotalAmount = itemArrayList.get(i).getProductPrice();
                            TotalAmount = TotalAmount.replaceAll("\\$","");
                            total += Integer.parseInt(TotalAmount);
                        }

                        mCartTotal.setText("Cart Total: $" + total);
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

    private void mDeclaration() {
        mImageBack = mView.findViewById(R.id.icon_back);
        mTextToolbarTitle = mView.findViewById(R.id.toolbar_title);
        mRecyclerView = mView.findViewById(R.id.recycler_view);
        mImageNoData = mView.findViewById(R.id.no_data);
        mCartTotal = mView.findViewById(R.id.cart_total);
        mProcced = mView.findViewById(R.id.procced);

        mLinearCheckoutOptions = mView.findViewById(R.id.checkout_options);

        mImageBack.setVisibility(View.GONE);
        mTextToolbarTitle.setText(R.string.title_cart);

        mProcced.setOnClickListener(v -> {
            Intent intent = new Intent(mActivity, CheckoutActivity.class);
            intent.putExtra("total_item", "Total Item: " + itemArrayList.size());
            intent.putExtra("cart_total", mCartTotal.getText().toString());
            startActivity(intent);
        });
    }
}