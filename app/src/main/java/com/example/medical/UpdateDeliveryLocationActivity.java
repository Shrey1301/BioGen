package com.example.medical;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.medical.helper.SharedPref;
import com.example.medical.helpers.APIClient;
import com.example.medical.helpers.APIInterface;
import com.example.medical.helpers.Constants;
import com.google.gson.JsonObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateDeliveryLocationActivity extends AppCompatActivity {

    EditText mEditFlatNo, mEditStreet, mEditCity, mEditStat;
    Activity mActivity;
    ImageView mImageBack;
    TextView mTextToolbarTitle;
    Button mButtonUpdate;

    APIInterface apiInterface;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_delivery_location);

        Window window = this.getWindow();

        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.white));

        mActivity = UpdateDeliveryLocationActivity.this;

        SharedPref.init(getApplicationContext());

        apiInterface = APIClient.getClient().create(APIInterface.class);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        initViews();
        setUpClickListeners();
    }

    private void setUpClickListeners() {

        if (!SharedPref.readString(Constants.HOUSE_NO,"").equals("")) {
            mEditFlatNo.setText(SharedPref.readString(Constants.HOUSE_NO,""));
        }
        if (!SharedPref.readString(Constants.STREET,"").equals("")) {
            mEditStreet.setText(SharedPref.readString(Constants.STREET,""));
        }
        if (!SharedPref.readString(Constants.TOWN,"").equals("")) {
            mEditCity.setText(SharedPref.readString(Constants.TOWN,""));
        }
        if (!SharedPref.readString(Constants.STATE,"").equals("")) {
            mEditStat.setText(SharedPref.readString(Constants.STATE,""));
        }

        mButtonUpdate.setOnClickListener(view -> {
            if (isDataValid()) {
                updateLocation();
            }
        });

        mImageBack.setOnClickListener(v -> onBackPressed());
    }

    private void updateLocation() {
        progressDialog.show();
        JsonObject jsonObject = new JsonObject();
        try {

            jsonObject.addProperty("httpMethod", "POST");
            jsonObject.addProperty("TableName", "Users");
            jsonObject.addProperty("firstname", SharedPref.readString(Constants.FIRST_NAME,""));
            jsonObject.addProperty("lastname", SharedPref.readString(Constants.LAST_NAME,""));
            jsonObject.addProperty("id", SharedPref.readString(Constants.USER_ID,""));
            jsonObject.addProperty("password", SharedPref.readString(Constants.PASSWORD,""));
            jsonObject.addProperty("house_no", mEditFlatNo.getText().toString());
            jsonObject.addProperty("street", mEditStreet.getText().toString());
            jsonObject.addProperty("town", mEditCity.getText().toString());
            jsonObject.addProperty("state", mEditStat.getText().toString());
            jsonObject.addProperty("status", "true");

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
                        SharedPref.writeString(Constants.HOUSE_NO, mEditFlatNo.getText().toString());
                        SharedPref.writeString(Constants.TOWN, mEditCity.getText().toString());
                        SharedPref.writeString(Constants.STREET, mEditStreet.getText().toString());
                        SharedPref.writeString(Constants.STATE, mEditStat.getText().toString());

                        Intent intent = new Intent(mActivity, DashboardActivity.class);
                        startActivity(intent);
                        finish();
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

    private boolean isDataValid() {
        boolean status = true;

        if (mEditFlatNo.getText().toString().equalsIgnoreCase("")) {
            mEditFlatNo.setError("Enter Flat/House No");
            mEditFlatNo.requestFocus();
            status = false;
        }
        if (mEditStreet.getText().toString().trim().equalsIgnoreCase("")) {
            mEditStreet.setError("Enter Street Name");
            mEditStreet.requestFocus();
            status = false;
        }
        if (mEditCity.getText().toString().trim().equalsIgnoreCase("")) {
            mEditCity.setError("Enter City Name");
            mEditCity.requestFocus();
            status = false;
        }
        if (mEditStat.getText().toString().trim().equalsIgnoreCase("")) {
            mEditStat.setError("Enter State Name");
            mEditStat.requestFocus();
            status = false;
        }

        return status;
    }

    private void initViews() {
        mEditFlatNo = findViewById(R.id.edit_flat_no);
        mEditStreet = findViewById(R.id.edit_street);
        mEditCity = findViewById(R.id.edit_city);
        mEditStat = findViewById(R.id.edit_state);
        mButtonUpdate = findViewById(R.id.button_update_location);

        mImageBack = findViewById(R.id.icon_back);
        mTextToolbarTitle = findViewById(R.id.toolbar_title);
        mTextToolbarTitle.setText("Update Location");
    }
}