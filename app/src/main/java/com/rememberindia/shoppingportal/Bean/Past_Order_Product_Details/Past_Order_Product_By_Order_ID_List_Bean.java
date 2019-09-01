package com.rememberindia.shoppingportal.Bean.Past_Order_Product_Details;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Past_Order_Product_By_Order_ID_List_Bean {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private List<Past_Order_Product_By_Order_ID_Details_Bean> data = null;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Past_Order_Product_By_Order_ID_Details_Bean> getData() {
        return data;
    }

    public void setData(List<Past_Order_Product_By_Order_ID_Details_Bean> data) {
        this.data = data;
    }


}
