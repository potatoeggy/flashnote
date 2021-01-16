package com.flashnote.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
// manages the two retrofits
public class ApiAdapter {
    private static Retrofit queryRetrofit;
    private static Retrofit pipelineRetrofit;
    private static Retrofit emptyRetrofit;
    private static Gson gson = new GsonBuilder().create();
    
    public static Retrofit getQueryRetrofit() {
        if (queryRetrofit == null) {
            queryRetrofit = new Retrofit
                    .Builder()
                    .baseUrl("https://query.dropbase.io/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return queryRetrofit;
    }
    
    public static Retrofit getPipelineRetrofit() {
        if (pipelineRetrofit == null) {
            pipelineRetrofit = new Retrofit
                    .Builder()
                    .baseUrl("https://api2.dropbase.io/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return pipelineRetrofit;
    }
    
    public static Retrofit getEmptyRetrofit() {
        if (emptyRetrofit == null) {
            emptyRetrofit = new Retrofit
                    .Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return emptyRetrofit;
    }
}
