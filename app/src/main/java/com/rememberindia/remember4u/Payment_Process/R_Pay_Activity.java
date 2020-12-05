package com.rememberindia.remember4u.Payment_Process;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;
import com.rememberindia.remember4u.Bean.R_Pay_Bean;
import com.rememberindia.remember4u.R;
import com.rememberindia.remember4u.Rest.ApiClient;
import com.rememberindia.remember4u.Rest.ApiInterface;
import com.rememberindia.remember4u.Utility.Common_Class;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class R_Pay_Activity extends AppCompatActivity implements PaymentResultListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_r__pay_);

        Checkout.preload(getApplicationContext());

        SignUp_Update_API_Call();

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
        }
    }
    @Override
    public void onPaymentSuccess(String s) {
        finish();
    }

    @Override
    public void onPaymentError(int i, String s) {
    }



    public void SignUp_Update_API_Call()
    {

        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
//
        Call<R_Pay_Bean> call = apiService.Create_Order_OF_RazorPay(
                "Generate_New_OrderID_Live", // Generate_New_OrderID
                Common_Class.Order_Amount+ "",
                Common_Class.Order_ID + "");
        call.enqueue(new Callback<R_Pay_Bean>() {
            @Override
            public void onResponse(Call<R_Pay_Bean> call, Response<R_Pay_Bean> response) {


                startPayment( response.body().getId());
                Common_Class.Payment_ID = response.body().getId();

            }

            @Override
            public void onFailure(Call<R_Pay_Bean> call, Throwable t) {
                Toast.makeText(R_Pay_Activity.this, t.getMessage() + "", Toast.LENGTH_SHORT).show();
            }
        });

    }

}
