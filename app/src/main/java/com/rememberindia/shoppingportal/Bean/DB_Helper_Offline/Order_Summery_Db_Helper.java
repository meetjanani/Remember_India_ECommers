package com.rememberindia.shoppingportal.Bean.DB_Helper_Offline;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class Order_Summery_Db_Helper extends SQLiteOpenHelper {


    private static final int Db_Version = 1;
    private static final String Db_Name = "Order_Summery";
    private static final String Table_Name = "Order_tbl";

    private static final String id = "id";
    private static final String item_id = "item_id"; // Product_ID
    private static final String item_name = "item_name";    // Product_Name
    private static final String qty = "qty";
    private static final String base_price = "base_price";  // Product Price
    private static final String total_price = "total_price";    // T
    private static final String varient_name = "varient_name";


    public Order_Summery_Db_Helper(Context context) {
        super(context, Db_Name, null, Db_Version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String Create_Table = "CREATE TABLE " + Table_Name +
                "(" +
                id + " INTEGER PRIMARY KEY," +
                item_id + " TEXT," +
                item_name + " TEXT," +
                qty + " TEXT," +
                base_price + " TEXT," +
                total_price + " TEXT," +
                varient_name + " TEXT)";
        db.execSQL(Create_Table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Table_Name);
        onCreate(db);
    }

    public void addOrder(Order_Summery_Ofline_Bean order_items, String item__id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        // cv.put(User_id,usr.getId());
        cv.put(item_id, item__id);
        cv.put(item_name, order_items.getItem_name());
        cv.put(qty, order_items.getQty());
        cv.put(base_price, order_items.getBase_price());
        cv.put(total_price, order_items.getTotal_price());
        cv.put(varient_name, order_items.getVarient_name() + "");
        db.insert(Table_Name, null, cv);
        db.close();
    }

    // code to update the single order
    public int updateOrder(Order_Summery_Ofline_Bean order_items, String item__id) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        //cv.put(item_name, order_items.getItem_name());
        //cv.put(item_name, order_items.getItem_name());
        cv.put(qty, order_items.getQty());
        //cv.put(base_price , order_items.getBase_price());
        cv.put(total_price, order_items.getTotal_price());
        //cv.put(varient_name , order_items.getVarient_name());

        // updating row
        return db.update(Table_Name, cv, item_id + "=?",
                new String[]{item__id});
    }

    // Update Order By Varient Name
    public int updateOrder_ByVarientName(Order_Summery_Ofline_Bean order_items, String varient__name) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        //cv.put(item_name, order_items.getItem_name());
        //cv.put(item_name, order_items.getItem_name());
        cv.put(qty, order_items.getQty());
        //cv.put(base_price , order_items.getBase_price());
        cv.put(total_price, order_items.getTotal_price());
        //cv.put(varient_name , order_items.getVarient_name());

        // updating row
        return db.update(Table_Name, cv, varient_name + "=? and " + item_id + "=?",
                new String[]{varient__name, order_items.getItem_id()});
    }

    // Deleting all order
    public void deleteAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Table_Name, id + " >= 0",
                null);
        db.close();
    }

    // Deleting single order by itemid
    public void deleteByItemID(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Table_Name, item_id + " = " + id,
                null);
        db.close();
    }

    public void deleteByID(int id_) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Table_Name, id + " = " + id_,
                null);
        db.close();
    }

