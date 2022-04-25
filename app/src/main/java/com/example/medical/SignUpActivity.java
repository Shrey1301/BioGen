package com.example.medical;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.medical.helpers.APIClient;
import com.example.medical.helpers.APIInterface;
import com.example.medical.models.UserModel;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.JsonObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {

    EditText mEditEmail, mEditPassword, mEditFirstName, mEditLastName, mEditCPassword;
    TextView mTextLogin;
    Button mButtonSignUp;
    Activity mActivity;
    ScrollView mParentLayout;
    APIInterface apiInterface;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_up);

        apiInterface = APIClient.getClient().create(APIInterface.class);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");

        mActivity = SignUpActivity.this;
        initViews();
        setUpClickListeners();
    }

    private void setUpClickListeners() {
        mButtonSignUp.setOnClickListener(view -> {
            if (isDataValid()) {
                progressDialog.show();
                registerUser();
            } else {
                Snackbar.make(mParentLayout, "Enter Valid Details", Snackbar.LENGTH_LONG)
                        .setAction("CLOSE", view2 -> {
                        })
                        .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                        .show();
            }
        });

        mTextLogin.setOnClickListener(v -> {
            Intent intent = new Intent(mActivity, LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void registerUser() {
        JsonObject jsonObject = new JsonObject();
        try {

            jsonObject.addProperty("httpMethod", "POST");
            jsonObject.addProperty("TableName", "Users");
            jsonObject.addProperty("firstname", mEditFirstName.getText().toString().trim());
            jsonObject.addProperty("lastname", mEditLastName.getText().toString().trim());
            jsonObject.addProperty("id", mEditEmail.getText().toString().trim());
            jsonObject.addProperty("password", mEditPassword.getText().toString().trim());
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
                        Intent intent = new Intent(mActivity, LoginActivity.class);
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
        mButtonSignUp = findViewById(R.id.button_signup);
        mTextLogin = findViewById(R.id.login_text);
        mParentLayout = findViewById(R.id.parent_login);

    }
}