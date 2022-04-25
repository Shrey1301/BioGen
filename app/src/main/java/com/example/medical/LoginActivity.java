package com.example.medical;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.medical.helper.SharedPref;
import com.example.medical.helpers.APIClient;
import com.example.medical.helpers.APIInterface;
import com.example.medical.helpers.Constants;
import com.example.medical.models.LoginModel;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.JsonObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    EditText mEditEmail, mEditPassword;
    TextView mTextForgetPassword, mTextSignUp;
    Button mButtonLogin;
    Activity mActivity;
    RelativeLayout mParentLayout;
    APIInterface apiInterface;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        SharedPref.init(getApplicationContext());

        apiInterface = APIClient.getClient().create(APIInterface.class);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");

        mActivity = LoginActivity.this;
        mDeclaration();
    }

    private void mDeclaration() {
        mParentLayout = findViewById(R.id.parent_login);
        mEditEmail = findViewById(R.id.edit_username);
        mEditPassword = findViewById(R.id.edit_password);
        mTextForgetPassword = findViewById(R.id.forget_password);
        mTextSignUp = findViewById(R.id.sign_up);
        mButtonLogin = findViewById(R.id.button_login);

        mInitMethods();
    }

    private void mInitMethods() {
        mTextSignUp.setOnClickListener(v -> {
            Intent intent = new Intent(mActivity, SignUpActivity.class);
            startActivity(intent);
            finish();
        });

        mTextForgetPassword.setOnClickListener(v -> {

        });

        mButtonLogin.setOnClickListener(v -> {
            if (isValidInput()) {
                progressDialog.show();
                mLogin();
            } else {
                Snackbar.make(mParentLayout, "Enter Valid Details", Snackbar.LENGTH_LONG)
                        .setAction("CLOSE", view -> {
                        })
                        .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                        .show();
            }
        });
    }

    private void mLogin() {
        LoginModel jsonObject = new LoginModel("GET","Users", mEditEmail.getText().toString().trim());

        Call<LoginModel> call = apiInterface.loginAPI(jsonObject);
        call.enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                progressDialog.hide();
                try {
                    if (response.code() == 200) {
                        Toast.makeText(mActivity, "Success", Toast.LENGTH_SHORT).show();
                        LoginModel newModel = response.body();

                        if (newModel.getItem().getId().equals("")) {
                            Toast.makeText(mActivity,"User does not exist", Toast.LENGTH_SHORT).show();
                        } else if (!newModel.getItem().getPassword().equals(mEditPassword.getText().toString())) {
                            Toast.makeText(mActivity,"Password does not match", Toast.LENGTH_SHORT).show();
                        } else {
                            SharedPref.writeString(Constants.USER_ID, newModel.getItem().getId());
                            SharedPref.writeString(Constants.USER_NAME, newModel.getItem().getFirstname() + " " + newModel.getItem().getLastname());
                            SharedPref.writeString(Constants.FIRST_NAME, newModel.getItem().getFirstname());
                            SharedPref.writeString(Constants.LAST_NAME, newModel.getItem().getLastname());
                            SharedPref.writeString(Constants.PASSWORD, newModel.getItem().getPassword());
                            SharedPref.writeString(Constants.HOUSE_NO, newModel.getItem().getHouseNo());
                            SharedPref.writeString(Constants.TOWN, newModel.getItem().getTown());
                            SharedPref.writeString(Constants.STREET, newModel.getItem().getStreet());
                            SharedPref.writeString(Constants.STATE, newModel.getItem().getState());
                            Intent intent = new Intent(mActivity, DashboardActivity.class);
                            startActivity(intent);
                            finishAffinity();
                        }

                    } else {
                        Toast.makeText(mActivity, "Failed", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                progressDialog.hide();
                call.cancel();
                Toast.makeText(mActivity, "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean isValidInput() {
        boolean status = true;
        String email = mEditEmail.getText().toString();
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            status = false;
            mEditEmail.requestFocus();
            mEditEmail.setError("Enter Valid Email");
        } else if (mEditPassword.getText().toString().trim().isEmpty() || mEditPassword.getText().toString().trim().length() < 8) {
            status = false;
            mEditPassword.requestFocus();
        }
        return status;
    }
}