//    public Product_Master_Details_Bean get_Order_By_ID(int ID) {
//        Product_Master_Details_Bean order = new Product_Master_Details_Bean();
//
//        String countQuery = "SELECT  * FROM " + Table_Name + " WHERE id = " + ID;
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery(countQuery, null);
//        if (cursor.moveToFirst()) {
//            do {
//
//                // order.setId(Integer.parseInt(cursor.getString(0)));
//                order.setID(Integer.parseInt(cursor.getString(1)));
//                order.setProductName(cursor.getString(2));
//                order.setQty_local(cursor.getInt(3));
//                //order.set(cursor.getString(3));
//                //laundry_list.add(laundry);
//            } while (cursor.moveToNext());
//
//            cursor.close();
//
//            // return count
//
//        }
//        return order;
//    }

    public List<Order_Summery_Ofline_Bean> getAllOrder() {
        List<Order_Summery_Ofline_Bean> order_list = new ArrayList<Order_Summery_Ofline_Bean>();
        // Select All Query
        String selectQuery = "SELECT  *   FROM " + Table_Name; //, Price ,Qty ,Item

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Order_Summery_Ofline_Bean order = new Order_Summery_Ofline_Bean();
                order.setId(cursor.getString(0));
                order.setItem_id(cursor.getString(1));
                order.setItem_name(cursor.getString(2));
                order.setQty(cursor.getString(3));
                order.setBase_price(cursor.getString(4));
                order.setTotal_price(cursor.getString(5));
                order.setVarient_name(cursor.getString(6));

                // Adding contact to list
                order_list.add(order);
            } while (cursor.moveToNext());
        }

        // return contact list
        return order_list;
    }


    public void get_Qty(Order_Summery_Ofline_Bean order_items, String item__id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        // cv.put(User_id,usr.getId());
        cv.put(item_id, item__id);
        cv.put(item_name, order_items.getItem_name());
        cv.put(qty, order_items.getQty());
        cv.put(base_price, order_items.getBase_price());
        cv.put(total_price, order_items.getTotal_price());
        cv.put(varient_name, order_items.getVarient_name() + "");
        db.insert(Table_Name, null, cv);
        db.close();
    }


    public String get_Qty_By_varientName(String varient__name, String item__id) {
        //Item_Details_Bean order = new Item_Details_Bean();
        String Total_Qty = "0";

        String countQuery = "SELECT " + qty + " FROM " + Table_Name +
                " WHERE " + varient_name + " = '" + varient__name +
                "' and " +
                item_id + " = " + item__id + " ;";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        if (cursor.moveToFirst()) {
            do {

                // order.setId(Integer.parseInt(cursor.getString(0)));
                Total_Qty = (cursor.getString(0));
                //order.setItemname(cursor.getString(2));
                //order.setItemname(cursor.getString(3));
                //order.set(cursor.getString(3));
                //laundry_list.add(laundry);
            } while (cursor.moveToNext());

            cursor.close();

            // return count

        }
        return Total_Qty;
    }


    public int get_Qty_By_Item_ID(int item__id) {
        //Item_Details_Bean order = new Item_Details_Bean();
        int Total_Qty = 0;

        String countQuery = "SELECT SUM(" + qty + ") as qty FROM " + Table_Name +
                " WHERE " + item_id + " = " + item__id + " ;";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        if (cursor.moveToFirst()) {
            do {

                // order.setId(Integer.parseInt(cursor.getString(0)));
                Total_Qty = (cursor.getInt(0));
                //order.setItemname(cursor.getString(2));
                //order.setItemname(cursor.getString(3));
                //order.set(cursor.getString(3));
                //laundry_list.add(laundry);
            } while (cursor.moveToNext());

            cursor.close();

            // return count

        }
        return Total_Qty;
    }

    public int get_Qty_By_Item_ID_Varient(String varient__name, int item__id) {
        //Item_Details_Bean order = new Item_Details_Bean();
        int Total_Qty = 0;

        String countQuery = "SELECT SUM(" + qty + ") as qty FROM " + Table_Name +
                " WHERE " + varient_name + " = '" + varient__name +
                "' and " +
                item_id + " = " + item__id + " ;";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        if (cursor.moveToFirst()) {
            do {

                // order.setId(Integer.parseInt(cursor.getString(0)));
                Total_Qty = (cursor.getInt(0));
                //order.setItemname(cursor.getString(2));
                //order.setItemname(cursor.getString(3));
                //order.set(cursor.getString(3));
                //laundry_list.add(laundry);
            } while (cursor.moveToNext());

            cursor.close();

            // return count

        }
        return Total_Qty;
    }


    public int get_Varient_Price_By_Item_ID(int item__id) {
        //Item_Details_Bean order = new Item_Details_Bean();
        int ID = 0;

        String countQuery = "SELECT " + id + " FROM " + Table_Name +
                " WHERE " + item_id + " = " + item__id + " order By id desc limit 1;";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        if (cursor.moveToFirst()) {
            do {

                // order.setId(Integer.parseInt(cursor.getString(0)));
                ID = (cursor.getInt(0));
                //order.setItemname(cursor.getString(2));
                //order.setItemname(cursor.getString(3));
                //order.set(cursor.getString(3));
                //laundry_list.add(laundry);
            } while (cursor.moveToNext());

            cursor.close();

            // return count


        }


        int Base_Price_Rate = 0;
        String Varient_Name = "";


        String countQuery_1 = "SELECT " + base_price + " , " + varient_name + " FROM " + Table_Name +
                " WHERE " + id + " = " + ID + " ;";
        SQLiteDatabase db_ = this.getReadableDatabase();
        Cursor cursor_ = db_.rawQuery(countQuery_1, null);
        if (cursor_.moveToFirst()) {
            do {
                Base_Price_Rate = (cursor_.getInt(0));
                Varient_Name = (cursor_.getString(1));
            } while (cursor.moveToNext());

            cursor.close();

            // return count

        }


        int Qty = get_Qty_By_Item_ID_Varient(Varient_Name, item__id);

        if (Qty == 1) {
            deleteByID(ID);
        } else {

            Qty = Qty - 1 ;
            int totalprice = (Qty) * Base_Price_Rate;
            SQLiteDatabase db_2 = this.getWritableDatabase();

            ContentValues cv_2 = new ContentValues();
            cv_2.put(qty, Qty);
            cv_2.put(total_price, totalprice);

            // updating row
            int tt = db_2.update(Table_Name, cv_2, id + "=? and " + item_id + "=?",
                    new String[]{ID + "", item__id + ""});

        }

        return Base_Price_Rate;
    }



}
