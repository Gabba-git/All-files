package com.nan.weather_open;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    EditText entercity;
    TextView city,temp,min,max,main;
    ArrayList<String> temp_list = new ArrayList<>();
    ArrayList<String> min_list = new ArrayList<>();
    ArrayList<String> max_list = new ArrayList<>();
    ArrayList<String> even_list = new ArrayList<>();
    ArrayList<String> days = new ArrayList<>();
    ArrayList<String> description_list = new ArrayList<>();
    Listadapter listadapter;
    ListView listView;
    ScrollView scroll;
    RelativeLayout week_layout;
    Button search;
    String current_url = "http://api.openweathermap.org/data/2.5/weather?q=";
    String week_url = "http://api.openweathermap.org/data/2.5/forecast/daily?q=";
    String key = "&APPID=f17284df36c9973bb81155f5447bf1c6";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        city = (TextView)findViewById(R.id.city_text);
        temp = (TextView)findViewById(R.id.temp_data);
        min = (TextView)findViewById(R.id.min_temp);
        max = (TextView)findViewById(R.id.max_temp);
        main = (TextView)findViewById(R.id.main_data);
        entercity = (EditText)findViewById(R.id.city);
        search = (Button)findViewById(R.id.search);
        listView = (ListView)findViewById(R.id.weatherlist);
        week_layout = (RelativeLayout)findViewById(R.id.week_layout);
        scroll = (ScrollView)findViewById(R.id.day_scroll);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String url = week_url + entercity.getText().toString().replace(" ", "")+"&cnt=7" + key;
                JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    JSONArray jsonarray = response.getJSONArray("list");
                                    for(int i=0; i<7; i++){
                                        JSONObject obj0 = jsonarray.getJSONObject(i);
                                        JSONObject obj1 = obj0.getJSONObject("temp");
                                        int current_temp = obj1.getInt("day")-273;
                                        int temp_min = obj1.getInt("night")-273;
                                        int temp_max = obj1.getInt("morn")-273;
                                        int temp_even = obj1.getInt("eve")-273;
                                        temp_list.add(i,current_temp+"°C");
                                        min_list.add(i,temp_min+"°C");
                                        max_list.add(i,temp_max+"°C");
                                        even_list.add(i,temp_even+"°C");
                                        JSONArray arr = obj0.getJSONArray("weather");
                                        for(int j=0; j<arr.length(); j++){
                                            JSONObject obj2 = arr.getJSONObject(j);
                                            description_list.add(i,obj2.getString("description"));
                                        }
                                        days.add(i,"Day "+(i+1));
                                    }
                                    scroll.setVisibility(View.GONE);
                                    week_layout.setVisibility(View.VISIBLE);
                                    listadapter = new Listadapter(MainActivity.this,temp_list,days,even_list,min_list,max_list,description_list);
                                    listView.setAdapter(listadapter);
                                } catch (JSONException e) {
                                    Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_LONG).show();

                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
                RequestQueue que = Volley.newRequestQueue(MainActivity.this);
                que.add(request);
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringRequest stringRequest = new StringRequest(Request.Method.GET, current_url + entercity.getText().toString().replace(" ","") + key,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                scroll.setVisibility(View.VISIBLE);
                                week_layout.setVisibility(View.GONE);
                                try {
                                    JSONObject obj = new JSONObject(response);
                                    JSONObject obj1 = obj.getJSONObject("main");
                                    JSONArray jsonArray = obj.getJSONArray("weather");

                                    for(int i=0; i<jsonArray.length(); i++){
                                        JSONObject obj2 = jsonArray.getJSONObject(i);
                                        main.setText(obj2.getString("description"));
                                    }

                                    int current_temp = obj1.getInt("temp")-273;
                                    int temp_min = obj1.getInt("temp_min")-273;
                                    int temp_max = obj1.getInt("temp_max")-273;
                                    temp.setText(current_temp+"°C");
                                    min.setText(temp_min+"°C");
                                    max.setText(temp_max+"°C");
                                    city.setText(obj.getString("name"));

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
                RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
                requestQueue.add(stringRequest);
            }
        });

    }

    public void week_click() {
        Toast.makeText(MainActivity.this, "imhere", Toast.LENGTH_LONG).show();
        final String url = week_url + entercity.getText().toString().replace(" ", "")+"&cnt=7 " + key;
        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(MainActivity.this, response.toString(), Toast.LENGTH_LONG).show();
                        Log.d(MainActivity.class.getSimpleName(),url+"fuckk");
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue que = Volley.newRequestQueue(MainActivity.this);
        que.add(request);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
