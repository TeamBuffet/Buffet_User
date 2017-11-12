package com.buffet_user.pojo;

/**
 * Created by Ankit on 08/11/17.
 */

public class SingleMenuPojo {

    private String id;
    private String category_id;
    private String menu_name;
    private String price;
    private String size;
    private String offer_id;
    private String topping_count;
    private String image;
    private String cat_id;
    private String category_name;
    private String sub_category_name;
    private String isCustomisation;
    private String extra_cheese_price;
    private String extra_topping_price;
    private String extra_cheese_burst_price;
    private String extra_wheat_crust_price;
    private String extra_italian_crust_price;
    private String extra_pan_crush_price;


    public String isCustomisation() {
        return isCustomisation;
    }

    public void setCustomisation(String customisation) {
        isCustomisation = customisation;
    }

    public String getExtra_cheese_price() {
        return extra_cheese_price;
    }

    public void setExtra_cheese_price(String extra_cheese_price) {
        this.extra_cheese_price = extra_cheese_price;
    }

    public String getExtra_topping_price() {
        return extra_topping_price;
    }

    public void setExtra_topping_price(String extra_topping_price) {
        this.extra_topping_price = extra_topping_price;
    }

    public String getExtra_cheese_burst_price() {
        return extra_cheese_burst_price;
    }

    public void setExtra_cheese_burst_price(String extra_cheese_burst_price) {
        this.extra_cheese_burst_price = extra_cheese_burst_price;
    }

    public String getExtra_wheat_crust_price() {
        return extra_wheat_crust_price;
    }

    public void setExtra_wheat_crust_price(String extra_wheat_crust_price) {
        this.extra_wheat_crust_price = extra_wheat_crust_price;
    }

    public String getExtra_italian_crust_price() {
        return extra_italian_crust_price;
    }

    public void setExtra_italian_crust_price(String extra_italian_crust_price) {
        this.extra_italian_crust_price = extra_italian_crust_price;
    }

    public String getExtra_pan_crush_price() {
        return extra_pan_crush_price;
    }

    public void setExtra_pan_crush_price(String extra_pan_crush_price) {
        this.extra_pan_crush_price = extra_pan_crush_price;
    }


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getOffer_details() {
        return offer_details;
    }

    public void setOffer_details(String offer_details) {
        this.offer_details = offer_details;
    }

    public String getDiscount_price() {
        return discount_price;
    }

    public void setDiscount_price(String discount_price) {
        this.discount_price = discount_price;
    }

    private String topping_id;
    private String topping_name;
    private String offer_details;
    private String discount_price;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public String getMenu_name() {
        return menu_name;
    }

    public void setMenu_name(String menu_name) {
        this.menu_name = menu_name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getOffer_id() {
        return offer_id;
    }

    public void setOffer_id(String offer_id) {
        this.offer_id = offer_id;
    }

    public String getTopping_count() {
        return topping_count;
    }

    public void setTopping_count(String topping_count) {
        this.topping_count = topping_count;
    }

    public String getCat_id() {
        return cat_id;
    }

    public void setCat_id(String cat_id) {
        this.cat_id = cat_id;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getSub_category_name() {
        return sub_category_name;
    }

    public void setSub_category_name(String sub_category_name) {
        this.sub_category_name = sub_category_name;
    }

    public String getTopping_id() {
        return topping_id;
    }

    public void setTopping_id(String topping_id) {
        this.topping_id = topping_id;
    }

    public String getTopping_name() {
        return topping_name;
    }

    public void setTopping_name(String topping_name) {
        this.topping_name = topping_name;
    }
}