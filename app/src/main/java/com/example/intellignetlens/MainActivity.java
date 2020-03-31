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

    DatabaseReference mdatabase;

    ArrayList<Data>ARRAY_LIST = new ArrayList<Data>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mdatabase = FirebaseDatabase.getInstance().getReference().child("Items");

        textView = (TextView)findViewById(R.id.text_view);
        button = (Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Category().execute();                                                                 //Main Category Page
                new Sub_Category().execute();                                                            //Sub Category Page
                new Website().execute();                                                                //Item Page
                new Inside_website().execute();                                                        //Detailed Page

//                ARRAY_LIST.add(new Data("Hello","hi","whatsapp","null"));
//                ARRAY_LIST.add(new Data("Hello1","hi1","whatsapp1","null1"));
//                ARRAY_LIST.add(new Data("Hello2","hi2","whatsapp2","null2"));
//                ARRAY_LIST.add(new Data("Hello3","hi3","whatsapp3","null3"));
////
////                mdatabase.child("CHILD").setValue(ARRAY_LIST);
//                for(int i=0;i<ARRAY_LIST.size();i++){
//                    String a,b,c,dd;
//
//                    a=ARRAY_LIST.get(i).show_link();
//                    b=ARRAY_LIST.get(i).show_Product_id();
//                    c=ARRAY_LIST.get(i).show_Product_name();
//                    dd=ARRAY_LIST.get(i).show_product_description();
//
//                    Data object = new Data();
//
//                    object.setDescription(dd);
//                    object.setProduct_id(b);
//                    object.setProduct_name(c);
//                    object.setLinks(a);
//
//                    mdatabase.child(b).setValue(object);
//                }
            }
        });
    }
}
