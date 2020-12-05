package com.rememberindia.remember4u.Activity.product;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.rememberindia.remember4u.Adapter.Product_List_Display_Adapter;
import com.rememberindia.remember4u.Bean.Product_Details_Bean;
import com.rememberindia.remember4u.Bean.Product_List_Bean;
import com.rememberindia.remember4u.R;
import com.rememberindia.remember4u.Rest.ApiClient;
import com.rememberindia.remember4u.Rest.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Category_Wise_Product_Activity extends AppCompatActivity {

    LinearLayout lyt_root;
    List<Product_Details_Bean> Records;
    List<Product_Details_Bean> Filtered_Records ;
    Product_List_Display_Adapter adapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category__wise__product_);
        recyclerView = findViewById(R.id.recycler_view_Product_List);
        Product_List_Display_API_Call(getIntent().getStringExtra("CategoryID"));
    }

    public void Product_List_Display_API_Call(String CategoryID)
    {
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<Product_List_Bean> call=apiService.Display_ProductCategoryWise("Display_Product_Category_Wise", CategoryID);
        call.enqueue(new Callback<Product_List_Bean>() {
            @Override
            public void onResponse(Call<Product_List_Bean> call, Response<Product_List_Bean> response) {
                if (response.body().getStatus() == 1)
                {
                    Records = response.body().getData();
                    adapter = new Product_List_Display_Adapter(Category_Wise_Product_Activity.this, Records, "");
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new GridLayoutManager((Category_Wise_Product_Activity.this),2));
                }
                else
                {
                    Toast.makeText(Category_Wise_Product_Activity.this, response.body().getMessage() + "", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Product_List_Bean> call, Throwable t) {
            }
        });
    }
}
