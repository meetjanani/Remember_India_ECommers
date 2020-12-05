package com.rememberindia.remember4u.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rememberindia.remember4u.Bean.DB_Helper_Offline.Order_Summery_Ofline_Bean;
import com.rememberindia.remember4u.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Cart_Adapter  extends RecyclerView.Adapter<Cart_Adapter.ViewHolder> {

    private Context context;
    private List<Order_Summery_Ofline_Bean> arrayCart;

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView product_name;
        TextView product_quantity;
        TextView product_price;
        ImageView product_image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            product_name = itemView.findViewById(R.id.product_name);
            product_quantity = itemView.findViewById(R.id.product_quantity);
            product_price = itemView.findViewById(R.id.product_price);
            product_image = itemView.findViewById(R.id.product_image);

        }
    }

    public Cart_Adapter(Context context, List<Order_Summery_Ofline_Bean> arrayCart) {
        this.context = context;
        this.arrayCart = arrayCart;
    }


    @NonNull
    @Override
    public Cart_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull Cart_Adapter.ViewHolder holder, int i) {

        holder.product_name.setText(arrayCart.get(i).getItem_name());
        holder.product_quantity.setText(arrayCart.get(i).getBase_price() + " ₹ X " + arrayCart.get(i).getQty());
        holder.product_price.setText(arrayCart.get(i).getTotal_price() + " ₹");

        Picasso.with(context)
                .load(arrayCart.get(i).getVarient_name() + "")
                .placeholder(R.mipmap.ic_launcher_foreground)
                .resize(250, 250)
                .centerCrop()
                //.transform(transformation)
                .into(holder.product_image);
    }

    @Override
    public int getItemCount() {
        return arrayCart.size();
    }


}
