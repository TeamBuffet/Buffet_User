package com.buffet_user.retrofit;

/**
 * Created by Ankit on 02/11/17.
 */
import com.buffet_user.pojo.BlogFeedPOJO;
import com.buffet_user.pojo.MenuPojo;
import com.buffet_user.pojo.MessageFeedPOJO;
import com.buffet_user.pojo.PostFeedDataPOJO;
import com.buffet_user.pojo.PostFeedPOJO;

import org.json.JSONObject;

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
}