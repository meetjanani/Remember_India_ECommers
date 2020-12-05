package com.rememberindia.remember4u.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.rememberindia.remember4u.Activity.Share_post_Activity.Multi_Media_Activity;
import com.rememberindia.remember4u.Adapter.Product_Category_Display_Adapter;
import com.rememberindia.remember4u.Adapter.Shared_Post_Adapter_New;
import com.rememberindia.remember4u.Bean.Common_Insert_Response_Bean;
import com.rememberindia.remember4u.Bean.Product_Details_Bean;
import com.rememberindia.remember4u.Bean.Product_List_Bean;
import com.rememberindia.remember4u.Bean.Shared_Post_Bean.Shared_Post_Details_Bean;
import com.rememberindia.remember4u.Bean.Shared_Post_Bean.Shared_Post_List_Bean;
import com.rememberindia.remember4u.BuildConfig;
import com.rememberindia.remember4u.R;
import com.rememberindia.remember4u.Rest.ApiClient;
import com.rememberindia.remember4u.Rest.ApiInterface;
import com.rememberindia.remember4u.Utility.My_Profile_Activity;
import com.rememberindia.remember4u.Utility.Session_Manager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    //    private RecyclerView recyclerView;
//    private List<Product_List_Bean> productList;
//    private Product_List_Display_Adapter mAdapter;
//    private SearchView searchView;
//    SwipeRefreshLayout swipeRefreshLayout = null;
    LinearLayout lyt_root;
    LinearLayout LL_MultiMedia_Galary, LL_Shooping_Mall;
    List<Shared_Post_Details_Bean> Records_Post;
    List<Product_Details_Bean> Records_Product;
    //SwipeRefreshLayout SwipeRefresh_Past_Order = null;
    Shared_Post_Adapter_New adapter_Post;
    Product_Category_Display_Adapter adapter_Product;
    Button buttonAbountUs, buttonArticles, buttonVideosToLook, buttonShopNow;
    private RecyclerView recyclerView_Post, recyclerView_Product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        LL_MultiMedia_Galary = findViewById(R.id.LL_MultiMedia_Galary);
        LL_Shooping_Mall = findViewById(R.id.LL_Shooping_Mall);
        buttonAbountUs = findViewById(R.id.buttonAbountUs);
        buttonArticles = findViewById(R.id.buttonArticles);
        buttonVideosToLook = findViewById(R.id.buttonVideosToLook);
        buttonShopNow = findViewById(R.id.buttonShopNow);


        buttonAbountUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "http://rememberindia.com/aboutus";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        buttonArticles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(MainActivity.this, ArticlesActivity.class);
                startActivity(in);
            }
        });
        buttonVideosToLook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(MainActivity.this, Multi_Media_Activity.class);
                startActivity(in);
            }
        });

        buttonShopNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(MainActivity.this, Product_List_Activity.class);
                startActivity(in);
            }
        });


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        recyclerView_Post = findViewById(R.id.recycler_view_Shared_Post);
        recyclerView_Product = findViewById(R.id.recycler_view_Product_List);

        Product_List_Display_API_Call();
        //                    API Call for POst
        // Shared_Post_List_Display_API_Call();
    }


    public void Product_List_Display_API_Call() {
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<Product_List_Bean> call = apiService.Display_ProductList("Display_Product_Category_List");
        call.enqueue(new Callback<Product_List_Bean>() {
            @Override
            public void onResponse(Call<Product_List_Bean> call, Response<Product_List_Bean> response) {
                if (response.body().getStatus() == 1) {
                    Records_Product = response.body().getData();
                    adapter_Product = new Product_Category_Display_Adapter(MainActivity.this, Records_Product, "");
                    recyclerView_Product.setAdapter(adapter_Product);
                    recyclerView_Product.setLayoutManager(new GridLayoutManager((MainActivity.this), 2));
                    checkPincode();
                } else {
                    Toast.makeText(MainActivity.this, response.body().getMessage() + "", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Product_List_Bean> call, Throwable t) {
            }
        });
    }

    public void checkPincode() {
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Call<Common_Insert_Response_Bean> call = apiService.CheckPincode("Pincode_Is_Valid",
                Session_Manager.getPincode(MainActivity.this).toString());
        call.enqueue(new Callback<Common_Insert_Response_Bean>() {
            @Override
            public void onResponse(Call<Common_Insert_Response_Bean> call, Response<Common_Insert_Response_Bean> response) {

                if (response.body().getStatus() == 1) {
                    Session_Manager.setisPincodeValid(MainActivity.this, true);
                } else {
                    Session_Manager.setisPincodeValid(MainActivity.this, false);
                }

            }

            @Override
            public void onFailure(Call<Common_Insert_Response_Bean> call, Throwable t) {
            }
        });
    }
    
    public void Shared_Post_List_Display_API_Call() {

        final ProgressDialog progress = new ProgressDialog(MainActivity.this);
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
                    adapter_Post = new Shared_Post_Adapter_New(MainActivity.this, Records_Post);
                    recyclerView_Post.setAdapter(adapter_Post);
                    recyclerView_Post.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false));
                    //  SwipeRefresh_Past_Order.setRefreshing(false);
                    progress.dismiss();
                } else {
                    Toast.makeText(MainActivity.this, response.body().getMessage() + "", Toast.LENGTH_SHORT).show();
                    progress.dismiss();
                }


            }

            @Override
            public void onFailure(Call<Shared_Post_List_Bean> call, Throwable t) {

            }
        });

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_cart) {
            Intent in = new Intent(MainActivity.this, Cart_Activity.class);
            startActivity(in);
            return true;

        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        //
        //        if (id == R.id.nav_home) {
        //            // Handle the camera action
        //        }
        if (id == R.id.nav_shoppingmall) {
            Intent in = new Intent(MainActivity.this, Product_List_Activity.class);
            startActivity(in);
        } else if (id == R.id.nav_cart) {
            Intent in = new Intent(MainActivity.this, Cart_Activity.class);
            startActivity(in);

        } else if (id == R.id.nav_pastorder) {
            Intent in = new Intent(MainActivity.this, Past_Order_Header_Data_Activity.class);
            startActivity(in);
        }
//        else if (id == R.id.nav_post_photo) {
//            Intent in = new Intent(MainActivity.this, Upload_Image_Activity.class);
//            startActivity(in);
//        } else if (id == R.id.nav_post_video) {
//            Intent in = new Intent(MainActivity.this, Upload_Video_Activity.class);
//            startActivity(in);
//        }
        else if (id == R.id.nav_my_profile) {
            Intent in = new Intent(MainActivity.this, My_Profile_Activity.class);
            startActivity(in);
        }  else if (id == R.id.nav_logout) {
            Session_Manager.Save_Login_Data(this, "False", "", "", "", "",
                    "", "", "", "", "", "", "");
            finish();
            Intent in = new Intent(MainActivity.this, Login_Activity.class);
            startActivity(in);
        } else if (id == R.id.nav_share) {
            Intent share = new Intent(android.content.Intent.ACTION_SEND);
            share.setType("text/plain");
            share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
            share.putExtra(Intent.EXTRA_SUBJECT, "Android App For E-Commerce Shopping Portal");
            share.putExtra(Intent.EXTRA_TEXT, "Link is here for Remember me india App :https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID + "\n\n");
            startActivity(Intent.createChooser(share, "Share link!"));
        }
//        else if (id == R.id.nav_send) {
//
//        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
