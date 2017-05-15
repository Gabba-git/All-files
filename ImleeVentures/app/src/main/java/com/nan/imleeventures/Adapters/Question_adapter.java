package com.nan.imleeventures.Adapters;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.nan.imleeventures.R;

import java.util.ArrayList;

/**
 * Created by arvind on 24/10/16.
 */

public class Question_adapter extends RecyclerView.Adapter<Question_adapter.viewholder> {
    private ArrayList<String> questions,time,rupees;
    private Activity activity;
    public Question_adapter(Activity activity,ArrayList<String> questions, ArrayList<String> rupees,ArrayList<String> time){
        this.activity = activity;
        this.questions = questions;
        this.rupees = rupees;
        this.time = time;
    }
    public class viewholder extends RecyclerView.ViewHolder{
        TextView question_text,rupee_text,time_text;
        Button red,green;
        public viewholder(View itemView) {
            super(itemView);
            question_text = (TextView)itemView.findViewById(R.id.question_id);
            rupee_text = (TextView)itemView.findViewById(R.id.rupees);
            time_text = (TextView)itemView.findViewById(R.id.time_text);
            red = (Button) itemView.findViewById(R.id.decline_button);
            green = (Button) itemView.findViewById(R.id.accept_button);
        }
    }
    @Override
    public viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.questions_row,parent,false);

        return new viewholder(itemView);
    }

    @Override
    public void onBindViewHolder(final viewholder holder, final int position) {
            holder.question_text.setText(questions.get(position));
            holder.rupee_text.setText(rupees.get(position));
            holder.time_text.setText(time.get(position));
            holder.red.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(activity,
                            questions.get(position)+" is removed.",
                            Toast.LENGTH_SHORT).show();
                    questions.remove(position);
                    rupees.remove(position);
                    time.remove(position);
                    onBindViewHolder(holder,position);

                }
            });
            holder.green.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }
}
