package com.rememberindia.shoppingportal.Bean.Login_User_Bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Login_User_Details_Bean {

    @SerializedName("ID")
    @Expose
    private String iD;
    @SerializedName("User_ID")
    @Expose
    private String userID;
    @SerializedName("Password")
    @Expose
    private String password;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Degree")
    @Expose
    private String degree;
    @SerializedName("Registration_No")
    @Expose
    private String registrationNo;
    @SerializedName("Mobile_NO")
    @Expose
    private String mobileNO;
    @SerializedName("Email_ID")
    @Expose
    private String emailID;
    @SerializedName("City")
    @Expose
    private String city;
    @SerializedName("State")
    @Expose
    private String state;
    @SerializedName("Address_1")
    @Expose
    private String address1;
    @SerializedName("Created_At")
    @Expose
    private String createdAt;
    @SerializedName("Updated_At")
    @Expose
    private String updatedAt;
    @SerializedName("Is_Active")
    @Expose
    private String isActive;
    @SerializedName("URL_1")
    @Expose
    private String uRL1;
    @SerializedName("URL_2")
    @Expose
    private String uRL2;

    public String getID() {
        return iD;
    }

    public void setID(String iD) {
        this.iD = iD;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getRegistrationNo() {
        return registrationNo;
    }

    public void setRegistrationNo(String registrationNo) {
        this.registrationNo = registrationNo;
    }

    public String getMobileNO() {
        return mobileNO;
    }

    public void setMobileNO(String mobileNO) {
        this.mobileNO = mobileNO;
    }

    public String getEmailID() {
        return emailID;
    }

    public void setEmailID(String emailID) {
        this.emailID = emailID;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public String getURL1() {
        return uRL1;
    }

    public void setURL1(String uRL1) {
        this.uRL1 = uRL1;
    }

    public String getURL2() {
        return uRL2;
    }

    public void setURL2(String uRL2) {
        this.uRL2 = uRL2;
    }



}
