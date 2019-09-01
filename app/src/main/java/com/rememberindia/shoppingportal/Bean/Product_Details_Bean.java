package com.rememberindia.shoppingportal.Bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Product_Details_Bean {

    @SerializedName("ID")
    @Expose
    private String iD;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Pack_Size")
    @Expose
    private String packSize;
    @SerializedName("MRP_1")
    @Expose
    private String mRP1;
    @SerializedName("MRP_2")
    @Expose
    private String mRP2;
    @SerializedName("Min_Qty")
    @Expose
    private String minQty;
    @SerializedName("Seq")
    @Expose
    private String seq;

    @SerializedName("Description")
    @Expose
    private String Description;

    @SerializedName("Created_At")
    @Expose
    private String createdAt;
    @SerializedName("Is_Active")
    @Expose
    private String isActive;
    @SerializedName("URL_1")
    @Expose
    private String uRL1;

    public String getID() {
        return iD;
    }

    public void setID(String iD) {
        this.iD = iD;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPackSize() {
        return packSize;
    }

    public void setPackSize(String packSize) {
        this.packSize = packSize;
    }

    public String getMRP1() {
        return mRP1;
    }

    public void setMRP1(String mRP1) {
        this.mRP1 = mRP1;
    }

    public String getMRP2() {
        return mRP2;
    }

    public void setMRP2(String mRP2) {
        this.mRP2 = mRP2;
    }

    public String getMinQty() {
        return minQty;
    }

    public void setMinQty(String minQty) {
        this.minQty = minQty;
    }

    public String getSeq() {
        return seq;
    }

    public void setSeq(String seq) {
        this.seq = seq;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
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



}
