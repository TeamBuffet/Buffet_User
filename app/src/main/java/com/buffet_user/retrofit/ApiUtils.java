package com.buffet_user.retrofit;

/**
 * Created by Ankit on 02/11/17.
 */
public class ApiUtils {

    public static final String BASE_URL = "http://139.59.5.188:8080/";

    public static SOService getSOService() {
        return RetrofitClient.getClient(BASE_URL).create(SOService.class);
    }
}
