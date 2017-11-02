package com.buffet_user.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.buffet_user.R;
import com.buffet_user.pojo.MessageFeedPOJO;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by Ankit on 03/11/17.
 */


public class ReviewFeedsAdapter extends RecyclerView.Adapter<ReviewFeedsAdapter.ReciewViewHolder> {

    private Context context;
    private List<MessageFeedPOJO> data;

    public ReviewFeedsAdapter(Context context, List<MessageFeedPOJO> data) {

        this.context = context;
        this.data = data;
    }


    @Override
    public ReciewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.blog_feed_card, null);
        ReciewViewHolder holder = new ReciewViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ReciewViewHolder holder, int position) {


        final MessageFeedPOJO current = data.get(position);
        holder.txtName.setText(current.getName());
        holder.txtCaption.setText(current.getCaption());
        holder.txtLikes.setText(String.valueOf(current.getLikes()));
        holder.txtDate.setText(getDate(Long.parseLong(current.getTimestamp())));
        Picasso.with(context).load(current.getUrl()).into(holder.imageItem);

        holder.imgLove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Item clicked "+current.getBlogId(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String getDate(long time) {
        time=time*1000;
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(time);
        String date = DateFormat.format("dd-MM-yyyy", cal).toString();
        return date;
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    class ReciewViewHolder extends RecyclerView.ViewHolder {

        TextView txtDate;
        TextView txtCaption;
        TextView txtName;
        TextView txtLikes;
        ImageButton imgLove;
        ImageView imageItem;

        public ReciewViewHolder(View view) {
            super(view);

            txtDate = (TextView) view.findViewById(R.id.txtTime);
            txtCaption = (TextView) view.findViewById(R.id.txtUserCaption);
            txtLikes = (TextView) view.findViewById(R.id.txtLikes);
            txtName = (TextView) view.findViewById(R.id.txtUserName);
            imgLove = (ImageButton) view.findViewById(R.id.imgLove);
            imageItem = (ImageView) view.findViewById(R.id.imgFeed);

        }

    }

}
