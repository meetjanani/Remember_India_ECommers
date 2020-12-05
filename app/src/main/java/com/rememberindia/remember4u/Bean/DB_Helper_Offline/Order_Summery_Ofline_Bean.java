package com.rememberindia.remember4u.Bean.DB_Helper_Offline;

public class Order_Summery_Ofline_Bean {

    public String id , item_id , item_name , qty , base_price , total_price , GST_Percentage, EX_GST_MRP, GST_MRP,  varient_name;


    public Order_Summery_Ofline_Bean(String id , String item_id, String item_name , String qty,
                                     String base_price, String total_price ,String GST_Percentage, String EX_GST_MRP, String GST_MRP , String varient_name){
        this.id = id;
        this.item_id = item_id;
        this.item_name = item_name;
        this.qty = qty;
        this.base_price = base_price;
        this.total_price = total_price;
        this.GST_Percentage = GST_Percentage;
        this.EX_GST_MRP = EX_GST_MRP;
        this.GST_MRP = GST_MRP;
        this.varient_name = varient_name;
    }

    public Order_Summery_Ofline_Bean()
    {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getBase_price() {
        return base_price;
    }

    public void setBase_price(String base_price) {
        this.base_price = base_price;
    }

    public String getTotal_price() {
        return total_price;
    }

    public void setTotal_price(String total_price) {
        this.total_price = total_price;
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

    public String getVarient_name() {
        return varient_name;
    }

    public void setVarient_name(String varient_name) {
        this.varient_name = varient_name;
    }

}
