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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medical.R;
import com.example.medical.adapters.ShoppingAdapter;
import com.example.medical.helper.SharedPref;
import com.example.medical.helpers.APIClient;
import com.example.medical.helpers.APIInterface;
import com.example.medical.models.CategoryModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryFragment extends Fragment {

    View mView;
    ImageView mImageBack;
    TextView mTextToolbarTitle;
    Activity mActivity;
    RecyclerView mRecyclerView;
    ShoppingAdapter mAdapter;

    APIInterface apiInterface;
    ProgressDialog progressDialog;

    ArrayList<CategoryModel.Item> itemArrayList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_category, container, false);
        mActivity = getActivity();

        SharedPref.init(mActivity);

        apiInterface = APIClient.getClient().create(APIInterface.class);

        progressDialog = new ProgressDialog(mActivity);
        progressDialog.setMessage("Loading...");

        mDeclaration();
        getCatAndSubCat();
        return mView;
    }

    private void getCatAndSubCat() {
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
                        setAdapter();
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

    private void setAdapter() {
        mAdapter = new ShoppingAdapter(mActivity, itemArrayList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(mAdapter);
    }

    private void mDeclaration() {
        mImageBack = mView.findViewById(R.id.icon_back);
        mTextToolbarTitle = mView.findViewById(R.id.toolbar_title);
        mRecyclerView = mView.findViewById(R.id.recycler_view);

        mImageBack.setVisibility(View.GONE);
        mTextToolbarTitle.setText(R.string.title_shopping);
    }
}