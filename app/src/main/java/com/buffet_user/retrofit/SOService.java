package com.buffet_user.retrofit;

/**
 * Created by Ankit on 02/11/17.
 */

import com.buffet_user.pojo.BlogFeedPOJO;
import com.buffet_user.pojo.LoginDataPOJO;
import com.buffet_user.pojo.LoginPojo;
import com.buffet_user.pojo.LoginSuperPojo;
import com.buffet_user.pojo.MenuPojo;
import com.buffet_user.pojo.PostFeedDataPOJO;
import com.buffet_user.pojo.PostFeedPOJO;

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

    @GET("/filters")
    Call<MenuPojo> getredmenu();

    @POST("/auth")
    Call<LoginSuperPojo> getLogin(@Body LoginDataPOJO data);

}