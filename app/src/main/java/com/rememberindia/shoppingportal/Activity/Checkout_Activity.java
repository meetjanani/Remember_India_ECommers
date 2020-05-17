package com.rememberindia.shoppingportal.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;
import com.rememberindia.shoppingportal.Bean.Common_Insert_Response_Bean;
import com.rememberindia.shoppingportal.Bean.DB_Helper_Offline.Order_Summery_Db_Helper;
import com.rememberindia.shoppingportal.Bean.DB_Helper_Offline.Order_Summery_Ofline_Bean;
import com.rememberindia.shoppingportal.Bean.R_Pay_Bean;
import com.rememberindia.shoppingportal.R;
import com.rememberindia.shoppingportal.Rest.ApiClient;
import com.rememberindia.shoppingportal.Rest.ApiInterface;
import com.rememberindia.shoppingportal.Utility.Common_Class;
import com.rememberindia.shoppingportal.Utility.Session_Manager;
import com.rememberindia.shoppingportal.Utility.SharedPref;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Checkout_Activity extends AppCompatActivity implements PaymentResultListener {

   // RequestQueue requestQueue;
    Button btn_submit_order;
    EditText edt_name, edt_email, edt_phone, edt_address,  edt_order_list, edt_order_total, edt_comment;
    String str_name, str_email, str_phone, str_address, str_shipping, str_order_list, str_order_total, str_comment;
    String data_order_list = "";
    double str_tax;
    String str_currency_code;
    ProgressDialog progressDialog;
    Order_Summery_Db_Helper dbhelper;
    List<Order_Summery_Ofline_Bean> data;
    private static final String ALLOWED_CHARACTERS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    View view;
    private String rand = getRandomString(9);
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String date = dateFormat.format(Calendar.getInstance().getTime());
    SharedPref sharedPref;
    private Spinner spinner;
    private ArrayList<String> arrayList;
    private JSONArray result;
    String Result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout_);

        view = findViewById(android.R.id.content);
        sharedPref = new SharedPref(this);

        setupToolbar();
      //  getSpinnerData();
        Checkout.preload(getApplicationContext());
        getTaxCurrency();

        dbhelper = new Order_Summery_Db_Helper(this);
//        try {
//            dbhelper.openDataBase();
//        } catch (SQLException sqle) {
//            throw sqle;
//        }

        // Creating Volley newRequestQueue
//        requestQueue = Volley.newRequestQueue(Checkout_Activity.this);
//        progressDialog = new ProgressDialog(Checkout_Activity.this);

        btn_submit_order = findViewById(R.id.btn_submit_order);

        edt_name = findViewById(R.id.edt_name);
        edt_email = findViewById(R.id.edt_email);
        edt_phone = findViewById(R.id.edt_phone);
        edt_address = findViewById(R.id.edt_address);
        edt_order_list = findViewById(R.id.edt_order_list);
        edt_order_total = findViewById(R.id.edt_order_total);
        edt_comment = findViewById(R.id.edt_comment);

        edt_order_list.setEnabled(false);

        getDataFromDatabase();
        submitOrder();

    }

    public void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(R.string.title_checkout);
        }
    }

