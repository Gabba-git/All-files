package com.nan.greedygame;

import android.app.Application;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by arvind on 19/4/17.
 */

public class Global extends Application {
    ArrayList<LatLng> locations = new ArrayList<>();
    ArrayList<Integer> time = new ArrayList<>();
    ArrayList<String> region = new ArrayList<>();

    public ArrayList<LatLng> getLocations(){
        return  locations;
    }
    public ArrayList<Integer> gettime(){
        return  time;
    }
    public ArrayList<String> getregion(){
        return  region;
    }
}
