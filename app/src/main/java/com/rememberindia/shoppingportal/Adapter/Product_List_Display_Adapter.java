package com.rememberindia.shoppingportal.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.service.autofill.Transformation;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.rememberindia.shoppingportal.Activity.Product_Details_Activity;
import com.rememberindia.shoppingportal.Bean.Product_Details_Bean;
import com.rememberindia.shoppingportal.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import com.squareup.picasso.Picasso;


public class Product_List_Display_Adapter extends RecyclerView.Adapter<Product_List_Display_Adapter.MyViewHolder> implements Filterable {


    private Context context;
    private List<Product_Details_Bean> productList;
    private List<Product_Details_Bean> productListFiltered;
    //private ContactsAdapterListener listener;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView Tv_Brand_Name , product_name, product_price , Tv_Discount_Price;
        public ImageView product_image;
        public RelativeLayout RL_Main;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            Tv_Brand_Name = itemView.findViewById(R.id.Tv_Brand_Name);
            product_name = itemView.findViewById(R.id.product_name);
            product_price = itemView.findViewById(R.id.product_price);
            Tv_Discount_Price = itemView.findViewById(R.id.Tv_Discount_Price);
            product_image = itemView.findViewById(R.id.category_image);
            RL_Main = itemView.findViewById(R.id.RL_Main);

//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    listener.onContactSelected(productListFiltered.get(getAdapterPosition()));
//                }
//            });

        }
    }
                        // Removed ContactsAdapterListener listener
    public Product_List_Display_Adapter(Context context, List<Product_Details_Bean> productList , String string) {
        this.context = context;
      //  this.listener = listener;
        this.productList = productList;
        this.productListFiltered = productList;
    }



    @NonNull
    @Override
    public Product_List_Display_Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.pattern_product, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull Product_List_Display_Adapter.MyViewHolder holder, int i) {

        final Product_Details_Bean product = productListFiltered.get(i);
        holder.Tv_Brand_Name.setText("Brand Name");
        holder.product_name.setText(product.getName());

//        if (Config.ENABLE_DECIMAL_ROUNDING) {
//            String price = String.format(Locale.GERMAN, "%1$,.0f", ((Product_Details_Bean) product).getMRP1());
//            holder.product_price.setText(price + " " + product.getCurrency_code());
//        } else {
//            holder.product_price.setText(product.getProduct_price() + " " + product.getCurrency_code());
//        }


        holder.product_price.setText(product.getMRP1());
        holder.Tv_Discount_Price.setText(" " + product.getMRP2() + " ₹ For Drs");
        holder.product_price.setPaintFlags( holder.product_price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        com.squareup.picasso.Transformation transformation = new RoundedTransformationBuilder()
                .cornerRadiusDp(6)
                .oval(false)
                .build();

//        GlideApp.with(context)
//                .load("http://via.placeholder.com/300.png")
//                //.override(300, 200)
//                .into(holder.product_image);

        Picasso.with(context)
                .load(product.getURL1())
                .placeholder(R.drawable.rememberme_logo)
                .resize(250, 250)
                .centerCrop()
                .transform(transformation)
                .into(holder.product_image);

        holder.RL_Main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(context , Product_Details_Activity.class);
                in.putExtra("Product_ID" , product.getID() + "");
                context.startActivity(in);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productListFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    productListFiltered = productList;
                } else {
                    List<Product_Details_Bean> filteredList = new ArrayList<>();
                    for (Product_Details_Bean row : productList) {
                        if (row.getName().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }
                    productListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = productListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                productListFiltered = (ArrayList<Product_Details_Bean>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }


//    public interface ContactsAdapterListener {
//        void onContactSelected(Product_Details_Bean product);
//    }

}
