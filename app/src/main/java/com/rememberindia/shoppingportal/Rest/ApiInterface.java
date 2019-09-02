package com.rememberindia.shoppingportal.Rest;

import com.rememberindia.shoppingportal.Bean.Common_Insert_Response_Bean;
import com.rememberindia.shoppingportal.Bean.Login_User_Bean.Login_User_List_Bean;
import com.rememberindia.shoppingportal.Bean.Past_Order_Header_Data.Past_Order_Header_List_Bean;
import com.rememberindia.shoppingportal.Bean.Past_Order_Product_Details.Past_Order_Product_By_Order_ID_List_Bean;
import com.rememberindia.shoppingportal.Bean.Product_List_Bean;
import com.rememberindia.shoppingportal.Bean.R_Pay_Bean;
import com.rememberindia.shoppingportal.Bean.Shared_Post_Bean.Shared_Post_List_Bean;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiInterface {

    // Sign Up
    @FormUrlEncoded
    @POST("Master.php")
    Call<Common_Insert_Response_Bean> SignUp_New_User(@Field("methodname") String methodname,
                                                    @Field("Name") String Name,
                                                    @Field("Password") String Password,
                                                      @Field("Degree") String Degree,
                                                      @Field("Registration_No") String Registration_No,
                                                      @Field("Mobile_NO") String Mobile_NO,
                                                      @Field("Email_ID") String Email_ID,
                                                      @Field("City") String City,
                                                      @Field("State") String State,
                                                      @Field("Address_1") String Address_1);

    // Update User Profile
    @FormUrlEncoded
    @POST("Master.php")
    Call<Common_Insert_Response_Bean> SignUp_Update_User(@Field("methodname") String methodname,
                                                         @Field("ID") String ID,
                                                         @Field("Name") String Name,
                                                         @Field("Degree") String Degree,
                                                         @Field("Registration_No") String Registration_No,
                                                         @Field("Mobile_NO") String Mobile_NO,
                                                         @Field("Email_ID") String Email_ID,
                                                         @Field("City") String City,
                                                         @Field("State") String State,
                                                         @Field("Address_1") String Address_1);



    @FormUrlEncoded
    @POST("Master.php")
    Call<Login_User_List_Bean> SignIn_User(@Field("methodname") String methodname,
                                           @Field("Mobile_NO") String Mobile_NO,
                                           @Field("Password") String Password);


    // Display Patch
    @FormUrlEncoded
    @POST("Master.php")
    Call<Product_List_Bean> Display_ProductList(@Field("methodname") String methodname);

    @FormUrlEncoded
    @POST("Master.php")
    Call<Product_List_Bean> Display_Product_By_ID(@Field("methodname") String methodname,
                                                  @Field("ID") String ID);

    // Insert New Order
    @FormUrlEncoded
    @POST("Shooping_Order.php")
    Call<Common_Insert_Response_Bean> Add_New_Order(@Field("methodname") String methodname,
                                                    @Field("Order_ID") String Order_ID,
                                                    @Field("Payment_ID") String Payment_ID,
                                                    @Field("Order_Status") String Order_Status,
                                                    @Field("Order_Type") String Order_Type,
                                                    @Field("Product_ID") String Product_ID,
                                                    @Field("Product_Name") String Product_Name,
                                                    @Field("Product_Category") String Product_Category,
                                                    @Field("Qty") String Qty,
                                                    @Field("Product_Price") String Product_Price,
                                                    @Field("Product_Total") String Product_Total,
                                                    @Field("Order_Total") String Order_Total,
                                                    @Field("User_Mobile_No") String User_Mobile_No,
                                                    @Field("User_Name") String User_Name,
                                                    @Field("User_ID") String User_ID,
                                                    @Field("URL_1") String URL_1);

    @FormUrlEncoded
    @POST("Shooping_Order.php")
    Call<Common_Insert_Response_Bean> Fetch_New_Order_ID(@Field("methodname") String methodname,
                                                       @Field("ID") String ID);


    @FormUrlEncoded
    @POST("Shooping_Order.php")
    Call<Past_Order_Header_List_Bean> Past_Order_Header_Data_By_User_ID(@Field("methodname") String methodname,
                                                                        @Field("User_ID") String User_ID);

    @FormUrlEncoded
    @POST("Shooping_Order.php")
    Call<Past_Order_Product_By_Order_ID_List_Bean> Past_Order_Product_Details_By_Order_ID(@Field("methodname") String methodname,
                                                                                          @Field("Order_ID") String Order_ID);


    @FormUrlEncoded
    @POST("Master.php")
    Call<Shared_Post_List_Bean> Display_Shared_Post(@Field("methodname") String methodname);


    // Upload Image
    @Multipart
    @POST("Master.php")
    Call<Common_Insert_Response_Bean> Photo_Upload(@Part MultipartBody.Part image ,
                                                   @Part("methodname") RequestBody ss ,
                                                   @Part("Message_1") RequestBody Message_1,
                                                   @Part("User_ID") RequestBody User_ID,
                                                   @Part("User_Name") RequestBody User_Name);


    @Multipart
    @POST("Master.php")
    Call<Common_Insert_Response_Bean> Video_Upload(@Part MultipartBody.Part image ,
                                                   @Part("methodname") RequestBody ss ,
                                                   @Part("Message_1") RequestBody Message_1,
                                                   @Part("User_ID") RequestBody User_ID,
                                                   @Part("User_Name") RequestBody User_Name);

    @FormUrlEncoded
    @POST("razorpay.php")
    Call<R_Pay_Bean> Create_Order_OF_RazorPay(@Field("methodname") String methodname,
                                              @Field("amount") String amount,
                                              @Field("receipt") String receipt);


}
