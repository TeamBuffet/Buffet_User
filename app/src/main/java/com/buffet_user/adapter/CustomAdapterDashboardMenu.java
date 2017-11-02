package com.buffet_user.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.buffet_user.R;
import com.buffet_user.pojo.MenuPojo;
import com.buffet_user.pojo.SingleMenuPojo;
import com.squareup.picasso.Picasso;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;

import java.util.ArrayList;

/**
 * Created by akshaybmsa96 on 29/10/17.
 */

public class CustomAdapterDashboardMenu extends RecyclerView.Adapter<CustomAdapterDashboardMenu.ViewHolder> implements StickyRecyclerHeadersAdapter<RecyclerView.ViewHolder> {

    private Context context;
    Activity activity;
    int i=0;
    ArrayList <SingleMenuPojo> singlemenupojo=new ArrayList<>();

    public CustomAdapterDashboardMenu(Context context, ArrayList <SingleMenuPojo> singlemenupojo) {

        this.context = context;
        this.singlemenupojo = singlemenupojo;
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
        holder.textViewDescription.setText(singlemenupojo.get(position).getSize()+" @ "+ singlemenupojo.get(position).getPrice());
        Picasso.with(context).load(singlemenupojo.get(position).getImage()).into(holder.imageView);

    }


    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {


        TextView textViewItemName;
        TextView textViewDescription;
        ImageView imageView;


        public ViewHolder(View view) {
            super(view);
            textViewItemName=(TextView)view.findViewById(R.id.textViewItemName);
            textViewDescription=(TextView)view.findViewById(R.id.textViewDescription);
            imageView=(ImageView)view.findViewById(R.id.imageView);
            view.setOnClickListener(this);
            view.setOnLongClickListener(this);


        }

        @Override
        public void onClick(View view) {

           // Intent i = new Intent(context, ConfirmedOrdersFullDetailsActivity.class);
           // Gson gson = new Gson();
          //  i.putExtra("orderDetails", gson.toJson(orderlist.get(getPosition())));
          //  context.startActivity(i);

        }

        @Override
        public boolean onLongClick(View view) {
            //   Toast.makeText(context, "Item will be Delete", Toast.LENGTH_SHORT).show();
            return true;
        }

    }


    @Override
    public long getHeaderId(int position) {
        return singlemenupojo.get(position).getCat_id();
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


