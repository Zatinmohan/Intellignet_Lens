package com.example.intellignetlens.Activities;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.intellignetlens.R;
import com.example.intellignetlens.Adapters.RecyclerViewAdapter;
import com.example.intellignetlens.Adapters.extra_firebase;
import com.eyalbira.loadingdots.LoadingDots;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ResultActivity extends AppCompatActivity {

    DatabaseReference mdatabase;
    String content;
    RecyclerView recyclerView;
    RecyclerViewAdapter recyclerViewAdapter;
    CardView cardView;
    LoadingDots loadingDots;
    TextView total;
    TextView compare;

    CheckBox checkBox;
    List<extra_firebase>itemlist;                                                                   //List the coantain the foudn items
    boolean isFound=false;

    int m=0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productlist);

        compare = findViewById(R.id.compare);

        compare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(compare.getCurrentTextColor()==Color.parseColor("#e52d27"))            //Checking the Color
                    Toast.makeText(ResultActivity.this, "Comparable", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(ResultActivity.this, "Please choose only 2 products", Toast.LENGTH_SHORT).show();

            }
        });

        total = findViewById(R.id.total);

        content = getIntent().getStringExtra("Content");                                      // Get the Search title

        loadingDots = findViewById(R.id.loadingdots);
        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        cardView = (CardView)findViewById(R.id.card_view);

        mdatabase = FirebaseDatabase.getInstance().getReference().child("Items");                   //Database Connectivity

        itemlist= new ArrayList<>();
        Query query = mdatabase.orderByChild("product_name").endAt(content);                        //Try to get that particular product if it ends with that name

        query.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                loadingDots.setVisibility(View.VISIBLE);
                //progressBar.setVisibility(View.VISIBLE);
                for(DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    String z = snapshot.child("product_name").getValue(String.class);               //Gets the name of the product

                    if(z!=null && z.contains(content)){
                        String y = snapshot.child("description").getValue(String.class);            //Get the description
                        String x = snapshot.child("product_id").getValue(String.class);             //Get the Product ID
                        String img = snapshot.child("images").getValue(String.class);               //Get Images url
                        String url = snapshot.child("url").getValue(String.class);                  //Getting Page url.
                        itemlist.add(new extra_firebase(x,z,img,y,url));
                        isFound=true;
                    }
                }

                    recyclerViewAdapter = new RecyclerViewAdapter(itemlist, ResultActivity.this,compare);
                    recyclerView.setAdapter(recyclerViewAdapter);                                   //filling adapter
                    String size = String.valueOf(itemlist.size());
                    total.setText("Total Results : " + size);
                    loadingDots.setVisibility(View.GONE);



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ResultActivity.this, databaseError.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}