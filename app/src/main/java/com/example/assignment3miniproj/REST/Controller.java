package com.example.assignment3miniproj.REST;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Controller {

    private static String BASE_URL = "https://opentdb.com/";
    public static Retrofit retrofit = null;

    public static Methods getController(){

        if (retrofit ==null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(Methods.class);
    }

    public static Retrofit getRetrof() {
        return retrofit;
    }

}
