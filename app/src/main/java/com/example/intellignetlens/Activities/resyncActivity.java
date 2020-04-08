package com.example.intellignetlens.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.intellignetlens.Jsoup.Category;
import com.example.intellignetlens.Jsoup.Inside_website;
import com.example.intellignetlens.R;
import com.example.intellignetlens.Jsoup.Sub_Category;
import com.example.intellignetlens.Jsoup.Website;

public class resyncActivity extends AppCompatActivity {

    Toolbar toolbar;
    Button sync;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sync);

        toolbar = findViewById(R.id.toolbar2);

        setSupportActionBar(toolbar);

        if(toolbar!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
        sync = findViewById(R.id.sync_button);

        sync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Category().execute();                                                                 //Main Category Page
                new Sub_Category().execute();                                                            //Sub Category Page
                new Website().execute();                                                                //Item Page
                new Inside_website().execute();                                                        //Detailed Page
            }
        });
    }
}
