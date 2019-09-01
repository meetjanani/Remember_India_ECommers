package com.rememberindia.shoppingportal.Rest;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    //  Meetjanani.in Server
    //public static final String BASE_URL = "http://meetjanani.in/Android/Remember_Me/API/";
    // RememberMe.in Server
    public static final String BASE_URL = "http://rememberindia.in/Android/Remember_India_Shopping_Portal/API/";
    private static Retrofit retrofit = null;


    public static Retrofit getClient() {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

}
