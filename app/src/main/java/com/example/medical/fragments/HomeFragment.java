package com.example.medical.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.example.medical.R;
import com.example.medical.adapters.BestSellerAdapter;
import com.example.medical.adapters.CategoryHomeAdapter;
import com.example.medical.adapters.FeaturedAdapter;
import com.example.medical.adapters.PopularAdapter;
import com.example.medical.adapters.ProductListAdapter;
import com.example.medical.helper.SharedPref;
import com.example.medical.helpers.APIClient;
import com.example.medical.helpers.APIInterface;
import com.example.medical.models.CategoryModel;
import com.example.medical.models.HomeModel;
import com.example.medical.models.ProductModel;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {

    View mView;
    ImageView mImageBack;
    TextView mTextToolbarTitle;
    FeaturedAdapter mAdapter;
    BestSellerAdapter mAdapterBS;
    PopularAdapter mAdapterPopular;
    CategoryHomeAdapter categoryHomeAdapter;
    ArrayList<HomeModel> mArrayList;
    RecyclerView mRecyclerView;
    RecyclerView mRecyclerView2;
    RecyclerView mRecyclerView3;
    RecyclerView mRecyclerViewCat;

    SliderLayout sliderLayout;
    Activity mActivity;

    APIInterface apiInterface;
    ProgressDialog progressDialog;

    ArrayList<CategoryModel.Item> itemArrayList = new ArrayList<>();
    ArrayList<ProductModel.Item> bestSellerItem = new ArrayList<>();
    ArrayList<ProductModel.Item> popularItem = new ArrayList<>();
    ArrayList<ProductModel.Item> featuredItem = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_home, container, false);

        mActivity = getActivity();
        mArrayList = new ArrayList<>();

        SharedPref.init(mActivity);

        apiInterface = APIClient.getClient().create(APIInterface.class);

        progressDialog = new ProgressDialog(mActivity);
        progressDialog.setMessage("Loading...");

        mDeclaration();
        return mView;
    }
 
    private void getCategory() {
        progressDialog.show();
        CategoryModel jsonObject = new CategoryModel("SCAN","Category");

        Call<CategoryModel> call = apiInterface.getCat(jsonObject);
        call.enqueue(new Callback<CategoryModel>() {
            @Override
            public void onResponse(Call<CategoryModel> call, Response<CategoryModel> response) {
                progressDialog.hide();
                try {
                    if (response.code() == 200) {
                        Toast.makeText(mActivity, "Success", Toast.LENGTH_SHORT).show();
                        CategoryModel newModel = response.body();
                        itemArrayList = newModel.getItems();
                        setCatAdapter();
                    } else {
                        Toast.makeText(mActivity, "Failed", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<CategoryModel> call, Throwable t) {
                progressDialog.hide();
                call.cancel();
                Toast.makeText(mActivity, "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setCatAdapter() {
        categoryHomeAdapter = new CategoryHomeAdapter(mActivity, itemArrayList);
        mRecyclerViewCat.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.HORIZONTAL, false));
        mRecyclerViewCat.setAdapter(categoryHomeAdapter);
    }

    private void mDeclaration() {
        mImageBack = mView.findViewById(R.id.icon_back);
        mTextToolbarTitle = mView.findViewById(R.id.toolbar_title);

        mImageBack.setVisibility(View.GONE);
        mTextToolbarTitle.setText(R.string.title_home);

        sliderLayout = (SliderLayout) mView.findViewById(R.id.sliderLayout);
        setupSlider();
        getHomeData();
        getCategory();
        getBestSeller();
        getPopular();
        getFeatured();
    }

    private void getFeatured() {
        progressDialog.show();
        ProductModel jsonObject = new ProductModel("GET","Products", "", "", "featured");

        Call<ProductModel> call = apiInterface.getProducts(jsonObject);
        call.enqueue(new Callback<ProductModel>() {
            @Override
            public void onResponse(Call<ProductModel> call, Response<ProductModel> response) {
                progressDialog.hide();
                try {
                    if (response.code() == 200) {
                        Toast.makeText(mActivity, "Success", Toast.LENGTH_SHORT).show();
                        ProductModel newModel = response.body();
                        featuredItem = newModel.getItems();
                        setAdapterFeatured();
                    } else {
                        Toast.makeText(mActivity, "Failed", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ProductModel> call, Throwable t) {
                progressDialog.hide();
                call.cancel();
                Toast.makeText(mActivity, "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setAdapterFeatured() {
        mAdapter = new FeaturedAdapter(mActivity,featuredItem);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.HORIZONTAL, false));
        mRecyclerView.setAdapter(mAdapter);
    }

    private void getPopular() {
        progressDialog.show();
        ProductModel jsonObject = new ProductModel("GET","Products", "", "", "popular");

        Call<ProductModel> call = apiInterface.getProducts(jsonObject);
        call.enqueue(new Callback<ProductModel>() {
            @Override
            public void onResponse(Call<ProductModel> call, Response<ProductModel> response) {
                progressDialog.hide();
                try {
                    if (response.code() == 200) {
                        Toast.makeText(mActivity, "Success", Toast.LENGTH_SHORT).show();
                        ProductModel newModel = response.body();
                        popularItem = newModel.getItems();
                        setAdapterPopular();
                    } else {
                        Toast.makeText(mActivity, "Failed", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ProductModel> call, Throwable t) {
                progressDialog.hide();
                call.cancel();
                Toast.makeText(mActivity, "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setAdapterPopular() {
        mAdapterPopular = new PopularAdapter(mActivity,popularItem);
        mRecyclerView3.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.HORIZONTAL, false));
        mRecyclerView3.setAdapter(mAdapterPopular);
    }

    private void getBestSeller() {progressDialog.show();
        ProductModel jsonObject = new ProductModel("GET","Products", "", "", "bestseller");

        Call<ProductModel> call = apiInterface.getProducts(jsonObject);
        call.enqueue(new Callback<ProductModel>() {
            @Override
            public void onResponse(Call<ProductModel> call, Response<ProductModel> response) {
                progressDialog.hide();
                try {
                    if (response.code() == 200) {
                        Toast.makeText(mActivity, "Success", Toast.LENGTH_SHORT).show();
                        ProductModel newModel = response.body();
                        bestSellerItem = newModel.getItems();
                        setAdapterBS();
                    } else {
                        Toast.makeText(mActivity, "Failed", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ProductModel> call, Throwable t) {
                progressDialog.hide();
                call.cancel();
                Toast.makeText(mActivity, "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setAdapterBS() {
        mAdapterBS = new BestSellerAdapter(mActivity, bestSellerItem);
        mRecyclerView2.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.HORIZONTAL, false));
        mRecyclerView2.setAdapter(mAdapterBS);
    }

    private void getHomeData() {
        mRecyclerView = mView.findViewById(R.id.recycler_view_popular);
        mRecyclerView2 = mView.findViewById(R.id.recycler_view_best_seller);
        mRecyclerView3 = mView.findViewById(R.id.recycler_view_teas);
        mRecyclerViewCat = mView.findViewById(R.id.recycler_view_category);
    }

    private void setupSlider() {
        HashMap<String, Integer> sliderImages = new HashMap<>();
        sliderImages.put("Super-Food", R.drawable.bg_2);
        sliderImages.put("Barley-Max", R.drawable.bg_3);
//        sliderImages.put("Best Deals On Health Items", R.drawable.bg4);
        sliderImages.put("Medicines", R.drawable.bg5);
        sliderImages.put("Plant Power", R.drawable.bg6);
        sliderImages.put("Love Locks", R.drawable.bg7);
        sliderImages.put("Triphala", R.drawable.bg8);
        sliderImages.put("Argan Oil", R.drawable.bg9);

        for (String name : sliderImages.keySet()) {

            TextSliderView textSliderView = new TextSliderView(mActivity);
            //noinspection ConstantConditions
            textSliderView
                    .description(name)
                    .image(sliderImages.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra", name);
            sliderLayout.addSlider(textSliderView);
        }
        sliderLayout.setPresetTransformer(SliderLayout.Transformer.Accordion);
        sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        sliderLayout.setCustomAnimation(new DescriptionAnimation());
        sliderLayout.setDuration(3000);
        sliderLayout.addOnPageChangeListener(this);
    }


    @Override
    public void onSliderClick(BaseSliderView slider) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}