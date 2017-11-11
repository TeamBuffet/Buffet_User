package com.buffet_user.pojo.getfromcartpojo;

import java.util.ArrayList;

/**
 * Created by akshaybmsa96 on 09/11/17.
 */

public class CartinfoPojo
{
    private ArrayList<ItemInfoPojo> items;
    private String error;

    public ArrayList<ItemInfoPojo> getItems() {
        return items;
    }

    public void setItems(ArrayList<ItemInfoPojo> items) {
        this.items = items;
    }

    public String getError ()
    {
        return error;
    }

    public void setError (String error)
    {
        this.error = error;
    }



    @Override
    public String toString()
    {
        return "ClassPojo [error = "+error+", items = "+items+"]";
    }
}

