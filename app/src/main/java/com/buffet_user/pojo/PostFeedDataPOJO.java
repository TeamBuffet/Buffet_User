package com.buffet_user.pojo;

/**
 * Created by Ankit on 04/11/17.
 */

public class PostFeedDataPOJO {

    String blogImageUrl;
    String blogCaption;
    String userId;
    String timeStamp;

    public String getBlogImageUrl() {
        return blogImageUrl;
    }

    public void setBlogImageUrl(String blogImageUrl) {
        this.blogImageUrl = blogImageUrl;
    }

    public String getBlogCaption() {
        return blogCaption;
    }

    public void setBlogCaption(String blogCaption) {
        this.blogCaption = blogCaption;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }
}
