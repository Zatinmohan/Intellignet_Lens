package com.example.intellignetlens;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

public class MainActivity extends AppCompatActivity {

    Button button;
    TextView textView;

    boolean found=false;
    TextInputEditText search_bar;
    TextInputLayout textInputLayout;

    String content;
    //ImageView cropImageView;

    DatabaseReference mdatabase;
    Toolbar toolbar;
    ImageView camera;

    //Uri filepath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mdatabase = FirebaseDatabase.getInstance().getReference().child("Items");

        search_bar = findViewById(R.id.search_bar);
        textInputLayout = findViewById(R.id.editlayout);

        search_bar.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus)
                    search_bar.setHint("Eg: Drill Machine");

                else
                    search_bar.setHint("Search");
            }
        });

        toolbar = findViewById(R.id.toolbar);
        button = (Button)findViewById(R.id.button);

        //cropImageView = findViewById(R.id.profile_pic);

        camera = findViewById(R.id.camera);

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .start(MainActivity.this);

            }
        });

        setSupportActionBar(toolbar);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                content = search_bar.getText().toString();

                if(content.equals("Drill Machine") || content.equals("Drill Drivers"))                      //Drill machine is stored as Drill/Drivers
                    content = "Drill/Driver";

                Query query = mdatabase.orderByChild("product_name").endAt(content);                        //Try to get that particular product if it ends with that name
//                String b = content;
                        query.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                                    String z = snapshot.child("product_name").getValue(String.class);       //Gets the name of the product

                                    if(z!=null && z.contains(content)){
                                        Intent intent = new Intent(MainActivity.this,ResultActivity.class);     //Check the given keyword is present or not
                                        intent.putExtra("Content",content);
                                        startActivity(intent);
                                        found = true;
                                        break;
                                    }
                                }

                                if(!found)
                                    Toast.makeText(MainActivity.this, "Product Not Found", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                Toast.makeText(MainActivity.this, databaseError.toString(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });


    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {

                Uri resultUri = result.getUri();
                //cropImageView.setImageURI(resultUri);

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
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
