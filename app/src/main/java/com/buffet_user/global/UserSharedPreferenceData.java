package com.buffet_user.global;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

public class UserSharedPreferenceData
{
    static final String PREF_LOGGEDIN_USER_EMAIL = "logged_in_email";
    static final String PREF_USER_LOGGEDIN_STATUS = "logged_in_status";
    static final String PREF_LOGGEDIN_USER_NAME = "logged_in_username";
    static final String PREF_LOGGEDIN_USER_PHN = "logged_in_user_phn";
    static final String PREF_LOGGEDIN_USER_GENDER = "logged_in_user_gender";
    static final String PREF_LOGGEDIN_USER_ADDRESS = "logged_in_user_address";


    public static SharedPreferences getSharedPreferences(Context ctx)
    {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    public static void setLoggedInUserName(Context ctx, String name)
    {
        Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_LOGGEDIN_USER_NAME, name);
        editor.commit();
    }

    public static String getPrefLoggedinUserName(Context ctx)
    {
        return getSharedPreferences(ctx).getString(PREF_LOGGEDIN_USER_NAME, "");
    }

    public static void setLoggedInUserPhn(Context ctx, String phn)
    {
        Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_LOGGEDIN_USER_PHN, phn);
        editor.commit();
    }

    public static String getPrefLoggedinUserPhn(Context ctx)
    {
        return getSharedPreferences(ctx).getString(PREF_LOGGEDIN_USER_PHN, "1234");
    }

    public static void setPrefLoggedinUserGender(Context ctx, String gender)
    {
        Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_LOGGEDIN_USER_GENDER, gender);
        editor.commit();
    }

    public static String getPrefLoggedinUserGender(Context ctx)
    {
        return getSharedPreferences(ctx).getString(PREF_LOGGEDIN_USER_GENDER, "");
    }

    public static void setPrefLoggedinUserAddress(Context ctx, String address)
    {
        Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_LOGGEDIN_USER_ADDRESS, address);
        editor.commit();
    }

    public static String getPrefLoggedinUserAddress(Context ctx)
    {
        return getSharedPreferences(ctx).getString(PREF_LOGGEDIN_USER_ADDRESS, "");
    }


    public static void setLoggedInUserEmail(Context ctx, String email)
    {
        Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_LOGGEDIN_USER_EMAIL, email);
        editor.commit();
    }

    public static String getLoggedInEmailUser(Context ctx)
    {
        return getSharedPreferences(ctx).getString(PREF_LOGGEDIN_USER_EMAIL, "");
    }

    public static void setUserLoggedInStatus(Context ctx, boolean status)
    {
        Editor editor = getSharedPreferences(ctx).edit();
        editor.putBoolean(PREF_USER_LOGGEDIN_STATUS, status);
        editor.commit();
    }

    public static boolean getUserLoggedInStatus(Context ctx)
    {
        return getSharedPreferences(ctx).getBoolean(PREF_USER_LOGGEDIN_STATUS, false);
    }

    public static void clearLoggedInEmailAddress(Context ctx)
    {
        Editor editor = getSharedPreferences(ctx).edit();
        editor.remove(PREF_LOGGEDIN_USER_EMAIL);
        editor.remove(PREF_USER_LOGGEDIN_STATUS);
        editor.remove(PREF_LOGGEDIN_USER_NAME);
        editor.remove(PREF_LOGGEDIN_USER_GENDER);
        editor.remove(PREF_LOGGEDIN_USER_PHN);
        editor.remove(PREF_LOGGEDIN_USER_ADDRESS);
        editor.commit();
    }
}
