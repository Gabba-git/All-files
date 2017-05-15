package com.nan.weather_open;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by arvind on 27/11/16.
 */

public class Listadapter extends BaseAdapter {
    ArrayList<String> temp = new ArrayList<>();
    ArrayList<String> min = new ArrayList<>();
    ArrayList<String> max = new ArrayList<>();
    ArrayList<String> even = new ArrayList<>();
    ArrayList<String> description = new ArrayList<>();
    ArrayList<String> days = new ArrayList<>();
    Activity activity;
    public Listadapter(Activity activity,ArrayList<String> temp,ArrayList<String> days,ArrayList<String> even,ArrayList<String> min,ArrayList<String> max,ArrayList<String> description){
        this.temp = temp;
        this.max = max;
        this.min = min;
        this.description = description;
        this.even = even;
        this.activity = activity;
        this.days = days;
    }
    @Override
    public int getCount() {
        return temp.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }
    private class ViewHolder {
        TextView temp_data;
        TextView min_data;
        TextView max_data;
        TextView even_data;
        TextView description_data;
        TextView day;
        //  ImageView product_image;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        LayoutInflater inflater = activity.getLayoutInflater();if (convertView == null)
        {
            convertView = inflater.inflate(R.layout.weather_row, null);
            viewHolder = new ViewHolder();
            viewHolder.temp_data = (TextView) convertView.findViewById(R.id.temp_data);
            viewHolder.max_data = (TextView) convertView.findViewById(R.id.max_temp);
            viewHolder.min_data = (TextView) convertView.findViewById(R.id.min_temp);
            viewHolder.even_data = (TextView) convertView.findViewById(R.id.evening_temp);
            viewHolder.description_data = (TextView) convertView.findViewById(R.id.main_data);
            viewHolder.day = (TextView) convertView.findViewById(R.id.city_text);
            convertView.setTag(viewHolder);
        } else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.temp_data.setText(temp.get(position));
        viewHolder.max_data.setText(max.get(position));
        viewHolder.min_data.setText(min.get(position));
        viewHolder.even_data.setText(even.get(position));
        viewHolder.day.setText(days.get(position));
        viewHolder.description_data.setText(description.get(position));

        return convertView;
    }
}
