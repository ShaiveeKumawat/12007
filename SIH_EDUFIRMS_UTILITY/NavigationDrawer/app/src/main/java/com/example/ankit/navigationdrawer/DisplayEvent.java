package com.example.ankit.navigationdrawer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import com.bumptech.glide.Glide;

public class DisplayEvent extends AppCompatActivity {
        public Context nctx;

        @SuppressLint("NewApi")
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_display_event);
            Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
            toolbar.setTitle("Details");
            TextView title = (TextView)findViewById(R.id.dtitle);
            TextView description = (TextView)findViewById(R.id.dDescription);
            ImageView image = (ImageView)findViewById(R.id.dimage);


            Bundle bundle = getIntent().getExtras();
            String eId =bundle.getString("eId");
            String eTitle=bundle.getString("eTitle");
            String eDescription=bundle.getString("eDescription");
            String eImage=bundle.getString("eImage");


            title.setText(eTitle);
            description.setText(eDescription);
            Glide.with(getApplicationContext()).load(String.valueOf(eImage)).into(image);

        }
 }
