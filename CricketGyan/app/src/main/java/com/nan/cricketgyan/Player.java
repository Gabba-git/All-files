package com.nan.cricketgyan;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import de.hdodenhof.circleimageview.CircleImageView;

public class Player extends AppCompatActivity {
    CircleImageView image;
    URL url;
    Bitmap bm;
    byte[] bt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        TextView name = (TextView) findViewById(R.id.player_name);
        TextView country = (TextView) findViewById(R.id.player_country);
        TextView matches = (TextView) findViewById(R.id.player_matches);
        TextView runs = (TextView) findViewById(R.id.player_runs);
        TextView description = (TextView) findViewById(R.id.player_description);
        image = (CircleImageView) findViewById(R.id.player_image);
        Bundle extras = getIntent().getExtras();



        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar_main);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle(getIntent().getStringExtra("name"));
            getSupportActionBar().setHomeButtonEnabled(true);
            //
            // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        name.setText(getIntent().getStringExtra("name").toString());
        country.setText(getIntent().getStringExtra("country").toString());
        matches.setText(getIntent().getStringExtra("match").toString());
        runs.setText(getIntent().getStringExtra("score").toString());
        description.setText(getIntent().getStringExtra("description").toString());
        //Picasso.with(this).load(getIntent().getStringExtra("image").toString()).into(image);

        try {
            url = new URL(getIntent().getStringExtra("image").toString());
            bm = BitmapFactory.decodeStream(url.openConnection().getInputStream());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        image.setImageBitmap(bm);

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, stream);
        bt = stream.toByteArray();
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Player.this,Image.class);
                intent.putExtra("picture", bt);
                ActivityOptionsCompat options = ActivityOptionsCompat.
                        makeSceneTransitionAnimation(Player.this,image, "profile");
                startActivity(intent, options.toBundle());
            }
        });
    }
}