//    private void getSpinnerData() {
//
//        arrayList = new ArrayList<String>();
//        spinner = findViewById(R.id.spinner);
//
//        StringRequest stringRequest = new StringRequest(GET_SHIPPING, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                JSONObject jsonObject = null;
//                try {
//                    jsonObject = new JSONObject(response);
//                    result = jsonObject.getJSONArray("result");
//                    getShipping(result);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                    @Override
//                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                        edt_shipping.setText(setShipping(i));
//                    }
//
//                    @Override
//                    public void onNothingSelected(AdapterView<?> adapterView) {
//
//                    }
//                });
//
//            }
//        },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//
//                    }
//                });
//
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        requestQueue.add(stringRequest);
//
//    }

    private void getShipping(JSONArray jsonArray) {
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject json = jsonArray.getJSONObject(i);
                arrayList.add(json.getString("shipping_name"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        Toast.makeText(this, "Get_SHipping", Toast.LENGTH_SHORT).show();
//        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(Checkout_Activity.this, R.layout.spinner_item, arrayList);
//        myAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
//        spinner.setAdapter(myAdapter);
    }

    private String setShipping(int position) {
        String name = "";
        try {
            JSONObject json = result.getJSONObject(position);
            name = json.getString("shipping_name");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return name;
    }

    public void submitOrder() {
        btn_submit_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getValueFromEditText();
            }
        });
    }

    public void getValueFromEditText() {

        str_name = edt_name.getText().toString();
        str_email = edt_email.getText().toString();
        str_phone = edt_phone.getText().toString();
        str_address = edt_address.getText().toString();
        str_order_list = edt_order_list.getText().toString();
        str_order_total = edt_order_total.getText().toString();
        str_comment = edt_comment.getText().toString();

//        if (str_name.equalsIgnoreCase("") ||
//                str_email.equalsIgnoreCase("") ||
//                str_phone.equalsIgnoreCase("") ||
//                str_address.equalsIgnoreCase("") ||
//                str_shipping.equalsIgnoreCase("") ||
//                str_order_list.equalsIgnoreCase("")) {
//            Snackbar.make(view, R.string.checkout_fill_form, Snackbar.LENGTH_SHORT).show();
//        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.checkout_dialog_title);
            builder.setMessage(R.string.checkout_dialog_msg);
            builder.setCancelable(false);
            builder.setPositiveButton(getResources().getString(R.string.dialog_option_yes), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                  //  requestAction();
                    Session_Manager.Update_Checkout_Details(Checkout_Activity.this ,
                            edt_name.getText().toString() ,
                            edt_phone.getText().toString() ,
                            edt_email.getText().toString() ,
                            edt_address.getText().toString());
                    Get_New_Order_ID_Api_Call();
                    //new sendData().execute();
                }
            });
            builder.setNegativeButton(getResources().getString(R.string.dialog_option_no), null);
            builder.setCancelable(false);
            builder.show();
        //}
    }

