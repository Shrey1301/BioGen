package com.example.medical;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.medical.helper.SharedPref;
import com.example.medical.helpers.APIClient;
import com.example.medical.helpers.APIInterface;
import com.example.medical.helpers.Constants;
import com.google.gson.JsonObject;

import java.util.Calendar;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckoutActivity extends AppCompatActivity {

    Activity mActivity;
    ImageView mImageBack;
    TextView mTextToolbarTitle, mTotalItem, mTotalAmount, mPersonName, mPhone, mAddress;
    Button mButtonPlaceOrder;

    APIInterface apiInterface;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        Window window = this.getWindow();

        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.white));

        mActivity = CheckoutActivity.this;

        SharedPref.init(getApplicationContext());

        apiInterface = APIClient.getClient().create(APIInterface.class);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        initViews();
        setUpClickListeners();
    }

    private void setUpClickListeners() {

        Intent intent = getIntent();
        mTotalItem.setText(intent.getStringExtra("total_item"));
        mTotalAmount.setText(intent.getStringExtra("cart_total"));

        mPersonName.setText(SharedPref.readString(Constants.USER_NAME, ""));
        mPhone.setText(SharedPref.readString(Constants.USER_ID, ""));
        mAddress.setText(SharedPref.readString(Constants.HOUSE_NO, "") + " " + SharedPref.readString(Constants.STREET, "") + " " +
                SharedPref.readString(Constants.TOWN, "") + " " +
                SharedPref.readString(Constants.STATE, ""));
        mButtonPlaceOrder.setOnClickListener(v -> {
            placeOrder();
        });
    }

    private void placeOrder() {
        progressDialog.show();
        JsonObject jsonObject = new JsonObject();
        try {

            jsonObject.addProperty("httpMethod", "POST");
            jsonObject.addProperty("TableName", "Orders");
            jsonObject.addProperty("userId", SharedPref.readString(Constants.USER_ID,""));
            jsonObject.addProperty("order_id", String.valueOf(Calendar.getInstance().getTimeInMillis()));

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
                        Toast.makeText(mActivity, "Order Placed Successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(mActivity, DashboardActivity.class);
                        startActivity(intent);
                        finishAffinity();
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

    private void initViews() {
        mImageBack = findViewById(R.id.icon_back);
        mTextToolbarTitle = findViewById(R.id.toolbar_title);
        mTextToolbarTitle.setText("Place Order");
        mImageBack.setOnClickListener(v -> onBackPressed());

        mTotalAmount = findViewById(R.id.total_amount);
        mTotalItem = findViewById(R.id.cart_total_item);
        mPersonName = findViewById(R.id.person_name);
        mPhone = findViewById(R.id.person_phone);
        mAddress = findViewById(R.id.location);

        mButtonPlaceOrder = findViewById(R.id.button_place_order);
    }
}