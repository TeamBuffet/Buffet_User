package com.buffet_user.retrofit;

/**
 * Created by Ankit on 02/11/17.
 */

import com.buffet_user.pojo.BlogFeedPOJO;
import com.buffet_user.pojo.CartinfoPojo;
import com.buffet_user.pojo.MenuPojo;
import com.buffet_user.pojo.PostFeedDataPOJO;
import com.buffet_user.pojo.PostFeedPOJO;
import com.buffet_user.pojo.UserPOJO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface SOService {

    @GET("/review/getallfeeds")
    Call<BlogFeedPOJO> getMessage();

    @POST("/review/addfeed")
    Call<PostFeedPOJO> savePost(@Body PostFeedDataPOJO post);

    @GET("/getallmenu")
    Call<MenuPojo> getallmenu();

    @POST("/cart/getcartdata")
    Call<CartinfoPojo> getItems(@Body UserPOJO user);


    @POST("/cart/deletecartdata")
    Call<String> deleteCartItems(@Body String id);

    @POST("/cart/addtocartdata")
    Call<Integer> addCartItems(@Body String id);

}