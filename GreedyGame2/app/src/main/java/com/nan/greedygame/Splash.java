package com.nan.greedygame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Thread timer = new Thread() {  /// thread to stop the process
            public void run() {
                try {
                    InputStream in = getApplicationContext().getResources().openRawResource(R.raw.ip2loc);
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
                    String line = "";
                    ArrayList<Integer> time = new ArrayList<>();
                    String clock;
                    try {
                        while ((line = bufferedReader.readLine()) != null){
                            LatLng latLng = new LatLng(Double.parseDouble(line.split(",")[1].split(":")[0]),Double.parseDouble(line.split(",")[1].split(":")[1]));
                            ((Global)getApplicationContext()).locations.add(latLng);
                            clock = line.split(",")[0].split(" ")[1].replace(":","");
                            ((Global)getApplicationContext()).time.add(Integer.parseInt(clock)-10000);
                            String[] temp = line.split(",");
                            if(temp.length == 3){
                                ((Global)getApplicationContext()).region.add(temp[2]);
                            }else {
                                ((Global)getApplicationContext()).region.add(" ");
                            }

                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    //sleep(5000);
                    // Toast.makeText(getApplicationContext(),((Global)getApplicationContext()).locations.size()+"=length",Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    Intent strt = new Intent("com.nan.greedygame.STARTINGPOINT");
                    //strt.putExtra("Data", score_data);
                    startActivity(strt);
                }
            }
        };
        timer.start();
    }
    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
