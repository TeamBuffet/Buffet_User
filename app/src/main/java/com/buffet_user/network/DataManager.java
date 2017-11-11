package com.buffet_user.network;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

import com.buffet_user.pojo.getmenupojo.MenuPojo;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by akshaybmsa96 on 02/11/17.
 */

public class DataManager {
    Context context;
    MenuPojo menuPojoResult;

    public DataManager(Context context)
    {
        this.context=context;
    }

    public MenuPojo getDashboardData()
    {
        final ProgressDialog pDialog = new ProgressDialog(context);
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);
        pDialog.show();
        // show it
        ApiClientGetMenu apiClientGetMenu;
        apiClientGetMenu = ApiClientBase.getApiClient().create(ApiClientGetMenu.class);
        Call<MenuPojo> call= apiClientGetMenu.getInfo();
        call.enqueue(new Callback<MenuPojo>() {
            @Override
            public void onResponse(Call<MenuPojo> call, Response<MenuPojo> response) {


                MenuPojo menuPojo=response.body();

                menuPojoResult=menuPojo;

                pDialog.hide();

            }

            @Override
            public void onFailure(Call<MenuPojo> call, Throwable t) {
                pDialog.hide();

                Toast.makeText(context,"Something went wrong",Toast.LENGTH_SHORT).show();

            }
        });
        return menuPojoResult;
    }
}
