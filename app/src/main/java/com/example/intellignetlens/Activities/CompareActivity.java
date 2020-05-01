package com.example.intellignetlens.Activities;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.intellignetlens.Adapters.RecyclerViewAdapter;
import com.example.intellignetlens.R;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class CompareActivity extends AppCompatActivity {

    TextView compare1, compare2;
    CircleImageView product_image1, product_image2;
    TextView first_p1, second_p1, third_p1, first_p2, second_p2, third_p2;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compare);

        compare1 = findViewById(R.id.first_product_name);
        compare2 = findViewById(R.id.second_product_name);

        first_p1 = findViewById(R.id.first_first_compare);
        second_p1 = findViewById(R.id.first_second_compare);
        third_p1 = findViewById(R.id.first_third_compare);

        first_p2 = findViewById(R.id.second_first_compare);
        second_p2 = findViewById(R.id.second_second_compare);
        third_p2 = findViewById(R.id.second_third_compare);


        product_image1 = findViewById(R.id.first_product_image);
        product_image2 = findViewById(R.id.second_product_image);

        String product1 = getIntent().getStringExtra("First Name");
        String product2 = getIntent().getStringExtra("Second Name");

        String image1 = getIntent().getStringExtra("First Image");
        String image2 = getIntent().getStringExtra("Second Image");

        String f_p1 = "1. " + getIntent().getStringExtra("F First Desp");
        String s_p1 = "2. " + getIntent().getStringExtra("F Second Desp");
        String t_p1 = "3. " + getIntent().getStringExtra("F Third Desp");

        String f_p2 = "1. " + getIntent().getStringExtra("S First Desp");
        String s_p2 = "2. " + getIntent().getStringExtra("S First Desp");
        String t_p2 = "3. " + getIntent().getStringExtra("S First Desp");

        compare1.setText(product1);
        compare2.setText(product2);

        first_p1.setText(f_p1);
        second_p1.setText(s_p1);
        third_p1.setText(t_p1);

        first_p2.setText(f_p2);
        second_p2.setText(s_p2);
        third_p2.setText(t_p2);

        Picasso.get().load(image1).into(product_image1);
        Picasso.get().load(image2).into(product_image2);
    }
}
