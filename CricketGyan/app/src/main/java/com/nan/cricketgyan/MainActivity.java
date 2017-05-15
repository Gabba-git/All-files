package com.nan.cricketgyan;

import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.flipboard.bottomsheet.BottomSheetLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    ArrayList <Details> arrayList = new ArrayList<>();
    Adapter adapter;
    RecyclerView players;
    RelativeLayout score_asc,score_dsc,matches,name_layout;
    RadioButton name_bt,scoreasc_bt,scoredsc_bt,matches_bt;
    BottomSheetLayout bottomSheetLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String data = getIntent().getStringExtra("Data");
        //Log.d("i am here in main", data);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar_main);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle(R.string.app_name);
            getSupportActionBar().setHomeButtonEnabled(true);
            //
            // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        try {
            JSONObject jsonObject = new JSONObject(data);
            JSONArray jsonArray = jsonObject.getJSONArray("records");
            for(int i=0; i<jsonArray.length(); i++){
                JSONObject object = jsonArray.getJSONObject(i);
                Details details = new Details(object.getString("name"),object.getString("country"),object.getString("total_score"),
                        object.getString("matches_played"),object.getString("description"),object.getString("image"));

                arrayList.add(details);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        Collections.sort(arrayList,Details.NameComparator);

        players = (RecyclerView) findViewById(R.id.recycler_view);
        adapter = new Adapter(arrayList,this);
        players.setLayoutManager(new LinearLayoutManager(this));
        players.setItemAnimator(new DefaultItemAnimator());
        players.setAdapter(adapter);


        View itemView = getLayoutInflater().inflate(R.layout.my_sheet_layout,null);
        score_asc = (RelativeLayout) itemView.findViewById(R.id.score_asc);
        score_dsc = (RelativeLayout) itemView.findViewById(R.id.score_dsc);
        name_layout = (RelativeLayout) itemView.findViewById(R.id.name_asc);
        matches = (RelativeLayout) itemView.findViewById(R.id.match_asc);
        name_bt = (RadioButton) itemView.findViewById(R.id.name_asc_bt);
        scoreasc_bt = (RadioButton) itemView.findViewById(R.id.score_asc_bt);
        scoredsc_bt = (RadioButton) itemView.findViewById(R.id.score_dsc_bt);
        matches_bt = (RadioButton) itemView.findViewById(R.id.match_asc_bt);
        RelativeLayout sort_layout = (RelativeLayout) findViewById(R.id.sort_layout);
        name_bt.setChecked(true);
        bottomSheetLayout = (BottomSheetLayout) findViewById(R.id.bottom_sheet);

        sort_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetLayout.showWithSheetView(getLayoutInflater().inflate(R.layout.my_sheet_layout,bottomSheetLayout,false));
            }
        });
        bottomSheetLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                score_asc.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MainActivity.this, "i am clicked", Toast.LENGTH_LONG).show();
                        Collections.sort(arrayList,Details.Runs_asc);
                        players.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                        players.setItemAnimator(new DefaultItemAnimator());
                        players.setAdapter(adapter);
                        scoreasc_bt.setChecked(true);
                        scoredsc_bt.setChecked(false);
                        matches_bt.setChecked(false);
                        name_bt.setChecked(false);
                        bottomSheetLayout.dismissSheet();
                    }
                });
                matches.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MainActivity.this, "i am clicked", Toast.LENGTH_LONG).show();

                        Collections.sort(arrayList,Details.MatchesComparator);
                        players.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                        players.setItemAnimator(new DefaultItemAnimator());
                        players.setAdapter(adapter);
                        scoreasc_bt.setChecked(false);
                        scoredsc_bt.setChecked(false);
                        matches_bt.setChecked(true);
                        name_bt.setChecked(false);
                        bottomSheetLayout.dismissSheet();
                    }
                });
                score_dsc.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Collections.sort(arrayList,Details.Runs_dsc);
                        players.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                        players.setItemAnimator(new DefaultItemAnimator());
                        players.setAdapter(adapter);
                        Toast.makeText(MainActivity.this, "i am clicked", Toast.LENGTH_LONG).show();

                        scoreasc_bt.setChecked(false);
                        scoredsc_bt.setChecked(true);
                        matches_bt.setChecked(false);
                        name_bt.setChecked(false);
                        bottomSheetLayout.dismissSheet();
                    }
                });
                name_layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Collections.sort(arrayList,Details.NameComparator);
                        players.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                        players.setItemAnimator(new DefaultItemAnimator());
                        players.setAdapter(adapter);
                        scoreasc_bt.setChecked(false);
                        scoredsc_bt.setChecked(false);
                        Toast.makeText(MainActivity.this, "i am clicked", Toast.LENGTH_LONG).show();

                        matches_bt.setChecked(false);
                        name_bt.setChecked(true);
                        bottomSheetLayout.dismissSheet();
                    }
                });
            }
        });



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.name_asc:
                Collections.sort(arrayList,Details.NameComparator);
                players.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                players.setItemAnimator(new DefaultItemAnimator());
                players.setAdapter(adapter);
                scoreasc_bt.setChecked(false);
                scoredsc_bt.setChecked(false);
                Toast.makeText(MainActivity.this, "i am clicked", Toast.LENGTH_LONG).show();

                matches_bt.setChecked(false);
                name_bt.setChecked(true);
                bottomSheetLayout.dismissSheet();

        }
    }
}
