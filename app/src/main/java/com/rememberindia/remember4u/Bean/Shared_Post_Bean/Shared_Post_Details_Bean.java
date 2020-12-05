package com.rememberindia.remember4u.Bean.Shared_Post_Bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Shared_Post_Details_Bean {

    @SerializedName("ID")
    @Expose
    private String iD;
    @SerializedName("User_ID")
    @Expose
    private String userID;
    @SerializedName("Flag")
    @Expose
    private String flag;
    @SerializedName("URL_1")
    @Expose
    private String uRL1;
    @SerializedName("URL_2")
    @Expose
    private String uRL2;
    @SerializedName("youTubeAppIntent")
    @Expose
    private String youTubeAppIntent;
    @SerializedName("is_active")
    @Expose
    private String isActive;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("Message_1")
    @Expose
    private String Message_1;

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

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
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

    public String getyouTubeAppIntent() {
        return youTubeAppIntent;
    }

    public void setyouTubeAppIntent(String youTubeAppIntent) {
        this.youTubeAppIntent = youTubeAppIntent;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getMessage_1() {
        return Message_1;
    }

    public void setMessage_1(String Message_1) {
        this.Message_1 = Message_1;
    }



}
