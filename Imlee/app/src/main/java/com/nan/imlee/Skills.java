package com.nan.imlee;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class Skills extends AppCompatActivity {
    LinearLayout containerLayout;
    static int totalEditTexts = 0;
    Toolbar toolbar;
    FloatingActionButton floatingActionButton;
    ImageView add;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skills);
        if(toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("Add Skills");
            getSupportActionBar().setHomeButtonEnabled(true);
            //
            // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        containerLayout = (LinearLayout)findViewById(R.id.mlayout);
        add = (ImageView) findViewById(R.id.add_image);

        floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Skills.this,Navigation.class);
                startActivity(intent);
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                totalEditTexts++;
                if(totalEditTexts < 50){
                    EditText editText = new EditText(Skills.this);
                    containerLayout.addView(editText);
                    editText.setGravity(Gravity.LEFT);
                    //editText.setPadding(2,2,2,2);
                    LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) editText.getLayoutParams();
                    layoutParams.width = LinearLayout.LayoutParams.MATCH_PARENT;
                    layoutParams.setMargins(5,5,5,5);
                    editText.setLayoutParams(layoutParams);
                    //if you want to identify the created editTexts, set a tag, like below
                    editText.setHint("Add Skill" + totalEditTexts);
                }
            }
        });
    }
}
