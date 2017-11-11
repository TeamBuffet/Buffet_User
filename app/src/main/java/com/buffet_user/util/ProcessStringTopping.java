package com.buffet_user.util;

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
        for (String val : list)
        {
            if (val.contains("/")) {
                String[] str_chop = val.split("/");
                JSONObject jsonObject = new JSONObject();
                try
                {
                    jsonObject.put("value", Arrays.toString(str_chop));
                }

                catch (JSONException e)
                {
                    e.printStackTrace();
                }
                jsonArray.put(jsonObject);
            }
            /*
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject js = null;
                try {
                    js = jsonArray.getJSONObject(i);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                System.out.println(js);
            }
*/

        }

     //   System.out.println(jsonArray.length());
    //    System.out.println(jsonArray.toString());


        return jsonArray;
    }


/*

    public static void main(String args[]) throws JSONException {
        JSONArray jsonArray = new JSONArray();

        ProcessStringTopping obj = new ProcessStringTopping();

        ArrayList<String> list = obj.process1("cheese,extraSomething/anything,something,oohh/aahhh,fuck,chodu/mc");
        for (String val : list) {
            if (val.contains("/")) {
                String[] str_chop = val.split("/");
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("value", str_chop);
                jsonArray.put(jsonObject);
            }
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject js = jsonArray.getJSONObject(i);
                System.out.println(js);
            }
        }


    /*
public static void main(String args[]) {
ProcessStringTopping obj = new ProcessStringTopping();
ArrayList<String> list=obj.process("cheese,extraSomething/anything,something");
    for (String val:list)
        {
            System.out.println(val);
            }
}
*/

    }

