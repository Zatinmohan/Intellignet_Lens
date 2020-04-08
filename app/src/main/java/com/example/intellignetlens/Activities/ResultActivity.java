package com.example.intellignetlens.Activities;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.intellignetlens.R;
import com.example.intellignetlens.Adapters.RecyclerViewAdapter;
import com.example.intellignetlens.Adapters.extra_firebase;
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

    List<extra_firebase>itemlist = new ArrayList<>();                                                   //List the coantain the foudn items

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productlist);

        content = getIntent().getStringExtra("Content");                                        // Get the Search title

        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        cardView = (CardView)findViewById(R.id.card_view);

        mdatabase = FirebaseDatabase.getInstance().getReference().child("Items");                   //Database Connectivity

        Query query = mdatabase.orderByChild("product_name").endAt(content);                        //Try to get that particular product if it ends with that name

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    String z = snapshot.child("product_name").getValue(String.class);               //Gets the name of the product

                    if(z!=null && z.contains(content)){
                        String y = snapshot.child("description").getValue(String.class);            //Get the description
                        String x = snapshot.child("product_id").getValue(String.class);             //Get the Product ID
                        String img = snapshot.child("images").getValue(String.class);               //Get Images url

                        itemlist.add(new extra_firebase(x,z,img));
                    }
                }

                recyclerViewAdapter = new RecyclerViewAdapter(itemlist,ResultActivity.this);
                recyclerView.setAdapter(recyclerViewAdapter);                                           //filling adapter
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
