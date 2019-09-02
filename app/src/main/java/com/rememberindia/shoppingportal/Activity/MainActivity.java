package com.rememberindia.shoppingportal.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.LinearLayout;

import com.rememberindia.shoppingportal.Activity.Share_post_Activity.Upload_Image_Activity;
import com.rememberindia.shoppingportal.Activity.Share_post_Activity.Upload_Video_Activity;
import com.rememberindia.shoppingportal.Payment_Process.R_Pay_Activity;
import com.rememberindia.shoppingportal.R;
import com.rememberindia.shoppingportal.Utility.My_Profile_Activity;
import com.rememberindia.shoppingportal.Utility.Session_Manager;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

//    private RecyclerView recyclerView;
//    private List<Product_List_Bean> productList;
//    private Product_List_Display_Adapter mAdapter;
//    private SearchView searchView;
//    SwipeRefreshLayout swipeRefreshLayout = null;
    LinearLayout lyt_root;
    LinearLayout LL_Media , LL_Shooping_Mall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Currently Active Offer Page Will Comming Soon... Stay Tuned With Us    ", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        LL_Media = (LinearLayout) findViewById(R.id.LL_Media);
        LL_Shooping_Mall = (LinearLayout)findViewById(R.id.LL_Shooping_Mall);


        LL_Media.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(MainActivity.this , Shared_Post_Activity.class);
                startActivity(in);
            }
        });

        LL_Shooping_Mall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(MainActivity.this , Product_List_Activity.class);
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
            Intent in = new Intent(MainActivity.this , Cart_Activity.class);
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

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_shoppingmall) {
            Intent in = new Intent(MainActivity.this , Product_List_Activity.class);
            startActivity(in);
        } else if (id == R.id.nav_cart) {
            Intent in = new Intent(MainActivity.this , Cart_Activity.class);
            startActivity(in);

        } else if (id == R.id.nav_pastorder) {
            Intent in = new Intent(MainActivity.this , Past_Order_Header_Data_Activity.class);
            startActivity(in);
        }
        else if (id == R.id.nav_post_photo) {
            Intent in = new Intent(MainActivity.this , Upload_Image_Activity.class);
            startActivity(in);
        }

        else if (id == R.id.nav_post_video) {
            Intent in = new Intent(MainActivity.this , Upload_Video_Activity.class);
            startActivity(in);
        }
        else if (id == R.id.nav_my_profile) {
            Intent in = new Intent(MainActivity.this , My_Profile_Activity.class);
            startActivity(in);
        }
        else if (id == R.id.nav_logout) {
            Session_Manager.Save_Login_Data(this , "False" , "","","","",
                                    "","","","","","");
            finish();
            Intent in = new Intent(MainActivity.this , Login_Activity.class);
            startActivity(in);
        }
        else if (id == R.id.nav_share) {
            Intent in = new Intent(MainActivity.this , R_Pay_Activity.class);
            startActivity(in);

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

//    @Override
//    public void onContactSelected(Product_Details_Bean product) {
////        Intent intent = new Intent(MainActivity.this, ActivityProductDetail.class);
////        intent.putExtra("product_id", product.getProduct_id());
////        intent.putExtra("title", product.getProduct_name());
////        intent.putExtra("image", product.getProduct_image());
////        intent.putExtra("product_price", product.getProduct_price());
////        intent.putExtra("product_description", product.getProduct_description());
////        intent.putExtra("product_quantity", product.getProduct_quantity());
////        intent.putExtra("product_status", product.getProduct_status());
////        intent.putExtra("currency_code", product.getCurrency_code());
////        intent.putExtra("category_name", product.getCategory_name());
////        startActivity(intent);
//
//    }


}
