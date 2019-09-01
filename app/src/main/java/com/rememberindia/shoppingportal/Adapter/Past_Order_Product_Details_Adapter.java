package com.rememberindia.shoppingportal.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rememberindia.shoppingportal.Activity.Past_Order_Product_Details_Activity;
import com.rememberindia.shoppingportal.Bean.Past_Order_Header_Data.Past_Order_Header_Details_Bean;
import com.rememberindia.shoppingportal.Bean.Past_Order_Product_Details.Past_Order_Product_By_Order_ID_Details_Bean;
import com.rememberindia.shoppingportal.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Past_Order_Product_Details_Adapter extends RecyclerView.Adapter<Past_Order_Product_Details_Adapter.MyViewHolder> {

    private Context context;
    private List<Past_Order_Product_By_Order_ID_Details_Bean> Records;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView product_name;
        TextView product_quantity;
        TextView product_price;
        ImageView product_image;
        LinearLayout LL_Main;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            product_name = itemView.findViewById(R.id.product_name);
            product_quantity = itemView.findViewById(R.id.product_quantity);
            product_price = itemView.findViewById(R.id.product_price);
            product_image = itemView.findViewById(R.id.product_image);

            LL_Main = (LinearLayout)itemView.findViewById(R.id.LL_Main);
        }
    }

    public Past_Order_Product_Details_Adapter(Context context, List<Past_Order_Product_By_Order_ID_Details_Bean> productList ) {
        this.context = context;
        //  this.listener = listener;
        this.Records = productList;
    }


    @NonNull
    @Override
    public Past_Order_Product_Details_Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_cart, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull Past_Order_Product_Details_Adapter.MyViewHolder holder, int i) {


        holder.product_name.setText(Records.get(i).getProductName());
        holder.product_quantity.setText(Records.get(i).getProductPrice() + " ₹ X " + Records.get(i).getQty());
        holder.product_price.setText(Records.get(i).getProductTotal() + " ₹");

        Picasso.with(context)
                .load(Records.get(i).getURL1() + "")
                .placeholder(R.drawable.ic_menu_gallery)
                .resize(250, 250)
                .centerCrop()
                //.transform(transformation)
                .into(holder.product_image);

    }

    @Override
    public int getItemCount() {
        return Records.size();
    }


}
