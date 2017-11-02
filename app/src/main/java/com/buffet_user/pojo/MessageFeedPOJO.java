package com.buffet_user.pojo;

/**
 * Created by Ankit on 02/11/17.
 */

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MessageFeedPOJO implements Serializable,Parcelable{


    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public MessageFeedPOJO createFromParcel(Parcel in) {
            return new MessageFeedPOJO(in);
        }

        public MessageFeedPOJO[] newArray(int size) {
            return new MessageFeedPOJO[size];
        }
    };


    @SerializedName("blog_id")
    @Expose
    private Integer blogId;
    @SerializedName("user")
    @Expose
    private Integer user;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("caption")
    @Expose
    private String caption;
    @SerializedName("timestamp")
    @Expose
    private String timestamp;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("dob")
    @Expose
    private String dob;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("likes")
    @Expose
    private Integer likes;

    public Integer getBlogId() {
        return blogId;
    }

    public void setBlogId(Integer blogId) {
        this.blogId = blogId;
    }

    public Integer getUser() {
        return user;
    }

    public void setUser(Integer user) {
        this.user = user;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public MessageFeedPOJO(Parcel in){
        this.blogId = in.readInt();
        this.userId=in.readInt();
        this.user =  in.readInt();
        this.likes=in.readInt();
        this.name = in.readString();
        this.url=in.readString();
        this.caption=in.readString();
        this.timestamp=in.readString();
        this.dob=in.readString();
        this.phone=in.readString();
        this.email=in.readString();
        this.gender=in.readString();
        this.location=in.readString();
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.blogId);
        dest.writeInt(this.user);
        dest.writeInt(this.userId);
        dest.writeInt(this.likes);
        dest.writeString(this.name);
        dest.writeString(this.url);
        dest.writeString(this.caption);
        dest.writeString(this.timestamp);
        dest.writeString(this.dob);
        dest.writeString(this.phone);
        dest.writeString(this.email);
        dest.writeString(this.gender);
        dest.writeString(this.location);
    }
}
