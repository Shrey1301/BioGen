package com.example.medical;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

public class PrivacyTermsAboutActivity extends AppCompatActivity {

    Activity mActivity;
    ImageView mImageBack;
    TextView mTextToolbarTitle, mTextToDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_terms_about);

        Window window = this.getWindow();

        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.white));

        mActivity = PrivacyTermsAboutActivity.this;
        initViews();
    }

    private void initViews() {
        mImageBack = findViewById(R.id.icon_back);
        mImageBack.setOnClickListener(v -> onBackPressed());
        mTextToolbarTitle = findViewById(R.id.toolbar_title);
        mTextToDisplay = findViewById(R.id.text_to_display);

        Intent intent = getIntent();
        String pageName = intent.getStringExtra("page_name");

        if (pageName.equals("privacy")) {
            mTextToolbarTitle.setText("Privacy Policy");
            mTextToDisplay.setText("Our Privacy Policy, which also governs your visit to Our Site, can be found below. Please review our Privacy Policy for information on how We collect, use and share information about our users.");
        } else if (pageName.equals("about")) {
            mTextToolbarTitle.setText("About Us");
            mTextToDisplay.setText("Biogenique, a research organization dedicated to improving health and well-being, founded by a small team in cardiology, pharmaceutical chemistry, biochemistry, health sciences and botany in 1999. After 20 years of research and development, we are sure to offer you the best products today");
        } else {
            mTextToolbarTitle.setText("Terms");
            mTextToDisplay.setText("This website and any mobile application (collectively, this \"Site\") is owned by Biogenique Laboratory Inc (\"We\", \"Us\" or \"Biogenique\"). We are providing you with access to this Site and our online store (together, our \"Services\") subject to the following terms and conditions. By browsing, accessing, using, registering for or purchasing merchandise on this Site or otherwise using our Services, you are agreeing to all of the following terms and conditions, including any policies referred to herein (collectively, these \"Terms\").");
        }
    }
}