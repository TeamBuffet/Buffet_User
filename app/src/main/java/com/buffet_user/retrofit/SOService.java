package com.buffet_user.retrofit;

/**
 * Created by Ankit on 02/11/17.
 */
import com.buffet_user.pojo.BlogFeedPOJO;
import com.buffet_user.pojo.MessageFeedPOJO;

import retrofit2.Call;
import retrofit2.http.GET;

public interface SOService {

    @GET("/review/getallfeeds")
    Call<BlogFeedPOJO> getMessage();

}