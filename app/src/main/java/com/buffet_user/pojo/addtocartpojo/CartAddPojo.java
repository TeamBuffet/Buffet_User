package com.buffet_user.pojo.addtocartpojo;

/**
 * Created by akshaybmsa96 on 09/11/17.
 */

public class CartAddPojo {

    private String user_phn_number;
    private String item_name;
    private String item_size;
    private String item_base_price;
    private String item_toppings;
    private String extra_add_on;
    private String extra_price;
    private String total_price;

    public String getUser_phn_number() {
        return user_phn_number;
    }

    public void setUser_phn_number(String user_phn_number) {
        this.user_phn_number = user_phn_number;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getItem_size() {
        return item_size;
    }

    public void setItem_size(String item_size) {
        this.item_size = item_size;
    }

    public String getItem_base_price() {
        return item_base_price;
    }

    public void setItem_base_price(String item_base_price) {
        this.item_base_price = item_base_price;
    }

    public String getItem_toppings() {
        return item_toppings;
    }

    public void setItem_toppings(String item_toppings) {
        this.item_toppings = item_toppings;
    }

    public String getExtra_add_on() {
        return extra_add_on;
    }

    public void setExtra_add_on(String extra_add_on) {
        this.extra_add_on = extra_add_on;
    }

    public String getExtra_price() {
        return extra_price;
    }

    public void setExtra_price(String extra_price) {
        this.extra_price = extra_price;
    }

    public String getTotal_price() {
        return total_price;
    }

    public void setTotal_price(String total_price) {
        this.total_price = total_price;
    }
}
