package com.rememberindia.remember4u.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;

import com.rememberindia.remember4u.Activity.product.Category_Wise_Product_Activity;
import com.rememberindia.remember4u.Bean.Product_Details_Bean;
import com.rememberindia.remember4u.R;

import java.util.ArrayList;
import java.util.List;


public class Product_Category_Display_Adapter extends RecyclerView.Adapter<Product_Category_Display_Adapter.MyViewHolder> implements Filterable {


    private Context context;
    private List<Product_Details_Bean> productList;
    private List<Product_Details_Bean> productListFiltered;
    //private ContactsAdapterListener listener;


                        // Removed ContactsAdapterListener listener
    public Product_Category_Display_Adapter(Context context, List<Product_Details_Bean> productList , String string) {
        this.context = context;
      //  this.listener = listener;
        this.productList = productList;
        this.productListFiltered = productList;
    }

    @NonNull
    @Override
    public Product_Category_Display_Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.pattern_product_category, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull Product_Category_Display_Adapter.MyViewHolder holder, int i) {

        final Product_Details_Bean product = productListFiltered.get(i);
        holder.buttonCategoryName.setText(product.getName());
        holder.buttonCategoryName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(context , Category_Wise_Product_Activity.class);
                in.putExtra("CategoryID" , product.getCategoryID() + "");
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

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public Button buttonCategoryName;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            buttonCategoryName = itemView.findViewById(R.id.buttonCategoryName);

//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    listener.onContactSelected(productListFiltered.get(getAdapterPosition()));
//                }
//            });

        }
    }


//    public interface ContactsAdapterListener {
//        void onContactSelected(Product_Details_Bean product);
//    }

}
