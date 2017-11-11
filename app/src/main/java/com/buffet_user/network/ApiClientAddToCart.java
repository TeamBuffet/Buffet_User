package com.buffet_user.network;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.PUT;

/**
 * Created by akshaybmsa96 on 09/11/17.
 */

public interface ApiClientAddToCart {

    @POST("addtocart.php")
    @FormUrlEncoded
    Call<Integer> getInfo(@Field("cartitem") String data);
}
