package com.buffet_user.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Ankit on 06/05/18.
 */

public class LoginSuperPojo {

    @SerializedName("message")
    @Expose
    private List<LoginPojo> message;
    @SerializedName("error")
    @Expose
    private String error;

    public List<LoginPojo> getMessage() {
        return message;
    }

    public void setMessage(List<LoginPojo> message) {
        this.message = message;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }


}
