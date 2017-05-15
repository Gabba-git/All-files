package com.nan.greedygame;

import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Parcelable;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.*;
import java.util.ArrayList;

public class MainActivity extends FragmentActivity implements OnMapReadyCallback {
    String Url = "http://freegeoip.net/json/";
    String ip = "";
    ArrayList<String> ips = new ArrayList<>();
    ArrayList<LatLng> locations = new ArrayList<>();
    String ip_string = "112.198.78.80,2016-12-28 01:00:00.791600\n" +
            "175.157.80.115,2016-12-28 01:00:00.791600\n" +
            "49.33.40.172,2016-12-28 01:00:00.791600\n" +
            "27.97.201.244,2016-12-28 01:00:00.791600\n" +
            "47.9.91.70,2016-12-28 01:00:00.791600\n" +
            "157.50.182.171,2016-12-28 01:00:00.791600\n" +
            "70.196.80.52,2016-12-28 01:00:00.791600\n" +
            "27.62.16.58,2016-12-28 01:00:00.791600\n" +
            "47.9.32.144,2016-12-28 01:00:00.791600\n" +
            "47.29.7.83,2016-12-28 01:00:00.791600\n" +
            "157.50.198.67,2016-12-28 01:00:00.791600\n" +
            "73.155.101.228,2016-12-28 01:00:00.791600\n" +
            "42.111.207.186,2016-12-28 01:00:01.791610\n" +
            "42.111.204.227,2016-12-28 01:00:01.791610\n" +
            "203.214.122.145,2016-12-28 01:00:01.791610\n" +
            "157.50.183.86,2016-12-28 01:00:01.791610\n" +
            "162.104.235.50,2016-12-28 01:00:01.791610\n" +
            "112.79.143.142,2016-12-28 01:00:01.791610\n" +
            "188.83.2.84,2016-12-28 01:00:01.791610\n" +
            "27.62.119.87,2016-12-28 01:00:01.791610\n" +
            "157.48.129.89,2016-12-28 01:00:01.791610";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String[] ip_array = ip_string.split("\n");
        SupportMapFragment mapFragment = ((SupportMapFragment) getFragmentManager().findFragmentById(R.id.map));
        mapFragment.getMapAsync(this);
        for(int i=0; i<ip_array.length; i++){
            ips.add(ip_array[i].split(",")[0]);
        }

        /////////////      Readiing ip Text File      ///////////////


        /*try {
            InputStream in = this.getResources().openRawResource(R.raw.ip);

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
            String line;
            int i = 0;
            if(in != null){
                while((line=bufferedReader.readLine()) != null || i <= 20){
                    Toast.makeText(MainActivity.this,"File has been read" + line,Toast.LENGTH_SHORT).show();
                    Log.d("print everything", line);
                    i++;
                }
                in.close();
            }


        } catch (FileNotFoundException e) {
            Toast.makeText(MainActivity.this,e.toString(),Toast.LENGTH_SHORT).show();

            e.printStackTrace();
        } catch (IOException e) {
            Toast.makeText(MainActivity.this,e.toString(),Toast.LENGTH_SHORT).show();

            e.printStackTrace();
        }*/



        for(int i=0; i<ips.size(); i++){
            String url_ip = ips.get(i);
            JsonObjectRequest obj = new JsonObjectRequest(Request.Method.GET, Url+url_ip,ip,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                LatLng latLng = new LatLng(response.getDouble("latitude"),response.getDouble("longitude"));
                                locations.add(latLng);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(MainActivity.this,error.toString(),Toast.LENGTH_SHORT).show();

                }
            });
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(obj);
        }


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

    }
}
