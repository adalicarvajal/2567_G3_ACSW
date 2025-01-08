package com.alex.ultim2.models;

import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class GoogleSheetsResponse {
    private static Retrofit retrofit = null;
    private static Retrofit retrofit1 = null;
    private static Retrofit retrofit2 = null;
    public static Retrofit getClientGetMethod(String baseUrl) {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
    public static Retrofit getClientGetMethod1(String baseUrl) {
        if (retrofit1 == null) {
            retrofit1 = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .build();
        }
        return retrofit1;
    }

    public static Retrofit getClientGetMethod2(String baseUrl) {
        if (retrofit2 == null) {
            retrofit2 = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .build();
        }
        return retrofit2;
    }
}
