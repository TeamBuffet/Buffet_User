package com.buffet_user.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by akshaybmsa96 on 29/10/17.
 */

public class ApiClientBase {

    public static String url="http://139.59.5.188:8080/";
    public static Retrofit retrofit = null;

    public static Retrofit getApiClient() {
        if (retrofit == null)
        {
            retrofit = new Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create()).build();
        }

        return retrofit;
    }
}
