package com.example.intellignetlens.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.intellignetlens.R;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {
    TextView product_id;
    TextView productName;
    TextView productDescription;
    ImageView productImage;
    Button redirect;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        redirect = findViewById(R.id.redirect);
        product_id = findViewById(R.id.desp_product_id);
        productDescription = findViewById(R.id.desp_product_description);
        productName = findViewById(R.id.desp_product_name);
        productImage = findViewById(R.id.desp_product_image);

        String name = getIntent().getStringExtra("ProductName");
        String id = getIntent().getStringExtra("ProductID");
        String desp = getIntent().getStringExtra("ProductDesp");
        String imgurl = getIntent().getStringExtra("ProductImage");
        final String url = getIntent().getStringExtra("ProductURL");
        Picasso.get().load(imgurl).into(productImage);
        product_id.setText(id);

        if(name==null || name.isEmpty()) {
            productName.setGravity(Gravity.CENTER);
            productName.setText("N/A");                                                             //Some Products don't have Product Name.
        }
        else
            productName.setText(name);

        if(desp==null || desp.isEmpty()) {
            productDescription.setGravity(Gravity.CENTER_HORIZONTAL);                               //Some Products don't have product Description
            productDescription.setText("No Description Available");
        }

        else
            productDescription.setText(desp);

        redirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);                                     //Opening website of that particular product.
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });
    }
}
