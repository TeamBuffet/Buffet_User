package com.buffet_user.global.utils;

/**
 * Created by Ankit on 12/11/17.
 */

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by akshaybmsa96 on 03/11/17.
 */

public class ProcessStringTopping {


    public ArrayList<String> process1(String str) {
        ArrayList<String> list = new ArrayList<String>();

        String[] str_chop = str.split(",");
        for (String value : str_chop) {
            list.add(value);
        }
        return list;
    }

    public JSONArray process(String str) {

        JSONArray jsonArray = new JSONArray();

        ArrayList<String> list = process1(str);
        for (String val : list) {
            if (val.contains("/")) {
                String[] str_chop = val.split("/");
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("value", Arrays.toString(str_chop));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                jsonArray.put(jsonObject);
            }

        }

        return jsonArray;
    }


}
