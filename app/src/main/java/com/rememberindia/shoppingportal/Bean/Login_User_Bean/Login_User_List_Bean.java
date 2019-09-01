package com.rememberindia.shoppingportal.Bean.Login_User_Bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Login_User_List_Bean {

    @SerializedName("status")
    @Expose
    private int status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private List<Login_User_Details_Bean> data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Login_User_Details_Bean> getData() {
        return data;
    }

    public void setData(List<Login_User_Details_Bean> data) {
        this.data = data;
    }


}
