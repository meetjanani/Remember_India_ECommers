package com.rememberindia.remember4u.Bean;

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
    @SerializedName("GST_Percentage")
    @Expose
    private String GST_Percentage;
    @SerializedName("EX_GST_MRP")
    @Expose
    private String EX_GST_MRP;
    @SerializedName("GST_MRP")
    @Expose
    private String GST_MRP;
    @SerializedName("Brand_Name")
    @Expose
    private String brandName;
    @SerializedName("Category_ID")
    @Expose
    private String categoryID;
    @SerializedName("Category_name")
    @Expose
    private String categoryName;
    @SerializedName("Seq")
    @Expose
    private String seq;
    @SerializedName("Description")
    @Expose
    private String description;
    @SerializedName("Created_At")
    @Expose
    private String createdAt;
    @SerializedName("Is_Active")
    @Expose
    private String isActive;
    @SerializedName("URL_1")
    @Expose
    private String uRL1;
    @SerializedName("Amazon_Link")
    @Expose
    private String amazonLink;
    @SerializedName("FlipKart_Link")
    @Expose
    private String flipKartLink;
    @SerializedName("Avaiable_Stock")
    @Expose
    private String avaiableStock;
    @SerializedName("Sold_Stock")
    @Expose
    private String soldStock;
    @SerializedName("Total_Stock")
    @Expose
    private String totalStock;

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

    public String getGST_Percentage() {
        return GST_Percentage;
    }

    public void setGST_Percentage(String GST_Percentage) {
        this.GST_Percentage = GST_Percentage;
    }

    public String getEX_GST_MRP() {
        return EX_GST_MRP;
    }

    public void setEX_GST_MRP(String EX_GST_MRP) {
        this.EX_GST_MRP = EX_GST_MRP;
    }

    public String getGST_MRP() {
        return GST_MRP;
    }

    public void setGST_MRP(String GST_MRP) {
        this.GST_MRP = GST_MRP;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getSeq() {
        return seq;
    }

    public void setSeq(String seq) {
        this.seq = seq;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getAmazonLink() {
        return amazonLink;
    }

    public void setAmazonLink(String amazonLink) {
        this.amazonLink = amazonLink;
    }

    public String getFlipKartLink() {
        return flipKartLink;
    }

    public void setFlipKartLink(String flipKartLink) {
        this.flipKartLink = flipKartLink;
    }

    public String getAvaiableStock() {
        return avaiableStock;
    }

    public void setAvaiableStock(String avaiableStock) {
        this.avaiableStock = avaiableStock;
    }

    public String getSoldStock() {
        return soldStock;
    }

    public void setSoldStock(String soldStock) {
        this.soldStock = soldStock;
    }

    public String getTotalStock() {
        return totalStock;
    }

    public void setTotalStock(String totalStock) {
        this.totalStock = totalStock;
    }

}
