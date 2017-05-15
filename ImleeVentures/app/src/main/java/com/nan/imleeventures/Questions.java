package com.nan.imleeventures;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.nan.imleeventures.Adapters.Question_adapter;

import java.util.ArrayList;

public class Questions extends AppCompatActivity {
    Toolbar toolbar;
    ArrayList<String> question_list,rupee_list,time_list;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);
        String title = getIntent().getStringExtra("title");
        question_list = new ArrayList<>();
        rupee_list = new ArrayList<>();
        time_list = new ArrayList<>();
        toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle(title);
            getSupportActionBar().setHomeButtonEnabled(true);

           getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        question_list.add("this is my first question?");
        question_list.add("this is second");
        question_list.add("here is the third and last");
        rupee_list.add("500");
        rupee_list.add("400");
        rupee_list.add("200");
        time_list.add("2hrs");
        time_list.add("1hrs");
        time_list.add("4hrs");

        recyclerView = (RecyclerView)findViewById(R.id.question_recycler);
        Question_adapter adapter = new Question_adapter(Questions.this,question_list,rupee_list,time_list);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(manager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

    }
}
