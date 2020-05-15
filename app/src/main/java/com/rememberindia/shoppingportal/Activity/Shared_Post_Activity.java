package com.rememberindia.shoppingportal.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.klinker.android.simple_videoview.SimpleVideoView;
import com.rememberindia.shoppingportal.Activity.Share_post_Activity.Upload_Video_Activity;
import com.rememberindia.shoppingportal.Adapter.Shared_Post_Adapter_Old;
import com.rememberindia.shoppingportal.Bean.Shared_Post_Bean.Shared_Post_Details_Bean;
import com.rememberindia.shoppingportal.Bean.Shared_Post_Bean.Shared_Post_List_Bean;
import com.rememberindia.shoppingportal.R;
import com.rememberindia.shoppingportal.Rest.ApiClient;
import com.rememberindia.shoppingportal.Rest.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Shared_Post_Activity extends AppCompatActivity {

    SimpleVideoView video_view;
    Button Btn_Stop , Btn_Play;

    private RecyclerView recyclerView;
    //SwipeRefreshLayout SwipeRefresh_Past_Order = null;
    LinearLayout lyt_root;
    List<Shared_Post_Details_Bean> Records;
    Shared_Post_Adapter_Old adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared__post_);

//        Btn_Stop = findViewById(R.id.Btn_Stop);
//        Btn_Play = findViewById(R.id.Btn_Play);
//        video_view = findViewById(R.id.video_view);

        FloatingActionButton fab = findViewById(R.id.fab_share_post);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(Shared_Post_Activity.this , Upload_Video_Activity.class);
                startActivity(in);
            }
        });

        recyclerView = findViewById(R.id.recycler_view_Shared_Post);

        Product_List_Display_API_Call();

//
        //recycler_view_Shared_Post



    }


//    @Override
//    public void onStop() {
//        super.onStop();
//        video_view.release();
//    }


    public void Product_List_Display_API_Call()
    {

        final ProgressDialog progress = new ProgressDialog(Shared_Post_Activity.this);
        progress.setMessage("Loading Data) ");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.show();


        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<Shared_Post_List_Bean> call=apiService.Display_Shared_Post("Display_All_Post");
        call.enqueue(new Callback<Shared_Post_List_Bean>() {
            @Override
            public void onResponse(Call<Shared_Post_List_Bean> call, Response<Shared_Post_List_Bean> response) {
                // Toast.makeText(Past_Order_Header_Data_Activity.this, response.body().getMessage() + "", Toast.LENGTH_SHORT).show();
                if (response.body().getStatus() == 1)
                {
                    Records = response.body().getData();
                    adapter = new Shared_Post_Adapter_Old(Shared_Post_Activity.this, Records);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(Shared_Post_Activity.this, LinearLayoutManager.HORIZONTAL, false));
                    //  SwipeRefresh_Past_Order.setRefreshing(false);
                    progress.dismiss();
                }
                else
                {
                    Toast.makeText(Shared_Post_Activity.this, response.body().getMessage() + "", Toast.LENGTH_SHORT).show();
                    progress.dismiss();
                }


            }

            @Override
            public void onFailure(Call<Shared_Post_List_Bean> call, Throwable t) {

            }
        });

    }

}

//        video_view = findViewById(R.id.video_view);
//        video_view.start("http://rememberindia.in/w_1.mp4");
//        video_view.release();

