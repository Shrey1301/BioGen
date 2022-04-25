package com.example.medical.helpers;

import com.example.medical.models.CartModel;
import com.example.medical.models.CategoryModel;
import com.example.medical.models.LoginModel;
import com.example.medical.models.OrderModel;
import com.example.medical.models.ProductModel;
import com.google.gson.JsonObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIInterface {

    @Headers({"Content-Type: application/json;charset=utf-8", "Accept: application/json;charset=utf-8", "Cache-Control: no-cache"})
    @POST("/server")
    Call<ResponseBody> apiCall(@Body JsonObject mJson);

    @Headers({"Content-Type: application/json;charset=utf-8", "Accept: application/json;charset=utf-8", "Cache-Control: no-cache"})
    @POST("/server")
    Call<LoginModel> loginAPI(@Body LoginModel mLogin);

    @Headers({"Content-Type: application/json;charset=utf-8", "Accept: application/json;charset=utf-8", "Cache-Control: no-cache"})
    @POST("/server")
    Call<CategoryModel> getCat(@Body CategoryModel mJson);

    @Headers({"Content-Type: application/json;charset=utf-8", "Accept: application/json;charset=utf-8", "Cache-Control: no-cache"})
    @POST("/server")
    Call<ProductModel> getProducts(@Body ProductModel mJson);

    @Headers({"Content-Type: application/json;charset=utf-8", "Accept: application/json;charset=utf-8", "Cache-Control: no-cache"})
    @POST("/server")
    Call<CartModel> getCART(@Body CartModel mJson);

    @Headers({"Content-Type: application/json;charset=utf-8", "Accept: application/json;charset=utf-8", "Cache-Control: no-cache"})
    @POST("/server")
    Call<OrderModel> getOrders(@Body OrderModel mJson);
}

