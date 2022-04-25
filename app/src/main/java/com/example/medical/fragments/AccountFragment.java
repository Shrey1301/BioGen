package com.example.medical.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.medical.BuildConfig;
import com.example.medical.CustomerSupportActivity;
import com.example.medical.LoginActivity;
import com.example.medical.MyOrderActivity;
import com.example.medical.PrivacyTermsAboutActivity;
import com.example.medical.R;
import com.example.medical.UpdateDeliveryLocationActivity;
import com.example.medical.UserProfileActivity;
import com.example.medical.WishlistActivity;
import com.example.medical.helper.SharedPref;
import com.example.medical.helpers.Constants;

import de.hdodenhof.circleimageview.CircleImageView;


public class AccountFragment extends Fragment {
    
    View mView;
    ImageView mImageBack;
    TextView mTextToolbarTitle;
    Activity mActivity;
    TextView mTextVersion, mTextOrders, mTextWishList, mTextProfile, mTextDeliveryLoc,
            mTextContactSupport, mTextTerms, mTextPrivacy, mTextAbout, mTextUserName, mLogout;
    CircleImageView mImageUserProfile;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_account, container, false);

        mActivity = getActivity();
        SharedPref.init(mActivity);
        mDeclaration();
        return mView;
    }

    private void mDeclaration() {
        mImageBack = mView.findViewById(R.id.icon_back);
        mTextToolbarTitle = mView.findViewById(R.id.toolbar_title);

        mTextVersion = mView.findViewById(R.id.version_text);
        mTextOrders = mView.findViewById(R.id.my_order);
        mTextWishList = mView.findViewById(R.id.my_list);
        mTextProfile = mView.findViewById(R.id.my_profile);
        mTextDeliveryLoc = mView.findViewById(R.id.my_address);
        mTextContactSupport = mView.findViewById(R.id.customer_service);
        mTextTerms = mView.findViewById(R.id.terms);
        mTextPrivacy = mView.findViewById(R.id.privacy_policy);
        mTextAbout = mView.findViewById(R.id.about);
        mTextUserName = mView.findViewById(R.id.text_user_name);
        mLogout = mView.findViewById(R.id.logout);

        mImageUserProfile = mView.findViewById(R.id.profile_image);

        mImageBack.setVisibility(View.GONE);
        mTextToolbarTitle.setText(R.string.title_account);

        mTextUserName.setText("Welcome" +  " , " + SharedPref.readString(Constants.USER_NAME,""));

        mInitMethods();
    }

    @SuppressLint("SetTextI18n")
    private void mInitMethods() {
        try {
            mTextVersion.setText("Version : " + BuildConfig.VERSION_CODE + " (" +BuildConfig.VERSION_NAME + ")");
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        mTextOrders.setOnClickListener(v -> {
            Intent intent = new Intent(mActivity, MyOrderActivity.class);
            startActivity(intent);
        });

        mTextProfile.setOnClickListener(v -> {
            Intent intent = new Intent(mActivity, UserProfileActivity.class);
            startActivity(intent);
        });

        mTextDeliveryLoc.setOnClickListener(v -> {
            Intent intent = new Intent(mActivity, UpdateDeliveryLocationActivity.class);
            startActivity(intent);
        });

        mTextPrivacy.setOnClickListener(v -> {
            Intent intent = new Intent(mActivity, PrivacyTermsAboutActivity.class);
            intent.putExtra("page_name", "privacy");
            startActivity(intent);
        });

        mTextAbout.setOnClickListener(v -> {
            Intent intent = new Intent(mActivity, PrivacyTermsAboutActivity.class);
            intent.putExtra("page_name", "about");
            startActivity(intent);
        });

        mTextTerms.setOnClickListener(v -> {
            Intent intent = new Intent(mActivity, PrivacyTermsAboutActivity.class);
            intent.putExtra("page_name", "terms");
            startActivity(intent);
        });

        mTextContactSupport.setOnClickListener(v -> {
            Intent intent = new Intent(mActivity, CustomerSupportActivity.class);
            startActivity(intent);
        });

        mLogout.setOnClickListener(v -> {
            SharedPref.writeString(Constants.USER_ID, "");
            Intent intent = new Intent(mActivity, LoginActivity.class);
            startActivity(intent);
            mActivity.finishAffinity();
        });

        mTextWishList.setOnClickListener(v -> {
            Intent intent = new Intent(mActivity, WishlistActivity.class);
            startActivity(intent);
        });
    }
}