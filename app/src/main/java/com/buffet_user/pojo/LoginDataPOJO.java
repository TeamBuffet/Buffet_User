package com.buffet_user.pojo;

/**
 * Created by Ankit on 06/05/18.
 */

public class LoginDataPOJO {

    String userid;
    String timestamp;

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "LoginDataPOJO{" +
                "userid='" + userid + '\'' +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }
}

