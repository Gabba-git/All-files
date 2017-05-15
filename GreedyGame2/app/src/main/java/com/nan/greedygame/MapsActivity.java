package com.nan.greedygame;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarFinalValueListener;
import com.crystal.crystalrangeseekbar.widgets.CrystalRangeSeekbar;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.TileOverlay;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.maps.android.heatmaps.HeatmapTileProvider;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


import static java.lang.Thread.sleep;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    Toolbar toolbar;

    HeatmapTileProvider mprovider;
    TileOverlay overlay = null;
    Button autoplay_mode, seek,seek_button,autoplay;
    CrystalRangeSeekbar rangeSeekbar;
    TextView text1,text2;
    Handler handler = new Handler();
    Runnable runnable;
    RelativeLayout layout,auto_layout;
    EditText editText;
    ArrayList<LatLng> list = new ArrayList<>();
    ArrayList<String> name = new ArrayList<>();

    int size;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        size = ((Global) getApplicationContext()).time.size();

        toolbar = (Toolbar)findViewById(R.id.toolbar_main);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle(R.string.app_name);
            getSupportActionBar().setHomeButtonEnabled(true);
        }

        autoplay = (Button) findViewById(R.id.autoplay_button);
        seek = (Button) findViewById(R.id.seekmode);
        rangeSeekbar = (CrystalRangeSeekbar) findViewById(R.id.rangebar);
        text1 = (TextView) findViewById(R.id.textMin1);
        text2 = (TextView) findViewById(R.id.textMax1);
        editText = (EditText) findViewById(R.id.time);
        layout = (RelativeLayout) findViewById(R.id.seek_layout);
        seek_button = (Button) findViewById(R.id.seek_button);
        auto_layout = (RelativeLayout) findViewById(R.id.auto_layout);

        autoplay_mode = (Button) findViewById(R.id.autoplay);

       // SupportMapFragment mapFragment = (SupportMapFragment) getFragmentManager().findFragmentById(R.id.map);
        //mapFragment.getMapAsync(this);



        autoplay_mode.setBackgroundColor(Color.BLACK);
        autoplay_mode.setTextColor(Color.WHITE);
        ArrayList<LatLng> all = ((Global)getApplicationContext()).getLocations();



        Log.d("-->" , "all : " + all.size()+"");




        /*if(mprovider == null){*/
            //mprovider = new HeatmapTileProvider.Builder().data(((Global)getApplicationContext()).getLocations()).build();
            mprovider = new HeatmapTileProvider.Builder().data(all).build();

           /*final HeatmapTileProvider mprovider2 = new HeatmapTileProvider.Builder().data(all).build();
            //while(getMap() == null);
           Runnable r = new Runnable() {
               @Override
               public void run() {
                   overlay.remove();
                   handler.postDelayed(new Runnable() {
                       @Override
                       public void run() {
                           Toast.makeText(MapsActivity.this, "overlay called", Toast.LENGTH_SHORT).show();
                           overlay = getMap().addTileOverlay(new TileOverlayOptions().tileProvider(mprovider));

                           handler.postDelayed(new Runnable() {
                               @Override
                               public void run() {
                                    overlay.remove();
                                   Log.d("-->", "remo0000ved");
                                   handler.postDelayed(new Runnable() {
                                       @Override
                                       public void run() {
                                           Log.d("-->", "New one");
                                           mprovider = new HeatmapTileProvider.Builder().data(sub).build();
                                           overlay = getMap().addTileOverlay(new TileOverlayOptions().tileProvider(mprovider));
                                           handler.postDelayed(new Runnable() {
                                               @Override
                                               public void run() {
//                                                   overlay.remove();
                                               }
                                           }, 5000);
                                       }
                                   }, 1);
                               }
                           }, 5000);

                       }
                   },5000);
               }
           };*/

