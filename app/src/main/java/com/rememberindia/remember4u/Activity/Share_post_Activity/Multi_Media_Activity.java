package com.rememberindia.remember4u.Activity.Share_post_Activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.rememberindia.remember4u.Adapter.Shared_Post_Adapter_New;
import com.rememberindia.remember4u.Bean.Shared_Post_Bean.Shared_Post_Details_Bean;
import com.rememberindia.remember4u.Bean.Shared_Post_Bean.Shared_Post_List_Bean;
import com.rememberindia.remember4u.R;
import com.rememberindia.remember4u.Rest.ApiClient;
import com.rememberindia.remember4u.Rest.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Multi_Media_Activity extends AppCompatActivity {

    List<Shared_Post_Details_Bean> Records_Post;
    Shared_Post_Adapter_New adapter_Post;
    private RecyclerView recyclerView_Post;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi__media_);

        recyclerView_Post = findViewById(R.id.recycler_view_Shared_Post);
        Shared_Post_List_Display_API_Call();
        
    }

    public void Shared_Post_List_Display_API_Call() {

        final ProgressDialog progress = new ProgressDialog(Multi_Media_Activity.this);
        progress.setMessage("Loading Data) ");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.show();


        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<Shared_Post_List_Bean> call = apiService.Display_Shared_Post("Display_All_Post");
        call.enqueue(new Callback<Shared_Post_List_Bean>() {
            @Override
            public void onResponse(Call<Shared_Post_List_Bean> call, Response<Shared_Post_List_Bean> response) {
                if (response.body().getStatus() == 1) {
                    Records_Post = response.body().getData();
                    adapter_Post = new Shared_Post_Adapter_New(Multi_Media_Activity.this, Records_Post);
                    recyclerView_Post.setAdapter(adapter_Post);
                    recyclerView_Post.setLayoutManager(new LinearLayoutManager(Multi_Media_Activity.this, LinearLayoutManager.VERTICAL, false));
                    //  SwipeRefresh_Past_Order.setRefreshing(false);
                    progress.dismiss();
                } else {
                    Toast.makeText(Multi_Media_Activity.this, response.body().getMessage() + "", Toast.LENGTH_SHORT).show();
                    progress.dismiss();
                }


            }

            @Override
            public void onFailure(Call<Shared_Post_List_Bean> call, Throwable t) {

            }
        });

    }
}
