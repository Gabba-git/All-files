package com.nan.cricketgyan;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.nan.cricketgyan.R;

public class Splash extends AppCompatActivity {

    String score_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitysplash);
        String url = "http://hackerearth.0x10.info/api/gyanmatrix?type=json&query=list_player";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                score_data = response;
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Splash.this, error.toString(), Toast.LENGTH_LONG).show();

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(Splash.this);
        requestQueue.add(stringRequest);


    Thread timer = new Thread() {  /// thread to stop the process
        public void run() {
            try {
                sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                Intent strt = new Intent("com.nan.cricketgyan.STARTINGPOINT");
                strt.putExtra("Data", score_data);
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