//           handler.postDelayed(r,3000);

        //}
       /*else {
            mprovider.setData(list);
            overlay.clearTileCache();
        }*/
        autoplay_mode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.clear();
                overlay = mMap.addTileOverlay(new TileOverlayOptions().tileProvider(mprovider));
                seek.setBackgroundColor(Color.WHITE);
                seek.setTextColor(Color.BLACK);
                autoplay_mode.setBackgroundColor(Color.BLACK);
                autoplay_mode.setTextColor(Color.WHITE);
                auto_layout.setVisibility(View.VISIBLE);
                layout.setVisibility(View.GONE);
            }
        });

        autoplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mMap.clear();

                Toast.makeText(MapsActivity.this,"Auto Playing",Toast.LENGTH_SHORT).show();
                rangeSeekbar.setVisibility(View.GONE);
                final int[] temp = new int[1];

                int start = Integer.parseInt(text1.getText().toString()) * 100;
                final int finish = Integer.parseInt(text2.getText().toString()) * 100;
                temp[0] = start;

                /*handler.post(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setProgress(finish);
                    }
                });*/
                final int finalFinish = finish;
                final int loop_end;
                /*if(finish == 6000){
                    loop_end = size;
                }
                else {
                    loop_end = ((Global)getApplicationContext()).time.indexOf(temp[0]+500);
                }*/

                runnable = new Runnable() {
                    @Override
                    public void run() {
                        overlay.remove();
                        final ArrayList<LatLng> sublocation = new ArrayList<LatLng>();
                        int loop_end;
                        if(temp[0]+100 == 6000){
                            loop_end = size;
                        }
                        else {
                            loop_end = ((Global)getApplicationContext()).time.indexOf(temp[0]+100);
                        }

                        for(int i = ((Global)getApplicationContext()).time.indexOf(temp[0]); i<loop_end; i++){
                            sublocation.add(((Global)getApplicationContext()).locations.get(i));
                            if(i >= size-1 ){
                                break;
                            }
                        }

                        Log.d("-->" , "sub : " + sublocation.size()+"");

                        //handler.postDelayed(runnable,3000);

                        ///Log.d("-->" , "sub : " + sublocation+"");


                        /*mprovider = new HeatmapTileProvider.Builder().data(sublocation).build();*/
                        HeatmapTileProvider mprovider1 = new HeatmapTileProvider.Builder().data(sublocation).build();
                        overlay = mMap.addTileOverlay(new TileOverlayOptions().tileProvider(mprovider1));
                        //Log.d("-->" , " thread called " + sublocation.size()+"");



                        temp[0] = temp[0] + 100;

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                text1.setText(temp[0] /100+"");

                            }
                        });
                        sublocation.clear();
                        handler.postDelayed(runnable,3000);
                        if(temp[0]/100 >= finalFinish/100){
                            rangeSeekbar.setVisibility(View.VISIBLE);
                            Log.d("-->" , "inside regex : " +finalFinish+"");
                            handler.removeCallbacks(runnable);
                        }


                    }

                };
                handler.post(runnable);

            }
        });

        seek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auto_layout.setVisibility(View.GONE);
                layout.setVisibility(View.VISIBLE);
                list.clear();
                name.clear();
                seek.setBackgroundColor(Color.BLACK);
                seek.setTextColor(Color.WHITE);
                autoplay_mode.setBackgroundColor(Color.WHITE);
                autoplay_mode.setTextColor(Color.BLACK);
                overlay.remove();

            }
        });


        seek_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MapsActivity.this, "Users on this Instant",Toast.LENGTH_SHORT).show();
                String time = editText.getText().toString();
                int time_int = Integer.parseInt(time.replace(":",""));
                if(time_int > 20000 || time_int < 10000){
                    editText.setError("Enter time between 1-2");
                }else {
                    list.clear();
                    name.clear();
                    overlay.remove();

                    time_int -= 10000;
                    if(time_int%100 == 59){
                        time_int +=100-59;
                    }
                    for(int i=((Global)getApplicationContext()).time.indexOf(time_int); i<((Global)getApplicationContext()).time.indexOf(time_int+1);i++){
                        list.add(((Global)getApplicationContext()).locations.get(i));
                        name.add(((Global)getApplicationContext()).region.get(i));
                    }
                    addMarker(list);
                }

            }
        });

        rangeSeekbar.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
            @Override
            public void valueChanged(Number minValue, Number maxValue) {
                text1.setText(String.valueOf(minValue));
                text2.setText(String.valueOf(maxValue));
            }
        });

// set final value listener
        rangeSeekbar.setOnRangeSeekbarFinalValueListener(new OnRangeSeekbarFinalValueListener() {
            @Override
            public void finalValue(Number minValue, Number maxValue) {
                Log.d("CRS=>", String.valueOf(minValue) + " : " + String.valueOf(maxValue));
            }
        });

    }

    private void addMarker(ArrayList<LatLng> list) {
        mMap.clear();
        for(int i=0; i<list.size(); i++){
            mMap.addMarker(new MarkerOptions().position(list.get(i))).setTitle(name.get(i));
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        setUpMap();
    }

    @Override
    public void onMapReady(GoogleMap map) {
        if (mMap != null) {
            return;
        }

        mMap = map;
       // Toast.makeText(MapsActivity.this, "onMapReady called", Toast.LENGTH_SHORT).show();
        Log.d("-->", "onMapReady");
        overlay = mMap.addTileOverlay(new TileOverlayOptions().tileProvider(mprovider));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(22.9734,78.6569),3.8f));
//        overlay = mMap.addTileOverlay(new TileOverlayOptions().tileProvider(mprovider));

    }
    private void setUpMap() {
        ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMapAsync(this);
    }

    /**
     * Run the demo-specific code.
     */


    protected GoogleMap getMap() {
        return mMap;
    }




    /*public class ipsync extends AsyncTask<Context,Integer,ArrayList<LatLng>>{
        int initial = Integer.parseInt(text1.getText().toString()) * 100;
        int end = Integer.parseInt(text2.getText().toString()) * 100;



        @Override
        protected ArrayList<LatLng> doInBackground(Context... params) {
            if(end >= size){
                end = size;
            }
            for(int i=((Global)getApplicationContext()).time.indexOf(initial); i<((Global)getApplicationContext()).time.indexOf(end); i++){
                temp_location.add(((Global)getApplicationContext()).locations.get(i));
            }
            return temp_location;
        }
    }*/


}