//    public void requestAction() {
//
//        progressDialog.setTitle(getString(R.string.checkout_submit_title));
//        progressDialog.setMessage(getString(R.string.checkout_submit_msg));
//        progressDialog.show();
//
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, POST_ORDER, new Response.Listener<String>() {
//            @Override
//            public void onResponse(final String ServerResponse) {
//
//                final Handler handler = new Handler();
//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        progressDialog.dismiss();
//                        dialogSuccessOrder();
//                    }
//                }, 2000);
//
//            }
//        },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError volleyError) {
//                        progressDialog.dismiss();
//                        Toast.makeText(getApplicationContext(), volleyError.toString(), Toast.LENGTH_LONG).show();
//                    }
//                }) {
//            @Override
//            protected Map<String, String> getParams() {
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("code", rand);
//                params.put("name", str_name);
//                params.put("email", str_email);
//                params.put("phone", str_phone);
//                params.put("address", str_address);
//                params.put("shipping", str_shipping);
//                params.put("order_list", str_order_list);
//                params.put("order_total", str_order_total);
//                params.put("comment", str_comment);
//                params.put("player_id", OneSignal.getPermissionSubscriptionState().getSubscriptionStatus().getUserId());
//
//                params.put("date", date);
//                params.put("server_url", Config.ADMIN_PANEL_URL);
//
//                return params;
//            }
//
//        };
//
//        RequestQueue requestQueue = Volley.newRequestQueue(ActivityCheckout.this);
//        requestQueue.add(stringRequest);
//    }

    public void getTaxCurrency() {
        Intent intent = getIntent();
        str_tax = intent.getDoubleExtra("tax", 0);
        str_currency_code = intent.getStringExtra("currency_code");
    }

    public void getDataFromDatabase() {

        data = dbhelper.getAllOrder();

        double Order_price = 0;
        double Total_price = 0;
        double tax = 0;

        for (int i = 0; i < data.size(); i++) {
            Order_Summery_Ofline_Bean row = data.get(i);

            String Menu_name = row.getItem_name();
            String Quantity = row.getQty();

            double Sub_total_price = Double.parseDouble(row.getTotal_price());

            String _Sub_total_price = String.format(Locale.GERMAN, "%1$,.0f", Sub_total_price);

            Order_price += Sub_total_price;

//            if (Config.ENABLE_DECIMAL_ROUNDING) {
//                data_order_list += (Quantity + " " + Menu_name + " " + _Sub_total_price + " " + str_currency_code + ",\n");
//            } else {
//                data_order_list += (Quantity + " " + Menu_name + " " + Sub_total_price + " " + str_currency_code + ",\n");
//            }
            data_order_list += (Quantity + " " + Menu_name + " " + Sub_total_price + " INR"  + ",\n");
        }

        if (data_order_list.equalsIgnoreCase("")) {
            data_order_list += getString(R.string.no_order_menu);
        }

        tax = Order_price * (str_tax / 100);
        Total_price = Order_price + tax;

        String price_tax = String.format(Locale.GERMAN, "%1$,.0f", str_tax);
        String _Order_price = String.format(Locale.GERMAN, "%1$,.0f", Order_price);
        String _tax = String.format(Locale.GERMAN, "%1$,.0f", tax);
        String _Total_price = String.format(Locale.GERMAN, "%1$,.0f", Total_price);

//        if (Config.ENABLE_DECIMAL_ROUNDING) {
//            data_order_list += "\n" + getResources().getString(R.string.txt_order) + " " + _Order_price + " " + str_currency_code +
//                    "\n" + getResources().getString(R.string.txt_tax) + " " + price_tax + " % : " + _tax + " " + str_currency_code +
//                    "\n" + getResources().getString(R.string.txt_total) + " " + _Total_price + " " + str_currency_code;
//
//
//            edt_order_total.setText(_Total_price + " " + str_currency_code);
//
//        } else {
        Common_Class.Order_Amount = (int)(Order_price * 100 );
            data_order_list += "\n" + getResources().getString(R.string.txt_order) + " " + Order_price + " INR"  +
                    "\n" + getResources().getString(R.string.txt_tax) + " " + str_tax + " % : " + tax + " INR"  +
                    "\n" + getResources().getString(R.string.txt_total) + " " + Total_price + " INR" ;

            edt_order_total.setText(Total_price + " " + str_currency_code);
        //}

        edt_order_list.setText(data_order_list);

    }

    public void dialogSuccessOrder() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.checkout_success_title);
        builder.setMessage(R.string.checkout_success_msg);
        builder.setCancelable(false);
        builder.setPositiveButton(R.string.checkout_option_ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
//                dbhelper.addDataHistory(rand, str_order_list, str_order_total, date);
//                dbhelper.deleteAllData();

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private static String getRandomString(final int sizeOfRandomString) {
        final Random random = new Random();
        final StringBuilder stringBuilder = new StringBuilder(sizeOfRandomString);
        for (int i = 0; i < sizeOfRandomString; ++i)
            stringBuilder.append(ALLOWED_CHARACTERS.charAt(random.nextInt(ALLOWED_CHARACTERS.length())));
        return stringBuilder.toString();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onResume() {
        edt_name.setText(Session_Manager.getName(Checkout_Activity.this));
        edt_email.setText(Session_Manager.getEmail_ID(Checkout_Activity.this));
        edt_phone.setText(Session_Manager.getMobile_No(Checkout_Activity.this));
        edt_address.setText(Session_Manager.getAddres_1(Checkout_Activity.this));
        super.onResume();
    }


    public void  New_Order_Submit_Api_Call(final String OrderID)
    {

        final ProgressDialog progress = new ProgressDialog(Checkout_Activity.this);
        progress.setMessage("Inserting Data) ");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.show();
        List<Order_Summery_Ofline_Bean> Records = dbhelper.getAllOrder();

        for (int i = 0; i < Records.size(); i++) {
            ApiInterface apiService =
                    ApiClient.getClient().create(ApiInterface.class);

            //  Toast.makeText(Display_Cart_Details_Activity.this, Records.size() + "", Toast.LENGTH_SHORT).show();
            Call<Common_Insert_Response_Bean> call = apiService.Add_New_Order(
                    "Insert_Order",
                    OrderID,
                    "0",
                    "Verifying",
                    "1",
                    Records.get(i).getItem_id(),
                    Records.get(i).getItem_name(),
                    "Cat 2",
                    Records.get(i).getQty(),
                    Records.get(i).getBase_price(),
                    (Double.parseDouble(Records.get(i).getBase_price()) *Double.parseDouble(Records.get(i).getQty()))+ "",
                    edt_phone.getText().toString() + "",
                    edt_address.getText().toString() + "",
                    edt_name.getText().toString() + "",
                    Session_Manager.getUser_ID(Checkout_Activity.this),
                    Records.get(i).getVarient_name(),
                    edt_comment.getText().toString() + "");
            call.enqueue(new Callback<Common_Insert_Response_Bean>() {
                @Override
                public void onResponse(Call<Common_Insert_Response_Bean> call, Response<Common_Insert_Response_Bean> response) {

                    if (response.body().getStatus() > 0) {
                        //Toast.makeText(Checkout_Activity.this, OrderID + "", Toast.LENGTH_SHORT).show();
                        Common_Class.Order_ID = OrderID;
                        // Common_Class.Order_Amount = ( response.body().getStatus() * 100 ) +"";

                        Toast.makeText(Checkout_Activity.this, response.body().getStatus() + "", Toast.LENGTH_SHORT).show();


                    } else {
                        Toast.makeText(Checkout_Activity.this, response.body().getMessage() + "", Toast.LENGTH_SHORT).show();
                    }



                }

                @Override
                public void onFailure(Call<Common_Insert_Response_Bean> call, Throwable t) {

                    Log.d("hello", "" + t.getMessage());
                    Toast.makeText(Checkout_Activity.this, t.getMessage() + "", Toast.LENGTH_SHORT).show();


                }
            });
        }



        razorPayGenerateOrder();

        progress.dismiss();
        //finish();

        //Fatch_All_Order();
        //Common_Class.total_amount = 0;

    }


    public String  Get_New_Order_ID_Api_Call()
    {

        final ProgressDialog progress = new ProgressDialog(Checkout_Activity.this);
        progress.setMessage("Inserting Data) ");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.show();
        List<Order_Summery_Ofline_Bean> Records = dbhelper.getAllOrder();


        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        //  Toast.makeText(Display_Cart_Details_Activity.this, Records.size() + "", Toast.LENGTH_SHORT).show();
        Call<Common_Insert_Response_Bean> call = apiService.Fetch_New_Order_ID(
                "Get_New_Order_ID" , "1");
        call.enqueue(new Callback<Common_Insert_Response_Bean>() {
            @Override
            public void onResponse(Call<Common_Insert_Response_Bean> call, Response<Common_Insert_Response_Bean> response) {


                Common_Class.Order_ID = response.body().getMessage();
                New_Order_Submit_Api_Call(Common_Class.Order_ID);

                //        Toast.makeText(Display_Cart_Details_Activity.this, "QQ " + Common_Class.Order_ID + "", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<Common_Insert_Response_Bean> call, Throwable t) {

                Log.d("hello", "" + t.getMessage());


            }
        });


        progress.dismiss();

        return  Common_Class.Order_ID;

    }


    public void razorPayGenerateOrder()
    {
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
//
        Call<R_Pay_Bean> call = apiService.Create_Order_OF_RazorPay(
                "Generate_New_OrderID_Test",
                Common_Class.Order_Amount + "",
                Session_Manager.getName(Checkout_Activity.this) + " U-" + Session_Manager.getUser_ID(Checkout_Activity.this)   + " O-" + Common_Class.Order_ID);
        call.enqueue(new Callback<R_Pay_Bean>() {
            @Override
            public void onResponse(Call<R_Pay_Bean> call, Response<R_Pay_Bean> response) {
                Common_Class.Payment_ID = response.body().getId();
                Change_Payment_Id(Common_Class.Order_ID, Common_Class.Payment_ID);
                startPayment(response.body().getId());

            }

            @Override
            public void onFailure(Call<R_Pay_Bean> call, Throwable t) {
                Toast.makeText(Checkout_Activity.this, t.getMessage() + "", Toast.LENGTH_SHORT).show();
            }
        });


//        ApiInterface apiService =
//                ApiClient.getClient().create(ApiInterface.class);
////
//        Call<R_Pay_Bean> call = apiService.Create_Order_OF_RazorPay(
//                "Generate_New_OrderID_Test",
//                Common_Class.Order_Amount+ "",
//                Common_Class.Order_ID + "");
//        call.enqueue(new Callback<R_Pay_Bean>() {
//            @Override
//            public void onResponse(Call<R_Pay_Bean> call, Response<R_Pay_Bean> response) {
//
//
//                // Toast.makeText(R_Pay_Activity.this, response.body().getId() + "", Toast.LENGTH_SHORT).show();
//                Common_Class.Payment_ID = response.body().getId();
//                Change_Payment_Id(Common_Class.Order_ID, Common_Class.Payment_ID);
//                startPayment( response.body().getId());
//
//
//            }
//
//            @Override
//            public void onFailure(Call<R_Pay_Bean> call, Throwable t) {
//                Toast.makeText(Checkout_Activity.this, t.getMessage() + "", Toast.LENGTH_SHORT).show();
//            }
//        });

    }

    public void Change_Payment_Id(String order_id, String PaymentOrderId) {
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Call<Common_Insert_Response_Bean> call = apiService.Change_Order_Status("Change_Payment_Id",
                PaymentOrderId,
                order_id);
        call.enqueue(new Callback<Common_Insert_Response_Bean>() {
            @Override
            public void onResponse(Call<Common_Insert_Response_Bean> call, Response<Common_Insert_Response_Bean> response) {

                if (response.body().getStatus() == 1) {
                    // Toast.makeText(context, response.body().getMessage() + "", Toast.LENGTH_SHORT).show();
                } else {
                }
            }

            @Override
            public void onFailure(Call<Common_Insert_Response_Bean> call, Throwable t) {
            }
        });
    }

    public void Change_Payment_Status(String order_id, String PaymentStatus) {
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Call<Common_Insert_Response_Bean> call = apiService.Change_Order_Status("Change_Payment_Status",
                PaymentStatus,
                order_id);
        call.enqueue(new Callback<Common_Insert_Response_Bean>() {
            @Override
            public void onResponse(Call<Common_Insert_Response_Bean> call, Response<Common_Insert_Response_Bean> response) {

                if (response.body().getStatus() == 1) {
                    // Toast.makeText(context, response.body().getMessage() + "", Toast.LENGTH_SHORT).show();
                } else {
                }
            }

            @Override
            public void onFailure(Call<Common_Insert_Response_Bean> call, Throwable t) {
            }
        });
    }

    public void startPayment(String order_id) {
        /**
         * Instantiate Checkout
         */
        Checkout checkout = new Checkout();


        /**
         * Set your logo here
         */
        checkout.setImage(R.drawable.ic_action_delete);

        /**
         * Reference to current activity
         */
        final Activity activity = this;

        /**
         * Pass your payment options to the Razorpay Checkout as a JSONObject
         */
        try {


            JSONObject options = new JSONObject();

            /**
             * Merchant Name
             * eg: ACME Corp || HasGeek etc.
             */
            options.put("name", "Remember India");

            /**
             * Description can be anything
             * eg: Order #123123 - This order number is passed by you for your internal reference. This is not the `razorpay_order_id`.
             *     Invoice Payment
             *     etc.
             */
            options.put("description", "Order ID : " + Common_Class.Order_ID);
            options.put("order_id", order_id); // order_DCDFACMZNVY4lk
            options.put("currency", "INR");

//            options.put("email", "test@razorpay.com");
//            options.put("contact", "9876543210");

            /**
             * Amount is always passed in currency subunits
             * Eg: "500" = INR 5.00
             */
            options.put("amount", Common_Class.Order_Amount);

            checkout.open(activity, options);
        } catch(Exception e) {
            //Log.e(TAG, "Error in starting Razorpay Checkout", e);
            Toast.makeText(activity, e.getMessage() + " 1", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onPaymentSuccess(String s) {
        dbhelper.deleteAll();
        Change_Payment_Status(Common_Class.Order_ID, "Success");
        Common_Class.Order_ID = null;
        Toast.makeText(this, s+ "", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onPaymentError(int i, String s) {
        Change_Payment_Status(Common_Class.Order_ID, "Failed");
        Common_Class.Order_ID = null;
    }
}
