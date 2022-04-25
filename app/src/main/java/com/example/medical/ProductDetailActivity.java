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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.medical.helper.SharedPref;
import com.example.medical.helpers.APIClient;
import com.example.medical.helpers.APIInterface;
import com.example.medical.helpers.Constants;
import com.example.medical.models.ProductModel;
import com.google.gson.JsonObject;

import java.util.Calendar;
import java.util.Random;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetailActivity extends AppCompatActivity {

    ImageView mImageBack;
    TextView mTextToolbarTitle, mAddToCart, mAddToWish, mTextName, mTextDetails, mTextPrice;
    Activity mActivity;
    String ProductID;

    private ImageSwitcher simpleImageSwitcher;
    ImageView btnNext;

    APIInterface apiInterface;
    ProgressDialog progressDialog;

    // Array of Image IDs to Show In ImageSwitcher
    int imageIds[] = {R.drawable.bg5, R.drawable.bg6, R.drawable.bg7, R.drawable.bg8, R.drawable.bg9 };
    int count = imageIds.length;
    // to keep current Index of ImageID array
    int currentIndex = 0;

    ProductModel.Item newModelName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        Window window = this.getWindow();

        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.white));
        SharedPref.init(getApplicationContext());

        apiInterface = APIClient.getClient().create(APIInterface.class);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");

        mActivity = ProductDetailActivity.this;
        mDeclaration();
    }

    private void mDeclaration() {
        mImageBack = findViewById(R.id.icon_back);
        mTextToolbarTitle = findViewById(R.id.toolbar_title);
        btnNext = findViewById(R.id.buttonNext);
        simpleImageSwitcher = findViewById(R.id.simpleImageSwitcher);
        mAddToCart = findViewById(R.id.add_cart);
        mAddToWish = findViewById(R.id.add_wishlist);
        mTextName = findViewById(R.id.product_name);
        mTextDetails = findViewById(R.id.text_details);
        mTextPrice = findViewById(R.id.text_price);

        mTextToolbarTitle.setText("Details");
        mImageBack.setOnClickListener(v -> onBackPressed());

        Intent intent = getIntent();
        newModelName = (ProductModel.Item) intent.getSerializableExtra("model");

        // Set the ViewFactory of the ImageSwitcher that will create ImageView object when asked
        simpleImageSwitcher.setFactory(() -> {
            // TODO Auto-generated method stub
            // Create a new ImageView and set it's properties
            ImageView imageView = new ImageView(getApplicationContext());
            // set Scale type of ImageView to Fit Center
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            // set the Height And Width of ImageView To FIll PARENT
            imageView.setLayoutParams(new ImageSwitcher.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
            return imageView;
        });

        // Declare in and out animations and load them using AnimationUtils class
        Animation in = AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left);
        Animation out = AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right);

        // set the animation type to ImageSwitcher
        simpleImageSwitcher.setInAnimation(in);
        simpleImageSwitcher.setOutAnimation(out);

        simpleImageSwitcher.setImageResource(imageIds[0]);


        // ClickListener for NEXT button
        // When clicked on Button ImageSwitcher will switch between Images
        // The current Image will go OUT and next Image  will come in with specified animation
        btnNext.setOnClickListener(v -> {
            // TODO Auto-generated method stub
            currentIndex++;
            //  Check If index reaches maximum then reset it
            if (currentIndex == count)
                currentIndex = 0;
            simpleImageSwitcher.setImageResource(imageIds[currentIndex]); // set the image in ImageSwitcher
        });

        mAddToCart.setOnClickListener(v -> addToCart());

        mAddToWish.setOnClickListener(v -> addToWishList());

        mTextName.setText(newModelName.getName());
        mTextDetails.setText(newModelName.getProductDesc());
        mTextPrice.setText(newModelName.getProductPrice());
        ProductID = newModelName.getProductId();
    }

    private void addToWishList() {
        progressDialog.show();
        JsonObject jsonObject = new JsonObject();
        long random = Calendar.getInstance().getTimeInMillis();
        try {
            jsonObject.addProperty("httpMethod", "POST");
            jsonObject.addProperty("TableName", "Cart");
            jsonObject.addProperty("userId", SharedPref.readString(Constants.USER_ID,""));
            jsonObject.addProperty("itemId", ProductID);
            jsonObject.addProperty("Cart_id", String.valueOf(random));
            jsonObject.addProperty("addTo", "WISHLIST");
            jsonObject.addProperty("cat_name", newModelName.getCatName());
            jsonObject.addProperty("sub_category", newModelName.getSubCat());
            jsonObject.addProperty("product_image", newModelName.getProductImage());
            jsonObject.addProperty("flag", newModelName.getFlag());
            jsonObject.addProperty("product_price", newModelName.getProductPrice());
            jsonObject.addProperty("name", newModelName.getName());
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
                        Toast.makeText(mActivity, "Item Added To Wish-List", Toast.LENGTH_SHORT).show();
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

    private void addToCart() {
        progressDialog.show();
        JsonObject jsonObject = new JsonObject();
        long random = Calendar.getInstance().getTimeInMillis();
        try {
            jsonObject.addProperty("httpMethod", "POST");
            jsonObject.addProperty("TableName", "Cart");
            jsonObject.addProperty("userId", SharedPref.readString(Constants.USER_ID,""));
            jsonObject.addProperty("itemId", ProductID);
            jsonObject.addProperty("Cart_id", String.valueOf(random));
            jsonObject.addProperty("addTo", "CART");
            jsonObject.addProperty("cat_name", newModelName.getCatName());
            jsonObject.addProperty("sub_category", newModelName.getSubCat());
            jsonObject.addProperty("product_image", newModelName.getProductImage());
            jsonObject.addProperty("flag", newModelName.getFlag());
            jsonObject.addProperty("product_price", newModelName.getProductPrice());
            jsonObject.addProperty("name", newModelName.getName());
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
                        Toast.makeText(mActivity, "Item Added To Cart", Toast.LENGTH_SHORT).show();
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
}