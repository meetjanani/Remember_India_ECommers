package com.rememberindia.remember4u.Utility;

import android.content.Context;
import android.content.SharedPreferences;

public class Session_Manager {

    public static final String MyPREFERENCES = "Login" ;
    public static final String Is_Login = "Is_Login";
    public static final String ID = "ID";
    public static final String Name = "Name";
    public static final String Password = "Password";
    public static final String Degree = "Degree";
    public static final String Registration_No = "Registration_No";
    public static final String Mobile_NO = "Mobile_NO";
    public static final String Email_ID = "Email_ID";
    public static final String City = "City";
    public static final String State = "State";
    public static final String Address_1 = "Address_1";
    public static final String Pincode = "Pincode";
    public static final String isPincodeValid = "isPincodeValid";

    public static void Save_Login_Data(Context ctx, String islogin, String id, String UserName ,  String pass , String degree,
                                                    String registration_no, String mobile_no , String email_id , String city ,
                                                    String state ,String address_1, String pincode)
    {
        SharedPreferences pref  = ctx.getSharedPreferences(MyPREFERENCES, 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(Is_Login, islogin); // Storing boolean - true/false
        editor.putString(ID, id);
        editor.putString(Name, UserName);
        editor.putString(Password, pass);
        editor.putString(Degree, degree);
        editor.putString(Registration_No, registration_no);
        editor.putString(Mobile_NO, mobile_no);
        editor.putString(Email_ID, email_id);
        editor.putString(City, city);
        editor.putString(State, state);
        editor.putString(Address_1, address_1);
        editor.putString(Pincode, pincode);

        editor.commit();
    }

    public static void Update_Checkout_Details(Context ctx,  String UserName , String mobile_no , String email_id , String address_1, String pincode)
    {
        SharedPreferences pref  = ctx.getSharedPreferences(MyPREFERENCES, 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(Name, UserName);
        editor.putString(Mobile_NO, mobile_no);
        editor.putString(Email_ID, email_id);
        editor.putString(Address_1, address_1);
        editor.putString(Pincode, pincode);

        editor.commit();
    }

    public static void setIs_Login(Context ctx , String islogin) {
        SharedPreferences pref  = ctx.getSharedPreferences(MyPREFERENCES, 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(Is_Login, islogin); // Storing boolean - true/false
        editor.commit();
    }


    public static String getIs_Login(Context ctx) {
        SharedPreferences pref  = ctx.getSharedPreferences(MyPREFERENCES, 0);
        return pref.getString(Is_Login, null); // getting String
    }

    public static String getUser_ID(Context ctx) {
        SharedPreferences pref  = ctx.getSharedPreferences(MyPREFERENCES, 0);
        return pref.getString(ID, null); // getting String
    }

    public static String getPassword(Context ctx) {
        SharedPreferences pref  = ctx.getSharedPreferences(MyPREFERENCES, 0);
        return pref.getString(Password, null); // getting String
    }

    public static String getName(Context ctx) {
        SharedPreferences pref  = ctx.getSharedPreferences(MyPREFERENCES, 0);
        return pref.getString(Name, null); // getting String
    }

    public static String getEmail_ID(Context ctx) {
        SharedPreferences pref  = ctx.getSharedPreferences(MyPREFERENCES, 0);
        return pref.getString(Email_ID, null); // getting String
    }

    public static String getCity(Context ctx) {
        SharedPreferences pref  = ctx.getSharedPreferences(MyPREFERENCES, 0);
        return pref.getString(City, null); // getting String
    }

    public static String getState(Context ctx) {
        SharedPreferences pref  = ctx.getSharedPreferences(MyPREFERENCES, 0);
        return pref.getString(State, null); // getting String
    }

    public static String getDegree(Context ctx) {
        SharedPreferences pref  = ctx.getSharedPreferences(MyPREFERENCES, 0);
        return pref.getString(Degree, null); // getting String
    }

    public static String getRegistration_No(Context ctx) {
        SharedPreferences pref  = ctx.getSharedPreferences(MyPREFERENCES, 0);
        return pref.getString(Registration_No, null); // getting String
    }

    public static String getAddres_1(Context ctx) {
        SharedPreferences pref  = ctx.getSharedPreferences(MyPREFERENCES, 0);
        return pref.getString(Address_1, null); // getting String
    }

    public static String getMobile_No(Context ctx) {
        SharedPreferences pref  = ctx.getSharedPreferences(MyPREFERENCES, 0);
        return pref.getString(Mobile_NO, null); // getting String
    }

     public static String getPincode(Context ctx) {
        SharedPreferences pref  = ctx.getSharedPreferences(MyPREFERENCES, 0);
        return pref.getString(Pincode, null); // getting String
    }

    public static void setPincode(Context ctx , String pincode) {
        SharedPreferences pref  = ctx.getSharedPreferences(MyPREFERENCES, 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(Pincode, pincode);
        editor.commit();
    }

    public static Boolean getisPincodeValid(Context ctx) {
        SharedPreferences pref  = ctx.getSharedPreferences(MyPREFERENCES, 0);
        return pref.getBoolean(isPincodeValid, false); // getting String
    }

    public static void setisPincodeValid(Context ctx , Boolean isPincodeValidvalue) {
        SharedPreferences pref  = ctx.getSharedPreferences(MyPREFERENCES, 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(isPincodeValid, isPincodeValidvalue);
        editor.commit();
    }
}
