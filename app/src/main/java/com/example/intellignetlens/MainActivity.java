package com.example.intellignetlens;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    Button button;
    TextView textView;

    TextInputEditText search_bar;

    DatabaseReference mdatabase;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mdatabase = FirebaseDatabase.getInstance().getReference().child("Items");

        search_bar = findViewById(R.id.search_bar);
        toolbar = findViewById(R.id.toolbar);
        textView = (TextView)findViewById(R.id.text_view);
        button = (Button)findViewById(R.id.button);

        setSupportActionBar(toolbar);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                new Category().execute();                                                                 //Main Category Page
//                new Sub_Category().execute();                                                            //Sub Category Page
//                new Website().execute();                                                                //Item Page
//                new Inside_website().execute();                                                        //Detailed Page
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menuz,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.resync:
                startActivity(new Intent(MainActivity.this,resyncActivity.class));
                break;

            case R.id.logout:
                Toast.makeText(this, "Clicked 2", Toast.LENGTH_SHORT).show();
                break;
        }

        return true;
    }
}
