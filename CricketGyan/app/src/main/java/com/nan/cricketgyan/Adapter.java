package com.nan.cricketgyan;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by arvind on 13/3/17.
 */

public class Adapter extends RecyclerView.Adapter<View_Holder> {

    ArrayList <Details> list = new ArrayList<>();
    Activity context;
    URL url;
    Bitmap bm;

    public Adapter(ArrayList<Details> list, Activity context){
        this.list = list;
        this.context = context;
    }
    @Override
    public View_Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card,parent,false);
        View_Holder viewholder = new View_Holder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(final View_Holder holder, final int position) {
        holder.name.setText(list.get(position).name);
        Picasso.with(context).load(list.get(position).image).into(holder.image);


        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,Player.class);
                intent.putExtra("name",list.get(position).name);
                intent.putExtra("country",list.get(position).country);
                intent.putExtra("match",list.get(position).matches);
                intent.putExtra("score",list.get(position).runs);
                intent.putExtra("description",list.get(position).description);
                intent.putExtra("image",list.get(position).image);
                ActivityOptionsCompat options = ActivityOptionsCompat.
                        makeSceneTransitionAnimation(context,holder.image, "profile");
                context.startActivity(intent, options.toBundle());
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
