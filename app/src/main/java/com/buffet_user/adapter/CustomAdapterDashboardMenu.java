package com.buffet_user.adapter;

/**
 * Created by Ankit on 08/11/17.
 */

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.buffet_user.R;
import com.buffet_user.fragment.review.CategorizationFragment;
import com.buffet_user.global.UserSharedPreferenceData;
import com.buffet_user.pojo.CartAddPojo;
import com.buffet_user.pojo.SingleMenuPojo;
import com.buffet_user.retrofit.ApiUtils;
import com.buffet_user.retrofit.SOService;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by akshaybmsa96 on 29/10/17.
 */

public class CustomAdapterDashboardMenu extends RecyclerView.Adapter<CustomAdapterDashboardMenu.ViewHolder> implements StickyRecyclerHeadersAdapter<RecyclerView.ViewHolder> {

    private Context context;
    Activity activity;
    int i = 0;
    ArrayList<SingleMenuPojo> singlemenupojo = new ArrayList<>();
    CartAddPojo cartAddPojo;
    private SOService mService;

    public CustomAdapterDashboardMenu(Context context, ArrayList<SingleMenuPojo> singlemenupojo) {

        this.context = context;
        this.singlemenupojo = singlemenupojo;
        mService = ApiUtils.getSOService();

    }


    @Override
    public CustomAdapterDashboardMenu.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dashboard_list_layout, parent, false);
        CustomAdapterDashboardMenu.ViewHolder holder = new CustomAdapterDashboardMenu.ViewHolder(view);


        return holder;
    }

    @Override
    public void onBindViewHolder(CustomAdapterDashboardMenu.ViewHolder holder, int position) {
        holder.textViewItemName.setText(singlemenupojo.get(position).getMenu_name());
        holder.textViewDescription.setText(singlemenupojo.get(position).getSize() + " @ " + singlemenupojo.get(position).getPrice());
        Picasso.with(context).load(singlemenupojo.get(position).getImage()).into(holder.imageView);

    }


    class ViewHolder extends RecyclerView.ViewHolder {


        TextView textViewItemName;
        TextView textViewDescription;
        ImageView imageView;
        Button buttonAdd;


        public ViewHolder(View view) {
            super(view);
            textViewItemName = (TextView) view.findViewById(R.id.textViewItemName);
            textViewDescription = (TextView) view.findViewById(R.id.textViewDescription);
            imageView = (ImageView) view.findViewById(R.id.imageView);
            buttonAdd = (Button) view.findViewById(R.id.buttonAdd);
            buttonAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    /*Intent i = new Intent(context, FullItemDetailActivity.class);
                      Gson gson = new Gson();
                       i.putExtra("itemDetails", gson.toJson(singlemenupojo.get(getPosition())));
                       context.startActivity(i);
             */

                    //   FragmentActivity activity = (new FragmentActivity(getContext()));
                    if (singlemenupojo.get(getPosition()).isCustomisation().equals("0") || singlemenupojo.get(getPosition()).isCustomisation() == null) {

                        //add to cart direct
                        setPOJO(getPosition());
                        Gson gson = new Gson();
                        String postData = (gson.toJson(cartAddPojo));
                        System.out.println(postData);
                        //   Toast.makeText(getContext(),finalTopping+" "+extraddons,Toast.LENGTH_SHORT).show();
                        addToDb(postData);

                    } else {
                        FragmentTransaction fragmentTransaction = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
                        Fragment prev = ((FragmentActivity) context).getSupportFragmentManager().findFragmentByTag("dialog");
                        if (prev != null) {
                            fragmentTransaction.remove(prev);
                        }
                        fragmentTransaction.addToBackStack(null);
                        // Create and show the dialog.
                        Gson gson = new Gson();
                        DialogFragment newFragment = CategorizationFragment.newInstance(gson.toJson(singlemenupojo.get(getPosition())));
                        newFragment.show(fragmentTransaction, "dialog");

                    }
                }
            });

        }


        private void addToDb(String postData) {

            final ProgressDialog pDialog = new ProgressDialog(context);
            pDialog.setMessage("Adding...");
            pDialog.setCancelable(false);
            pDialog.show();
            // show it
            pDialog.show();
            mService.addCartItems(postData).enqueue(new Callback<Integer>() {
                @Override
                public void onResponse(Call<Integer> call, Response<Integer> response) {

                    int isTrans = response.body();
                    if (isTrans == 1) {
                        Toast.makeText(activity, "Item Added To Cart", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(activity, "Cannot Add", Toast.LENGTH_SHORT).show();
                    }
                    pDialog.dismiss();

                }

                @Override
                public void onFailure(Call<Integer> call, Throwable t) {
                    pDialog.hide();

                    Toast.makeText(activity, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            });
        }

        private void setPOJO(int position) {
            cartAddPojo = new CartAddPojo();
            UserSharedPreferenceData userSharedPreferenceData = new UserSharedPreferenceData();
            cartAddPojo.setUser_phn_number(userSharedPreferenceData.getPrefLoggedinUserPhn(context));
            cartAddPojo.setItem_name(singlemenupojo.get(position).getMenu_name());
            cartAddPojo.setItem_size(singlemenupojo.get(position).getSize());
            cartAddPojo.setItem_base_price(singlemenupojo.get(position).getPrice());
            cartAddPojo.setItem_toppings("");
            cartAddPojo.setExtra_add_on("");
            cartAddPojo.setExtra_price(String.valueOf(""));
            cartAddPojo.setTotal_price(singlemenupojo.get(position).getPrice());
        }

    }

    @Override
    public long getHeaderId(int position) {

        return Long.parseLong(singlemenupojo.get(position).getCat_id());
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_header, parent, false);
        return new RecyclerView.ViewHolder(view) {
        };
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {

        TextView textView = (TextView) holder.itemView.findViewById(R.id.textView);
        textView.setText(singlemenupojo.get(position).getSub_category_name());
        //  Toast.makeText(context,singlemenupojo.get(position).getMenu_name()+"-"+singlemenupojo.get(position).getSub_category_name(),Toast.LENGTH_SHORT).show();

    }

    @Override
    public int getItemCount() {

        return singlemenupojo.size();
    }


}
