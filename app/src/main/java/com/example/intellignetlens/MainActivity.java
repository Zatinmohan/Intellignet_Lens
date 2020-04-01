package com.example.intellignetlens;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.ColorSpace;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button button;
    TextView textView;

    TextInputEditText search_bar;

    DatabaseReference mdatabase;

    ArrayList<Data>ARRAY_LIST = new ArrayList<Data>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mdatabase = FirebaseDatabase.getInstance().getReference().child("Items");

        search_bar = findViewById(R.id.search_bar);
        textView = (TextView)findViewById(R.id.text_view);
        button = (Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
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
