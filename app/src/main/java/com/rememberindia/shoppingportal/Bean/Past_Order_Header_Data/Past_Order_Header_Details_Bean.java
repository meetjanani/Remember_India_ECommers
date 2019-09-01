package com.rememberindia.shoppingportal.Bean.Past_Order_Header_Data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Past_Order_Header_Details_Bean {

    @SerializedName("Order_ID")
    @Expose
    private String orderID;
    @SerializedName("Payment_ID")
    @Expose
    private String paymentID;
    @SerializedName("Order_Status")
    @Expose
    private String orderStatus;
    @SerializedName("Order_Total")
    @Expose
    private String orderTotal;
    @SerializedName("Product_Count")
    @Expose
    private String productCount;
    @SerializedName("Product_Qty")
    @Expose
    private String productQty;

    @SerializedName("URL_1")
    @Expose
    private String uRL1;


    @SerializedName("Created_Date")
    @Expose
    private String Created_Date;

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getPaymentID() {
        return paymentID;
    }

    public void setPaymentID(String paymentID) {
        this.paymentID = paymentID;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(String orderTotal) {
        this.orderTotal = orderTotal;
    }

    public String getProductCount() {
        return productCount;
    }

    public void setProductCount(String productCount) {
        this.productCount = productCount;
    }

    public String getProductQty() {
        return productQty;
    }

    public void setProductQty(String productQty) {
        this.productQty = productQty;
    }


    public String getURL1() {
        return uRL1;
    }

    public void setURL1(String uRL1) {
        this.uRL1 = uRL1;
    }


    public String getCreated_Date() {
        return Created_Date;
    }

    public void setCreated_Date(String created_Date) {
        Created_Date = created_Date;
    }
}
