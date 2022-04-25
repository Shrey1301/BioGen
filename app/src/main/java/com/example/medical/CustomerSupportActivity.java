package com.example.medical;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomerSupportActivity extends AppCompatActivity {

    Activity mActivity;
    ImageView mImageBack;
    TextView mTextToolbarTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_support);

        Window window = this.getWindow();

        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.white));

        mActivity = CustomerSupportActivity.this;
        initViews();
    }

    @SuppressLint("SetTextI18n")
    private void initViews() {
        mImageBack = findViewById(R.id.icon_back);
        mImageBack.setOnClickListener(v -> onBackPressed());
        mTextToolbarTitle = findViewById(R.id.toolbar_title);

        mTextToolbarTitle.setText("Customer Support");
    }
}