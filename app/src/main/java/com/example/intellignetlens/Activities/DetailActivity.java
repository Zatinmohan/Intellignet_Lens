package com.example.intellignetlens.Activities;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.intellignetlens.Adapters.extra_firebase;
import com.example.intellignetlens.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {
    TextView product_id;
    TextView productName;
    TextView productDescription;
    ImageView productImage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        product_id = findViewById(R.id.desp_product_id);
        productDescription = findViewById(R.id.desp_product_description);
        productName = findViewById(R.id.desp_product_name);
        productImage = findViewById(R.id.desp_product_image);

        String name = getIntent().getStringExtra("ProductName");
        String id = getIntent().getStringExtra("ProductID");
        String desp = getIntent().getStringExtra("ProductDesp");
        String imgurl = getIntent().getStringExtra("ProductImage");

       product_id.setText(id);
        productName.setText(name);
        productDescription.setText(desp);
        Picasso.get().load(imgurl).into(productImage);
    }
}
