package com.example.medical;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.example.medical.helper.SharedPref;
import com.example.medical.helpers.Constants;

public class SplashScreenActivity extends AppCompatActivity {

    final Handler handler = new Handler();
    Activity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        setContentView(R.layout.activity_splash_screen);

        mActivity = SplashScreenActivity.this;
        SharedPref.init(getApplicationContext());

        handler.postDelayed(() -> {
            if (SharedPref.readString(Constants.USER_ID,"").equals("")) {
                Intent intent = new Intent(mActivity, LoginActivity.class);
                startActivity(intent);
                finish();
            } else {
                Intent intent = new Intent(mActivity, DashboardActivity.class);
                startActivity(intent);
                finish();
            }
        }, 3 * 1000);
    }
}