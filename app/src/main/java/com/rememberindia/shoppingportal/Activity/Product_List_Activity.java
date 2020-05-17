package com.rememberindia.shoppingportal.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.rememberindia.shoppingportal.Adapter.Product_List_Display_Adapter;
import com.rememberindia.shoppingportal.Bean.Product_Details_Bean;
import com.rememberindia.shoppingportal.Bean.Product_List_Bean;
import com.rememberindia.shoppingportal.R;
import com.rememberindia.shoppingportal.Rest.ApiClient;
import com.rememberindia.shoppingportal.Rest.ApiInterface;
import com.rememberindia.shoppingportal.Utility.Utils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Product_List_Activity extends AppCompatActivity {

    private RecyclerView recyclerView;
   // private SearchView searchView;
    SwipeRefreshLayout swipeRefreshLayout = null;
    LinearLayout lyt_root;
    List<Product_Details_Bean> Records;
    List<Product_Details_Bean> Filtered_Records ;
    Product_List_Display_Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product__list_);

        setTitle("Shopping Mall");
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setRefreshing(true);
        recyclerView = findViewById(R.id.recycler_view_Product_List);
        Product_List_Display_API_Call();
        onRefresh();


    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        MenuItem searchViewItem = menu.findItem(R.id.app_bar_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchViewItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchView.clearFocus();
                Search_Filter_Data(query);
                return false;

            }

            @Override
            public boolean onQueryTextChange(String s) {
                Search_Filter_Data(s);
                return false;
            }

        });
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
            Intent in = new Intent(Product_List_Activity.this , Cart_Activity.class);
            startActivity(in);
            return true;
        }
                if (id == R.id.home) {
                    onBackPressed();
                    return true;
                }
        return super.onOptionsItemSelected(item);
    }


    public void Product_List_Display_API_Call()
    {
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<Product_List_Bean> call=apiService.Display_ProductList("Display_Product_List");
        call.enqueue(new Callback<Product_List_Bean>() {
            @Override
            public void onResponse(Call<Product_List_Bean> call, Response<Product_List_Bean> response) {
                if (response.body().getStatus() == 1)
                {
                    Records = response.body().getData();
                    adapter = new Product_List_Display_Adapter(Product_List_Activity.this, Records, "");
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new GridLayoutManager((Product_List_Activity.this),2));
                    swipeRefreshLayout.setRefreshing(false);
                }
                else
                {
                    Toast.makeText(Product_List_Activity.this, response.body().getMessage() + "", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Product_List_Bean> call, Throwable t) {
            }
        });
    }

    private void onRefresh() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //  productList.clear();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (Utils.isNetworkAvailable(Product_List_Activity.this)) {
                            swipeRefreshLayout.setRefreshing(false);
                            Product_List_Display_API_Call();
                        } else {
                            swipeRefreshLayout.setRefreshing(false);
                            Toast.makeText(Product_List_Activity.this, getResources().getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
                        }

                    }
                }, 1500);
            }
        });
    }


    public void Search_Filter_Data(String Filter_Type)
    {
        Filtered_Records = new ArrayList<Product_Details_Bean>();

            for (int i=0 ; i < Records.size() ; i++)
            {
                if (Records.get(i).getName().toString().toLowerCase().contains(Filter_Type.toLowerCase()))
                {
                    Filtered_Records.add(Records.get(i));
                }
            }
            adapter = new Product_List_Display_Adapter(Product_List_Activity.this, Filtered_Records , "");
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new GridLayoutManager((Product_List_Activity.this),2));
        }
}
