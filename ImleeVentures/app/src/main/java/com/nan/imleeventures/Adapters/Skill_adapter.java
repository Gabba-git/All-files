package com.nan.imleeventures.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nan.imleeventures.MainActivity;
import com.nan.imleeventures.Questions;
import com.nan.imleeventures.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by arvind on 19/10/16.
 */

public class Skill_adapter extends RecyclerView.Adapter<Skill_adapter.viewholder> {

    private ArrayList<String> skills;
    private Activity context;

    public  Skill_adapter( Activity context,ArrayList<String> data){
        this.skills = data;
        this.context = context;
        Log.i("skill_costructor",skills.toString());

    }
    public static class viewholder extends RecyclerView.ViewHolder{
        public TextView skill_text;
        public viewholder(View itemView) {
            super(itemView);
            skill_text = (TextView) itemView.findViewById(R.id.skill_text);
        }
    }

    @Override
    public viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.skills,parent,false);
          Log.i("inviewholder",skills.toString());

        return new viewholder(itemView);
    }

    @Override
    public void onBindViewHolder(viewholder holder, final int position) {
         Log.i("valuelistner2",skills.get(position));

        holder.skill_text.setText(skills.get(position));
        holder.skill_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,Questions.class);
                intent.putExtra("title",skills.get(position));
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return skills.size();
    }
}
