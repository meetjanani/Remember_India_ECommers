package com.rememberindia.remember4u.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.rememberindia.remember4u.Adapter.Past_Order_Product_Details_Adapter;
import com.rememberindia.remember4u.Bean.Past_Order_Product_Details.Past_Order_Product_By_Order_ID_Details_Bean;
import com.rememberindia.remember4u.Bean.Past_Order_Product_Details.Past_Order_Product_By_Order_ID_List_Bean;
import com.rememberindia.remember4u.Bean.Product_Details_Bean;
import com.rememberindia.remember4u.R;
import com.rememberindia.remember4u.Rest.ApiClient;
import com.rememberindia.remember4u.Rest.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Past_Order_Product_Details_Activity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TextView textViewOrderTotal, textViewExGstTotal, textViewGSTTotal;
    //SwipeRefreshLayout SwipeRefresh_Past_Order_Product_Details = null;
    LinearLayout lyt_root;
    List<Past_Order_Product_By_Order_ID_Details_Bean> Records;
    List<Product_Details_Bean> Filtered_Records ;
    Past_Order_Product_Details_Adapter adapter;
    String Order_Id = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past__order__product__details_);

//
//        SwipeRefresh_Past_Order_Product_Details = (SwipeRefreshLayout) findViewById(R.id.SwipeRefresh_Past_Order_Product_Details);
//        SwipeRefresh_Past_Order_Product_Details.setRefreshing(true);


        recyclerView = findViewById(R.id.recycler_view_PastOrder_Product_Details);
        textViewOrderTotal = findViewById(R.id.textViewOrderTotal);
        textViewExGstTotal = findViewById(R.id.textViewExGstTotal);
        textViewGSTTotal = findViewById(R.id.textViewGSTTotal);

        Order_Id = getIntent().getStringExtra("Order_ID");

        Product_List_Display_API_Call(Order_Id);


    }


    public void Product_List_Display_API_Call(String order_Id)
    {



        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<Past_Order_Product_By_Order_ID_List_Bean> call=apiService.Past_Order_Product_Details_By_Order_ID("Display_past_Order_By_OrderID_Old",
                order_Id);
        call.enqueue(new Callback<Past_Order_Product_By_Order_ID_List_Bean>() {
            @Override
            public void onResponse(Call<Past_Order_Product_By_Order_ID_List_Bean> call, Response<Past_Order_Product_By_Order_ID_List_Bean> response) {
                if (response.body().getStatus() == 1)
                {
                    Records = response.body().getData();
                    adapter = new Past_Order_Product_Details_Adapter(Past_Order_Product_Details_Activity.this, Records);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager((Past_Order_Product_Details_Activity.this)));
                    textViewOrderTotal.setText("Order Total : " + Records.get(0).getOrderTotal());
                    textViewExGstTotal.setText("Ex GST : " + Records.get(0).getEXGSTMRPTotal());
                    textViewGSTTotal.setText("GST MRP : " + Records.get(0).getGSTMRPTotal());
                  //  SwipeRefresh_Past_Order_Product_Details.setRefreshing(false);
                }
                else
                {
                    Toast.makeText(Past_Order_Product_Details_Activity.this, response.body().getMessage() + "", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<Past_Order_Product_By_Order_ID_List_Bean> call, Throwable t) {

            }
        });

    }
}
