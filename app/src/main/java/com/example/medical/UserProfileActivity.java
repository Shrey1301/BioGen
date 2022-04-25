package com.example.medical;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.medical.helper.SharedPref;
import com.example.medical.helpers.APIClient;
import com.example.medical.helpers.APIInterface;
import com.example.medical.helpers.Constants;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.JsonObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserProfileActivity extends AppCompatActivity {

    EditText mEditEmail, mEditPassword, mEditFirstName, mEditLastName, mEditCPassword;
    Activity mActivity;
    ImageView mImageBack;
    TextView mTextToolbarTitle;
    Button mButtonUpdate;

    APIInterface apiInterface;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        Window window = this.getWindow();

        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.white));

        SharedPref.init(getApplicationContext());

        apiInterface = APIClient.getClient().create(APIInterface.class);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");

        mActivity = UserProfileActivity.this;
        initViews();
        setUpClickListeners();
    }

    private void setUpClickListeners() {
        if (!SharedPref.readString(Constants.FIRST_NAME,"").equals("")) {
            mEditFirstName.setText(SharedPref.readString(Constants.FIRST_NAME,""));
        }
        if (!SharedPref.readString(Constants.LAST_NAME,"").equals("")) {
            mEditLastName.setText(SharedPref.readString(Constants.LAST_NAME,""));
        }
        if (!SharedPref.readString(Constants.USER_ID,"").equals("")) {
            mEditEmail.setText(SharedPref.readString(Constants.USER_ID,""));
        }
        if (!SharedPref.readString(Constants.PASSWORD,"").equals("")) {
            mEditPassword.setText(SharedPref.readString(Constants.PASSWORD,""));
        }
        mButtonUpdate.setOnClickListener(view -> {
            if (isDataValid()) {
                updateProfile();
            }
        });

        mImageBack.setOnClickListener(v -> onBackPressed());
    }

    private void updateProfile() {
        progressDialog.show();
        JsonObject jsonObject = new JsonObject();
        try {

            jsonObject.addProperty("httpMethod", "POST");
            jsonObject.addProperty("TableName", "Users");
            jsonObject.addProperty("firstname", mEditFirstName.getText().toString());
            jsonObject.addProperty("lastname", mEditLastName.getText().toString());
            jsonObject.addProperty("id", mEditEmail.getText().toString());
            jsonObject.addProperty("password", mEditPassword.getText().toString());
            jsonObject.addProperty("house_no", SharedPref.readString(Constants.HOUSE_NO,""));
            jsonObject.addProperty("street", SharedPref.readString(Constants.STREET,""));
            jsonObject.addProperty("town", SharedPref.readString(Constants.TOWN,""));
            jsonObject.addProperty("state", SharedPref.readString(Constants.STATE,""));
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
                        SharedPref.writeString(Constants.FIRST_NAME, mEditFirstName.getText().toString());
                        SharedPref.writeString(Constants.LAST_NAME, mEditLastName.getText().toString());
                        SharedPref.writeString(Constants.PASSWORD, mEditPassword.getText().toString());
                        SharedPref.writeString(Constants.USER_NAME, mEditFirstName.getText().toString() + " " + mEditLastName.getText().toString());

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

        String email = mEditEmail.getText().toString();
        if (mEditPassword.getText().toString().equalsIgnoreCase("") || mEditPassword.getText().toString().trim().length() < 8) {
            mEditPassword.setError("Enter Valid Password");
            mEditCPassword.requestFocus();
            status = false;
        }
        if (mEditFirstName.getText().toString().trim().equalsIgnoreCase("")) {
            mEditFirstName.setError("Enter Valid Name");
            mEditCPassword.requestFocus();
            status = false;
        }
        if (mEditLastName.getText().toString().trim().equalsIgnoreCase("")) {
            mEditLastName.setError("Enter Valid Name");
            mEditCPassword.requestFocus();
            status = false;
        }
        if (!mEditCPassword.getText().toString().trim().equalsIgnoreCase(mEditPassword.getText().toString().trim())) {
            mEditCPassword.setError("Password Not Match");
            mEditCPassword.requestFocus();
            status = false;
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mEditEmail.setError("Enter Valid Email");
            mEditCPassword.requestFocus();
            status = false;
        }

        return status;
    }

    private void initViews() {
        mEditEmail = findViewById(R.id.edit_username);
        mEditPassword = findViewById(R.id.edit_password);
        mEditFirstName = findViewById(R.id.edit_first_name);
        mEditCPassword = findViewById(R.id.edit_c_password);
        mEditLastName = findViewById(R.id.edit_last_name);
        mButtonUpdate = findViewById(R.id.button_update_profile);

        mImageBack = findViewById(R.id.icon_back);
        mTextToolbarTitle = findViewById(R.id.toolbar_title);
        mTextToolbarTitle.setText("Profile");
    }
}