package com.example.medical;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.medical.fragments.AccountFragment;
import com.example.medical.fragments.CartFragment;
import com.example.medical.fragments.CategoryFragment;
import com.example.medical.fragments.HomeFragment;
import com.example.medical.helper.SharedPref;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class DashboardActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigation;
    @SuppressLint("NonConstantResourceId")
    BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            item -> {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        openFragment(new HomeFragment());
                        return true;
                    case R.id.navigation_category:
                        openFragment(new CategoryFragment());
                        return true;
                    case R.id.navigation_cart:
                        openFragment(new CartFragment());
                        return true;
                    case R.id.navigation_account:
                        openFragment(new AccountFragment());
                        return true;
                }
                return false;
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        Window window = this.getWindow();

        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.white));

        bottomNavigation = findViewById(R.id.nav_view);

        bottomNavigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        openFragment(new HomeFragment());
        SharedPref.init(getApplicationContext());

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public void openFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}