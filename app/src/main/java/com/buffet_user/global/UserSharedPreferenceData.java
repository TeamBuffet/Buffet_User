package com.buffet_user.global;

/**
 * Created by Ankit on 12/11/17.
 */

import android.content.Context;
import android.content.SharedPreferences;

import com.buffet_user.activity.BaseActivity;

public class UserSharedPreferenceData extends BaseActivity {
    static final String PREF_LOGGEDIN_USER_EMAIL = "logged_in_email";
    static final String PREF_USER_LOGGEDIN_STATUS = "logged_in_status";
    static final String PREF_LOGGEDIN_USER_NAME = "logged_in_username";
    static final String PREF_LOGGEDIN_USER_PHN = "logged_in_user_phn";
    static final String PREF_LOGGEDIN_USER_GENDER = "logged_in_user_gender";
    static final String PREF_LOGGEDIN_USER_ADDRESS = "logged_in_user_address";


    public SharedPreferences getSharedPreference(Context ctx) {

        return sharedPreferences;
    }

    public void setLoggedInUserName(Context ctx, String name) {
        editor.putString(PREF_LOGGEDIN_USER_NAME, name);
        editor.commit();
    }

    public String getPrefLoggedinUserName(Context ctx) {
        return sharedPreferences.getString(PREF_LOGGEDIN_USER_NAME, "");
    }

    public void setLoggedInUserPhn(Context ctx, String phn) {
        editor.putString(PREF_LOGGEDIN_USER_PHN, phn);
        editor.commit();
    }

    public String getPrefLoggedinUserPhn(Context ctx) {
        return sharedPreferences.getString(PREF_LOGGEDIN_USER_PHN, "1234");
    }

    public void setPrefLoggedinUserGender(Context ctx, String gender) {
        editor.putString(PREF_LOGGEDIN_USER_GENDER, gender);
        editor.commit();
    }

    public String getPrefLoggedinUserGender(Context ctx) {
        return sharedPreferences.getString(PREF_LOGGEDIN_USER_GENDER, "");
    }

    public void setPrefLoggedinUserAddress(Context ctx, String address) {
        editor.putString(PREF_LOGGEDIN_USER_ADDRESS, address);
        editor.commit();
    }

    public String getPrefLoggedinUserAddress(Context ctx) {
        return sharedPreferences.getString(PREF_LOGGEDIN_USER_ADDRESS, "");
    }


    public void setLoggedInUserEmail(Context ctx, String email) {
        editor.putString(PREF_LOGGEDIN_USER_EMAIL, email);
        editor.commit();
    }

    public String getLoggedInEmailUser(Context ctx) {
        return sharedPreferences.getString(PREF_LOGGEDIN_USER_EMAIL, "");
    }

    public void setUserLoggedInStatus(Context ctx, boolean status) {
        editor.putBoolean(PREF_USER_LOGGEDIN_STATUS, status);
        editor.commit();
    }

    public boolean getUserLoggedInStatus(Context ctx) {
        return sharedPreferences.getBoolean(PREF_USER_LOGGEDIN_STATUS, false);
    }

    public void clearLoggedInEmailAddress(Context ctx) {
        editor.remove(PREF_LOGGEDIN_USER_EMAIL);
        editor.remove(PREF_USER_LOGGEDIN_STATUS);
        editor.remove(PREF_LOGGEDIN_USER_NAME);
        editor.remove(PREF_LOGGEDIN_USER_GENDER);
        editor.remove(PREF_LOGGEDIN_USER_PHN);
        editor.remove(PREF_LOGGEDIN_USER_ADDRESS);
        editor.commit();
    }
}