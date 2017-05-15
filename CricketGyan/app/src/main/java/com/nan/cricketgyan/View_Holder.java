package com.nan.cricketgyan;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by arvind on 13/3/17.
 */

class View_Holder extends RecyclerView.ViewHolder{
    TextView name;
    CircleImageView image;
    ImageView forward;
    RelativeLayout layout;



    public View_Holder(View itemView) {
        super(itemView);
        name = (TextView) itemView.findViewById(R.id.name);
        image = (CircleImageView) itemView.findViewById(R.id.player_icon);
        forward = (ImageView) itemView.findViewById(R.id.forward_icon);
        layout = (RelativeLayout) itemView.findViewById(R.id.card_layout);

    }
}
