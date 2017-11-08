package com.buffet_user.pojo;

import java.util.ArrayList;

/**
 * Created by Ankit on 08/11/17.
 */

public class CategoryPojo {
    private ArrayList<SingleMenuPojo> Sides;
    private ArrayList<SingleMenuPojo> Pizza;


    public ArrayList<SingleMenuPojo> getSides() {
        return Sides;
    }

    public void setSides(ArrayList<SingleMenuPojo> sides) {
        Sides = sides;
    }

    public ArrayList<SingleMenuPojo> getPizza() {
        return Pizza;
    }

    public void setPizza(ArrayList<SingleMenuPojo> pizza) {
        Pizza = pizza;
    }
}