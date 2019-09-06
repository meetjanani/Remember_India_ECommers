package com.rememberindia.shoppingportal.Activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.rememberindia.shoppingportal.Adapter.Cart_Adapter;
import com.rememberindia.shoppingportal.Bean.DB_Helper_Offline.Order_Summery_Db_Helper;
import com.rememberindia.shoppingportal.Bean.DB_Helper_Offline.Order_Summery_Ofline_Bean;
import com.rememberindia.shoppingportal.R;
import com.rememberindia.shoppingportal.Utility.Session_Manager;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Cart_Activity extends AppCompatActivity {

    RecyclerView recyclerView;
    View lyt_empty_cart;
    RelativeLayout lyt_order;
    Order_Summery_Db_Helper dbhelper;
    public Cart_Adapter adapterCart;
    double total_price;
    final int CLEAR_ALL_ORDER = 0;
    final int CLEAR_ONE_ORDER = 1;
    int FLAG;
    int ID;
    double str_tax;
    String str_currency_code;
    Button btn_checkout, btn_continue;
   List<Order_Summery_Ofline_Bean> data;
    public static ArrayList<Integer> product_id = new ArrayList<Integer>();
    public static ArrayList<String> product_name = new ArrayList<String>();
    public static ArrayList<Integer> product_quantity = new ArrayList<Integer>();
    public static ArrayList<String> currency_code = new ArrayList<String>();
    public static ArrayList<Double> sub_total_price = new ArrayList<Double>();
    public static ArrayList<String> product_image = new ArrayList<String>();
    List<Order_Summery_Ofline_Bean> arrayCart;
    View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_);

        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(R.string.title_cart);
        }


        recyclerView = findViewById(R.id.recycler_view);

        lyt_empty_cart = findViewById(R.id.lyt_empty_history);
        btn_checkout = findViewById(R.id.btn_checkout);
        btn_checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbhelper.close();
                //                      // ***************************

                if (Session_Manager.getIs_Login(Cart_Activity.this).equals("True"))
                {
                    Intent intent = new Intent(Cart_Activity.this, Checkout_Activity.class);
                    intent.putExtra("tax", str_tax);
                    intent.putExtra("currency_code", str_currency_code);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(Cart_Activity.this, "Please Login Befor Placing Order With US", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btn_continue = findViewById(R.id.btn_continue);
        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
       // recyclerView.addItemDecoration(new MyDividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL, 86));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        lyt_order = (RelativeLayout) findViewById(R.id.lyt_history);
        dbhelper = new Order_Summery_Db_Helper(this);
        arrayCart = dbhelper.getAllOrder();
        adapterCart = new Cart_Adapter(this, arrayCart);


        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, final int position) {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        showClearDialog(CLEAR_ONE_ORDER, product_id.get(position));
                    }
                }, 400);

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        new getDataTask().execute();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_cart, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:
                finish();
                return true;

            case R.id.clear:
                if (product_id.size() > 0) {
                    showClearDialog(CLEAR_ALL_ORDER, 1111);
                } else {
                    Snackbar.make(view, R.string.msg_empty_cart, Snackbar.LENGTH_SHORT).show();
                }
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }



    public void clearData() {
        product_id.clear();
        product_name.clear();
        product_quantity.clear();
        sub_total_price.clear();
        currency_code.clear();
        product_image.clear();
    }

    public class getDataTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... arg0) {
            getDataFromDatabase();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            String _price = String.format(Locale.GERMAN, "%1$,.0f", total_price);
            String _tax = String.format(Locale.GERMAN, "%1$,.0f", str_tax);

            TextView txt_total_price = findViewById(R.id.txt_total_price);
            TextView txt_tax = findViewById(R.id.txt_tax);

//            if (Config.ENABLE_DECIMAL_ROUNDING) {
//                txt_total_price.setText(getResources().getString(R.string.txt_total) + " " + _price + " " + str_currency_code);
//                txt_tax.setText(getResources().getString(R.string.txt_tax) + " " + _tax + " %");
//            } else {
//                txt_total_price.setText(getResources().getString(R.string.txt_total) + " " + total_price + " " + str_currency_code);
//                txt_tax.setText(getResources().getString(R.string.txt_tax) + " " + str_tax + " %");
//            }

            txt_total_price.setText(getResources().getString(R.string.txt_total) + " " + total_price + " ₹");
            txt_tax.setText( "Inclusive All Tax "); //getResources().getString(R.string.txt_tax) // + str_tax + " %"

            if (product_id.size() > 0) {
                lyt_order.setVisibility(View.VISIBLE);

                arrayCart = dbhelper.getAllOrder();
                adapterCart = new Cart_Adapter(Cart_Activity.this, arrayCart);
                recyclerView.setAdapter(adapterCart);

            } else {
                lyt_empty_cart.setVisibility(View.VISIBLE);
            }

        }
    }

    public void getDataFromDatabase() {

        total_price = 0;
        clearData();
        data = dbhelper.getAllOrder();

        for (int i = 0; i < data.size(); i++) {
            Order_Summery_Ofline_Bean order_summery_ofline_bean = data.get(i);

            product_id.add(Integer.parseInt(order_summery_ofline_bean.getItem_id()));
            product_name.add(order_summery_ofline_bean.getItem_name());
            product_quantity.add(Integer.parseInt(order_summery_ofline_bean.getQty()));
            sub_total_price.add(Double.parseDouble(order_summery_ofline_bean.getTotal_price()));

            total_price += sub_total_price.get(i);

//            currency_code.add(row.get(4).toString());
//            product_image.add(row.get(5).toString());
        }

        total_price += (total_price * (str_tax / 100));

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public void showClearDialog(int flag, int id) {
        FLAG = flag;
        ID = id;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.confirm);
        switch (FLAG) {
            case 0:
                builder.setMessage(getString(R.string.clear_all_order));
                break;
            case 1:
                builder.setMessage(getString(R.string.clear_one_order));
                break;
        }
        builder.setCancelable(false);
        builder.setPositiveButton(getResources().getString(R.string.dialog_option_yes), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                switch (FLAG) {
                    case 0:
                        dbhelper.deleteAll();
                        clearData();
                        new getDataTask().execute();
                        break;
                    case 1:
                        dbhelper.deleteByItemID(ID);
                        clearData();
                        new getDataTask().execute();
                        break;
                }
            }
        });

        builder.setNegativeButton(getResources().getString(R.string.dialog_option_no), new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();

    }

    class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener) {

            this.clickListener = clickListener;

            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildAdapterPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildAdapterPosition(child));
            }

            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }

    public interface ClickListener {
        public void onClick(View view, int position);

        public void onLongClick(View view, int position);
    }


    @Override
    protected void onResume() {
        super.onResume();
        new getDataTask().execute();
    }
}
