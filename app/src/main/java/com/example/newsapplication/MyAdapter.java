package com.example.newsapplication;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    ArrayList<FeedItem> feedItems;
    Context context;
    public MyAdapter(Context context, ArrayList<FeedItem>feedItems){
        this.feedItems = feedItems;
        this.context = context;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_row_news_item, viewGroup, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        YoYo.with(Techniques.FadeIn).playOn(myViewHolder.cardView);
        final FeedItem current = feedItems.get(i);
        myViewHolder.title.setText(current.getTitle());
        myViewHolder.desc.setText(current.getDesc());
        myViewHolder.date.setText(current.getPubDate());
        Picasso.with(context).load(current.getThumbnail()).into(myViewHolder.thumb);
        myViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, NewsDetails.class);
                intent.putExtra("Link", current.getLink());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return feedItems.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title, desc, date;
        ImageView thumb;
        CardView cardView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title_text);
            desc = itemView.findViewById(R.id.desc);
            date = itemView.findViewById(R.id.date);
            thumb = itemView.findViewById(R.id.thumb);
            cardView = itemView.findViewById(R.id.cardview);
        }
    }
}
