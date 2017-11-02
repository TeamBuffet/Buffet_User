package com.buffet_user.pojo;

/**
 * Created by Ankit on 02/11/17.
 */


import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BlogFeedPOJO {

    @SerializedName("message")
    @Expose
    private List<MessageFeedPOJO> message;
    @SerializedName("error")
    @Expose
    private String error;

    public List<MessageFeedPOJO> getMessage() {
        return message;
    }

    public void setMessage(List<MessageFeedPOJO> message) {
        this.message = message;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

}
