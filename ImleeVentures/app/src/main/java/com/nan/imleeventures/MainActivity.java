package com.nan.imleeventures;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nan.imleeventures.Adapters.Skill_adapter;

import java.util.ArrayList;



public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private DatabaseReference mDatabase,mDatabase1;

    private String uid;
    private Context context = this ;
    private FirebaseAuth auth;
    private Button submit_skill;
    private ProgressBar progressBar;
    private EditText skill;
    private ListView skill_list;
    private ArrayList<String> skill_array;
    private RecyclerView skill_recycle;
    private Skill_adapter skill_adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("Skills and Interests");
            getSupportActionBar().setHomeButtonEnabled(true);

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }



        auth = FirebaseAuth.getInstance();
       /* progressDialog = new ProgressDialog(MainActivity.this);*/
        if(auth != null) {
            if (auth.getCurrentUser() != null) {
            /*startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();*/
                uid = auth.getCurrentUser().getUid();

            }
        }

        skill_array = new ArrayList<String>();
        skill_list = (ListView) findViewById(R.id.skill_list);
        skill_recycle = (RecyclerView)findViewById(R.id.skill_recycler);

       // final  ProgressDialog progressDialog = ProgressDialog.show(MainActivity.this,"Fetching Data","Please wait...",true);
        mDatabase1 = FirebaseDatabase.getInstance().getReference("users/"+uid+"/items/");
        mDatabase1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //progressDialog.dismiss();

                int i=0;
                skill_array.clear();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    String skill1 = postSnapshot.getValue().toString();

                  //  Log.i("valuelistner",skill1.substring(7,skill1.length()-1));
                    skill_array.add(i,skill1.substring(7,skill1.length()-1));
                    //Log.i("valuelistner",skill_array.get(i));
                    i++;
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1,skill_array);
                skill_list.setAdapter(adapter);
                skill_adapter = new Skill_adapter(MainActivity.this,skill_array);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                Log.i("afterrecycle",skill_array.toString());

                skill_recycle.setLayoutManager(mLayoutManager);
                skill_recycle.setItemAnimator(new DefaultItemAnimator());
                skill_recycle.setAdapter(skill_adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        skill_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this,Questions.class);
                intent.putExtra("title",skill_array.get(position));
                startActivity(intent);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Snackbar.make(v, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                final Dialog  dialog = new Dialog(context);
                dialog.setContentView(R.layout.skill_dialog);
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(dialog.getWindow().getAttributes());
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

                dialog.show();
                dialog.getWindow().setAttributes(lp);
                submit_skill = (Button) dialog.findViewById(R.id.skill_button);
                skill = (EditText) dialog.findViewById(R.id.skill_edittext);
                submit_skill.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String skill_text = skill.getText().toString();
                        if(skill_text.equals(null))
                        {
                            Toast.makeText(getApplicationContext(),
                                    "Enter Skill",
                                    Toast.LENGTH_SHORT).show();
                        }
                        else
                        {

                            Toast.makeText(getApplicationContext(),
                                    "adding data "+uid,
                                    Toast.LENGTH_SHORT).show();

                            //mDatabase.child("users").child(uid).child("items").push().child("title").setValue((skill_name_add).toString());
                            mDatabase = FirebaseDatabase.getInstance().getReference();
                            //mDatabase.child("users").child(uid).child("items").push().child(skill_name_add);//.child("title").setValue((skill_name_add).toString());
                            mDatabase.child("users").child(uid).child("items").push().child("title").setValue((skill_text).toString());
                            dialog.dismiss();



                        }
                    }

                });

            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.prof) {
            // Handle the camera action
        } else if (id == R.id.skill) {

        } else if (id == R.id.setting) {

        } else if (id == R.id.sign_out) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
