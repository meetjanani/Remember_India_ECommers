package com.rememberindia.remember4u.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.rememberindia.remember4u.Adapter.Past_Order_Header_Adapter;
import com.rememberindia.remember4u.Bean.Past_Order_Header_Data.Past_Order_Header_Details_Bean;
import com.rememberindia.remember4u.Bean.Past_Order_Header_Data.Past_Order_Header_List_Bean;
import com.rememberindia.remember4u.Bean.Product_Details_Bean;
import com.rememberindia.remember4u.R;
import com.rememberindia.remember4u.Rest.ApiClient;
import com.rememberindia.remember4u.Rest.ApiInterface;
import com.rememberindia.remember4u.Utility.Session_Manager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Past_Order_Header_Data_Activity extends AppCompatActivity {

    private RecyclerView recyclerView;
    //SwipeRefreshLayout SwipeRefresh_Past_Order = null;
    LinearLayout lyt_root;
    List<Past_Order_Header_Details_Bean> Records;
    List<Product_Details_Bean> Filtered_Records ;
    Past_Order_Header_Adapter adapter;
    View lyt_empty_history;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past__order__header__data_);

        setTitle("Past Order");
//        SwipeRefresh_Past_Order = (SwipeRefreshLayout) findViewById(R.id.SwipeRefresh_Past_Order);
//        SwipeRefresh_Past_Order.setRefreshing(true);


        recyclerView = findViewById(R.id.recycler_view_PastOrder);
        lyt_empty_history = findViewById(R.id.lyt_empty_history);

        Product_List_Display_API_Call();

    }

    public void Product_List_Display_API_Call()
    {
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<Past_Order_Header_List_Bean> call=apiService.Past_Order_Header_Data_By_User_ID("Display_past_Orders_List",
                Session_Manager.getUser_ID(Past_Order_Header_Data_Activity.this));
        call.enqueue(new Callback<Past_Order_Header_List_Bean>() {
            @Override
            public void onResponse(Call<Past_Order_Header_List_Bean> call, Response<Past_Order_Header_List_Bean> response) {
                if (response.body().getStatus() == 1)
                {
                    Records = response.body().getData();
                    adapter = new Past_Order_Header_Adapter(Past_Order_Header_Data_Activity.this, Records);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager((Past_Order_Header_Data_Activity.this)));

                    if(Records.size() > 0){
                        lyt_empty_history.setVisibility(View.GONE);
                    }
                  //  SwipeRefresh_Past_Order.setRefreshing(false);
                }
                else
                {
                    Toast.makeText(Past_Order_Header_Data_Activity.this, response.body().getMessage() + "", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<Past_Order_Header_List_Bean> call, Throwable t) {

            }
        });

    }


}
