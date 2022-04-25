package com.example.medical.adapters;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medical.R;
import com.example.medical.models.OrderModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MyOrderAdapter extends RecyclerView.Adapter<MyOrderAdapter.ViewHolder> {

    Context mContext;
    ArrayList<OrderModel.Item> itemArrayList;

    public MyOrderAdapter(Context mContext, ArrayList<OrderModel.Item> itemArrayList) {
        this.mContext = mContext;
        this.itemArrayList = itemArrayList;
    }

    @NonNull
    @Override
    public MyOrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_order, parent, false);
        return new MyOrderAdapter.ViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyOrderAdapter.ViewHolder holder, int position) {
        holder.mTextOrderId.setText("#" + itemArrayList.get(position).getOrderId());
        holder.mTextProductName.setText("Item Name: " + itemArrayList.get(position).getName());
        holder.mTextPrice.setText("Item Price: " + itemArrayList.get(position).getProductPrice());

        long timeStamp = Long.parseLong(itemArrayList.get(position).getCartId());
        String date = getDate(timeStamp);
        holder.mTextDate.setText("Order Date: " + date);

    }

    private String getDate(long time) {
        try{
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
            Date netDate = (new Date(time));
            return sdf.format(netDate);
        }
        catch(Exception ex){
            return "xx";
        }
    }

    @Override
    public int getItemCount() {
        return itemArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTextOrderId, mTextProductName, mTextPrice, mTextDate;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mTextOrderId = itemView.findViewById(R.id.text_order_id);
            mTextProductName = itemView.findViewById(R.id.text_product_name);
            mTextPrice = itemView.findViewById(R.id.text_product_price);
            mTextDate = itemView.findViewById(R.id.text_order_date);
        }
    }
}
