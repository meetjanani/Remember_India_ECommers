package com.rememberindia.remember4u.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rememberindia.remember4u.Activity.Past_Order_Product_Details_Activity;
import com.rememberindia.remember4u.Bean.Past_Order_Header_Data.Past_Order_Header_Details_Bean;
import com.rememberindia.remember4u.R;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.rememberindia.remember4u.R.drawable.green_dot;

public class Past_Order_Header_Adapter extends RecyclerView.Adapter<Past_Order_Header_Adapter.MyViewHolder> {

    private Context context;
    private List<Past_Order_Header_Details_Bean> Records;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout LL_Main;
        public TextView Tv_Order_ID , Tv_Order_Date , Tv_Order_Status , Tv_Order_Amount;
        ImageView product_image , Img_Accept , Img_Dispatched , Img_Deliverer;
        View View_Accept , View_Dispatched;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
           // Tv_Order_ID = (TextView)itemView.findViewById(R.id.Tv_Order_ID);
            Tv_Order_Date = (TextView)itemView.findViewById(R.id.Tv_Order_Date);
            Tv_Order_Status = (TextView)itemView.findViewById(R.id.Tv_Order_Status);
            Tv_Order_Amount = (TextView)itemView.findViewById(R.id.Tv_Order_Amount);
            product_image = itemView.findViewById(R.id.product_image);

            Img_Accept = (ImageView)itemView.findViewById(R.id.Img_Accept);
            Img_Dispatched = (ImageView)itemView.findViewById(R.id.Img_Dispatched);
            Img_Deliverer = (ImageView)itemView.findViewById(R.id.Img_Deliverer);

            View_Accept = (View)itemView.findViewById(R.id.View_Accept);
            View_Dispatched = (View)itemView.findViewById(R.id.View_Dispatched);

            LL_Main = (LinearLayout)itemView.findViewById(R.id.LL_Main);
        }
    }

    public Past_Order_Header_Adapter(Context context, List<Past_Order_Header_Details_Bean> productList ) {
        this.context = context;
        //  this.listener = listener;
        this.Records = productList;
    }


    @NonNull
    @Override
    public Past_Order_Header_Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.pattern_past_order_header, parent, false);

        return new MyViewHolder(itemView);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull Past_Order_Header_Adapter.MyViewHolder holder, final int i) {

        //holder.Tv_Order_ID.setText(Records.get(i).getOrderID());

        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy");
        //String inputDateStr="2013-06-24";
        Date date = null;
        try {
            date = inputFormat.parse(Records.get(i).getCreated_Date().subSequence(0,10).toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String outputDateStr = outputFormat.format(date);


        holder.Tv_Order_Date.setText( outputDateStr); // "Ordered On : " +
        holder.Tv_Order_Status.setText("Status : " +Records.get(i).getOrderStatus());
        holder.Tv_Order_Amount.setText(Records.get(i).getOrderTotal() + " ₹");


        holder.LL_Main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(context , Past_Order_Product_Details_Activity.class);
                in.putExtra("Order_ID" , Records.get(i).getOrderID());
                context.startActivity(in);
            }
        });

        Picasso.with(context)
                .load(Records.get(i).getURL1() + "")
                .placeholder(R.mipmap.ic_launcher_foreground)
                .resize(250, 250)
                .centerCrop()
                //.transform(transformation)
                .into(holder.product_image);


        if (Records.get(i).getOrderStatus().equals("Verifying")) {
            holder.Img_Accept.setImageResource(R.drawable.red_dot);
            holder.Img_Dispatched.setImageResource(R.drawable.red_dot);
            holder.Img_Deliverer.setImageResource(R.drawable.red_dot);
            holder.View_Accept.setBackgroundResource(R.color.red);
            holder.View_Dispatched.setBackgroundResource(R.color.red);
        }
        else if (Records.get(i).getOrderStatus().equals("Accepted"))
        {
            holder.Img_Accept.setImageResource(green_dot);
            holder.Img_Dispatched.setImageResource(R.drawable.red_dot);
            holder.Img_Deliverer.setImageResource(R.drawable.red_dot);
            holder.View_Accept.setBackgroundResource(R.color.red);
            holder.View_Dispatched.setBackgroundResource(R.color.red);
        }
        else if (Records.get(i).getOrderStatus().equals("Dispatched"))
        {
            holder.Img_Accept.setImageResource(R.drawable.green_dot);
            holder.Img_Dispatched.setImageResource(R.drawable.green_dot);
            holder.Img_Deliverer.setImageResource(R.drawable.red_dot);
            holder.View_Accept.setBackgroundResource(R.color.green);
            holder.View_Dispatched.setBackgroundResource(R.color.red);
        }
        else if (Records.get(i).getOrderStatus().equals("Delivered"))
        {
            holder.Img_Accept.setImageResource(R.drawable.green_dot);
            holder.Img_Dispatched.setImageResource(R.drawable.green_dot);
            holder.Img_Deliverer.setImageResource(green_dot);
            holder.View_Accept.setBackgroundResource(R.color.green);
            holder.View_Dispatched.setBackgroundResource(R.color.green);
        }
        else {
            holder.Img_Accept.setImageResource(R.drawable.red_dot);
            holder.Img_Dispatched.setImageResource(R.drawable.red_dot);
            holder.Img_Deliverer.setImageResource(R.drawable.red_dot);
            holder.View_Accept.setBackgroundResource(R.color.red);
            holder.View_Dispatched.setBackgroundResource(R.color.red);
        }


    }

    @Override
    public int getItemCount() {
        return Records.size();
    }


}
