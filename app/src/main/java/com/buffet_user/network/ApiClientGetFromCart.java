package com.buffet_user.network;

import com.buffet_user.pojo.getfromcartpojo.CartinfoPojo;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by akshaybmsa96 on 09/11/17.
 */

public interface ApiClientGetFromCart {

    @POST("getfromcart.php")
    @FormUrlEncoded
    Call<CartinfoPojo> getItems(@Field("userphn") String data);
}
