package com.buffet_user.adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.buffet_user.R;
import com.buffet_user.activity.CartActivity;
import com.buffet_user.global.UserSharedPreferenceData;
import com.buffet_user.network.ApiClientBase;
import com.buffet_user.network.ApiClientDeleteItem;
import com.buffet_user.network.ApiClientGetFromCart;
import com.buffet_user.pojo.getfromcartpojo.CartinfoPojo;
import com.buffet_user.pojo.getfromcartpojo.ItemInfoPojo;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by akshaybmsa96 on 09/11/17.
 */

public class CustomAdapterCart extends RecyclerView.Adapter<CustomAdapterCart.ViewHolder> {

    private Context context;
    Activity activity;
    ArrayList<ItemInfoPojo> itemInfoPojo;
    ApiClientDeleteItem apiclientdeleteitem;


    public CustomAdapterCart(Context context, ArrayList<ItemInfoPojo> itemInfoPojo,Activity activity) {

        this.context = context;
        this.itemInfoPojo=itemInfoPojo;
        this.activity=activity;

    }


    @Override
    public CustomAdapterCart.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.cart_items_list_layout, parent, false);
        CustomAdapterCart.ViewHolder holder = new CustomAdapterCart.ViewHolder(view);


        return holder;
    }

    @Override
    public void onBindViewHolder(CustomAdapterCart.ViewHolder holder, int position) {
        holder.textViewItemName.setText(itemInfoPojo.get(position).getItem_name());
        holder.textViewPrice.setText("₹ " + itemInfoPojo.get(position).getTotal_price());
        holder.textViewSize.setText("Size : "+itemInfoPojo.get(position).getItem_size());
          if(itemInfoPojo.get(position).getItem_toppings().equals("")||itemInfoPojo.get(position).getItem_toppings()==null) {
            holder.textViewTopping.setText("No Choice Toppings");
           }
           else {
        holder.textViewTopping.setText("Choice Toppings : "+itemInfoPojo.get(position).getItem_toppings());
           }

            if(itemInfoPojo.get(position).getExtra_add_on().equals("")||itemInfoPojo.get(position).getExtra_add_on()==null)
            {
            holder.textViewExtras.setText("No Extra Selected");
            }
          else{
        holder.textViewExtras.setText("Extras : "+itemInfoPojo.get(position).getExtra_add_on());
           }

    }


    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {


        TextView textViewItemName,textViewPrice,textViewSize,textViewTopping,textViewExtras;
        Button buttonRemoveItem;



        public ViewHolder(View view) {
            super(view);
            textViewItemName=(TextView)view.findViewById(R.id.textViewItemName);
            textViewPrice=(TextView)view.findViewById(R.id.textViewPrice);
            textViewSize=(TextView)view.findViewById(R.id.textViewSize);
            textViewTopping=(TextView)view.findViewById(R.id.textViewToppings);
            textViewExtras=(TextView)view.findViewById(R.id.textViewExtras);
            buttonRemoveItem=(Button)view.findViewById(R.id.buttonRemoveItem);
            view.setOnClickListener(this);
            view.setOnLongClickListener(this);

            buttonRemoveItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "Item " +itemInfoPojo.get(getPosition()).getId()+ " be Delete", Toast.LENGTH_SHORT).show();
                    deleteitem(getPosition());
                }
            });

        }

        @Override
        public void onClick(View view) {


        }

        @Override
        public boolean onLongClick(View view) {
            //   Toast.makeText(context, "Item will be Delete", Toast.LENGTH_SHORT).show();
            return true;
        }

    }

    private void deleteitem(int position) {
        final ProgressDialog pDialog = new ProgressDialog(context);
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);
        pDialog.show();
        // show it
        pDialog.show();
        apiclientdeleteitem = ApiClientBase.getApiClient().create(ApiClientDeleteItem.class);
        Call<String> call= apiclientdeleteitem.getItems(itemInfoPojo.get(position).getId());
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                String res=response.body();
                if(res.equals("1"))
                {
                    Toast.makeText(context, "Item Deleted From Cart", Toast.LENGTH_SHORT).show();
                    itemInfoPojo.remove(position);
                    notifyDataSetChanged();
                }

                else
                {
                    Toast.makeText(context,"Something went wrong dontknow what",Toast.LENGTH_SHORT).show();
                    notifyDataSetChanged();
                }
                pDialog.dismiss();

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                pDialog.hide();

                Toast.makeText(context,"Something went wrong",Toast.LENGTH_SHORT).show();
                System.out.println("failure"+"+ : "+t.getMessage());
                System.out.println("failure"+"+ : "+t.getCause());
                System.out.println("failure"+"+ : "+t.toString());
            }
        });
    }


    @Override
    public int getItemCount() {

        return itemInfoPojo.size();
    }


}