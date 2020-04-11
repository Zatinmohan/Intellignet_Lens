package com.example.intellignetlens.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.intellignetlens.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.ml.common.FirebaseMLException;
import com.google.firebase.ml.common.modeldownload.FirebaseModelDownloadConditions;
import com.google.firebase.ml.common.modeldownload.FirebaseModelManager;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.automl.FirebaseAutoMLRemoteModel;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.label.FirebaseVisionImageLabel;
import com.google.firebase.ml.vision.label.FirebaseVisionImageLabeler;
import com.google.firebase.ml.vision.label.FirebaseVisionOnDeviceAutoMLImageLabelerOptions;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button button;

    boolean found=false;
    TextInputEditText search_bar;
    TextInputLayout textInputLayout;

    String content;
//    ImageView cropImageView;
//    TextView resultview;

    DatabaseReference mdatabase;
    Toolbar toolbar;
    ImageView camera;

    ProgressDialog progressDialog;
    //Uri filepath;

    float maxconf;
    String Namez;

    FirebaseAutoMLRemoteModel remoteModel;                                                          // Get the Remote Image
    FirebaseVisionImage image;                                                                      //Send the Input Image
    FirebaseModelDownloadConditions conditions;                                                     //Set the condition to download models
    FirebaseVisionOnDeviceAutoMLImageLabelerOptions.Builder optionBuilder;                          // Which option is use to run the labeler local or remotely
    FirebaseVisionImageLabeler labeler;                                                             //For running the image labeler

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        maxconf = 0;                                                                                //Setting the Max Confidence 0
        int p;
        mdatabase = FirebaseDatabase.getInstance().getReference().child("Items");

        //resultview = findViewById(R.id.results);

        search_bar = findViewById(R.id.search_bar);
        textInputLayout = findViewById(R.id.editlayout);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Searching....");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);

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

                fromRemoteModel();                                                              //Download the Remote Modal

            }
        });

        setSupportActionBar(toolbar);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                found=false;
                progressDialog.show();
                content = search_bar.getText().toString();
                if(content.equals("Drill Machine") || content.equals("Drill Drivers") || content.equals("Drill Machine ")|| content.equals("Drill Machines") || content.equals("Drill Machines."))                                  //Drill machine is stored as Drill/Drivers
                    content = "Drill/Driver";

                if(content.endsWith(" ") || content.endsWith("s")|| content.endsWith(".")){
                    content = content.substring(0,content.length()-1);
                    int bb=0;
                }


                Query query = mdatabase.orderByChild("product_name").endAt(content);                                    //Try to get that particular product if it ends with that name
//                String b = content;
                query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                            String z = snapshot.child("product_name").getValue(String.class);                           //Gets the name of the product

                            if(z!=null && z.contains(content)){
                                Intent intent = new Intent(MainActivity.this, ResultActivity.class);     //Check the given keyword is present or not
                                intent.putExtra("Content",content);
                                startActivity(intent);
                                progressDialog.cancel();
                                found = true;
                                break;
                            }
                        }
                        if(!found) {
                            progressDialog.cancel();
                            Toast.makeText(MainActivity.this, "Product Not Found", Toast.LENGTH_SHORT).show();
                        }
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

                Uri resultUri = result.getUri();                                                            //Getting Image File Path
                //cropImageView.setImageURI(resultUri);                                                     //For Testing Purpose
                setLabelerFromRemoteLabel(resultUri);                                                       //Getting Labels of the Dataset from Remote Data-set.

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                Toast.makeText(this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void fromRemoteModel(){
        remoteModel = new FirebaseAutoMLRemoteModel.Builder("Products_202048165812").build();           //Machine Learning Data Set Model Name.
        conditions = new FirebaseModelDownloadConditions.Builder().requireWifi().build();                   //Requires Wifi do Download Dataset.

        FirebaseModelManager.getInstance().download(remoteModel,conditions)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        //Toast.makeText(MainActivity.this, "Download", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void setLabelerFromRemoteLabel(final Uri uri){                                          //Setting Image
        FirebaseModelManager.getInstance().isModelDownloaded(remoteModel)
                .addOnCompleteListener(new OnCompleteListener<Boolean>() {
                    @Override
                    public void onComplete(@NonNull Task<Boolean> task) {
                        if(task.isComplete())
                        {
                            optionBuilder = new FirebaseVisionOnDeviceAutoMLImageLabelerOptions.Builder(remoteModel);
                            FirebaseVisionOnDeviceAutoMLImageLabelerOptions options = optionBuilder
                                    .setConfidenceThreshold(0.0f)
                                    .build();

                            try{
                                labeler = FirebaseVision.getInstance().getOnDeviceAutoMLImageLabeler(options);
                                image = FirebaseVisionImage.fromFilePath(MainActivity.this,uri);
                                processImageLabeler(labeler,image);
                            }catch (FirebaseMLException | IOException e){
                                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(MainActivity.this, "Not Downlaoded!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void processImageLabeler(FirebaseVisionImageLabeler labeler, FirebaseVisionImage image){                                        //Processing Image
        labeler.processImage(image).addOnCompleteListener(new OnCompleteListener<List<FirebaseVisionImageLabel>>() {
            @Override
            public void onComplete(@NonNull Task<List<FirebaseVisionImageLabel>> task) {
                //Toast.makeText(MainActivity.this, "Done!", Toast.LENGTH_SHORT).show();

                for(FirebaseVisionImageLabel label : task.getResult()){
                    String eachlabel = label.getText().toUpperCase();
                    float confidence = label.getConfidence();

                    if(confidence>=maxconf){                                                                                                //Getting the Maximum confidence Value and Storing the ID of that product.
                        maxconf = confidence;
                        Namez = eachlabel;
                    }
                    //resultview.append(eachlabel + " - " + ("" + confidence*100).subSequence(0,4) + "%" + "\n\n");                         //Show confidence percentage level with label of the product
                }

                SearchAgain();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this, "Something Went Wrong!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void SearchAgain(){
        Namez = Namez.replace("_","-");                                                     //ID's of product contains "-" and MLkit doesn't allow "-".

        progressDialog.show();
        found=false;
        Query query = mdatabase.orderByChild("product_id").equalTo(Namez);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    String z = snapshot.child("product_id").getValue(String.class);                             //Gets the ID of the product

                    if(z!=null && z.contains(Namez)){
                        String n = snapshot.child("product_name").getValue(String.class);                       //Getting the name of the product.
                        Intent intent = new Intent(MainActivity.this, ResultActivity.class);
                        intent.putExtra("Content",n);                                                    //Sending ID to the Result Activity
                        startActivity(intent);
                        progressDialog.cancel();
                        found = true;
                        break;
                    }

                    if(!found) {
                        progressDialog.cancel();
                        Toast.makeText(MainActivity.this, "Not Found!", Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

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
                startActivity(new Intent(MainActivity.this, resyncActivity.class));
                break;

            case R.id.logout:
                Toast.makeText(this, "Clicked 2", Toast.LENGTH_SHORT).show();
                break;
        }

        return true;
    }
}