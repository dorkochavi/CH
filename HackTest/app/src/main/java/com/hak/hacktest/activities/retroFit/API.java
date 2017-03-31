package com.hak.hacktest.activities.retroFit;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;



public class API {

    private static AppInterface service;
    public static final String BASE_URL = "https://6ed3ec21.ngrok.io/"; // for development


    public static void init(){
        //move this code to API.class
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(AppInterface.class);
    }



    public static AppInterface getAPI(){
        return service;
    }
}
