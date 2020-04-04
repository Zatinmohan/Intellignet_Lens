package com.example.intellignetlens;

import android.os.AsyncTask;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class Inside_website extends AsyncTask<Void,Void,Void> {

    int statuscode;

    ArrayList<Data>obj = new ArrayList<Data>();
    Data data = new Data();
    DatabaseReference mdatabase;
    @Override
    protected Void doInBackground(Void... voids) {
        try
        {
            obj = data.get_items();                                                                  //fetching the saved list.
            int x = obj.size();                                                                     //Checking the size of the list [Debugging]

            for(int i=0;i<obj.size();i++){

                if(i==485) {                                                                        //Page 485, 486 and 487 have no description. It ends up halting the app process.
                    i += 3;
                }

                String url = obj.get(i).show_link();
                Connection connection = Jsoup.connect(url)
                        .userAgent("Mozilla/5.0 (Windows NT 6.3; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.101 Safari/537.36")
                        .timeout(10000);

                Document document = connection.get();
                statuscode = connection.response().statusCode();

                Elements body = document.select("body.detail");
                Elements div1 = body.select("div#page");

                //------------------------------------------------------
                //                  Title and Links
                String link = obj.get(i).show_link();
                String product_id = obj.get(i).show_Product_id();
                String product_name = obj.get(i).show_Product_name();
                String image = obj.get(i).show_product_images();
                //-------------------------------------------------------

                //-------------------------------------------------------
                //              Description
                Elements section = div1.select("section#section-techdata");
                Elements div_content = section.select("div.row");
                Elements div_deep_content = div_content.select("div.col-sm-12");
                Element inner_dis = div_deep_content.select("div.productspecs-inner").get(1);
                Elements paragraph = inner_dis.select("p");
                String s = paragraph.text();
                //-------------------------------------------------------

                 obj.set(i,new Data(image,link,product_id,product_name,s));                                //adding the description to the main list.
            }

           data.save_items(obj);                                                                    //Saving the new List

            mdatabase = FirebaseDatabase.getInstance().getReference().child("Items");


            for(int i=0;i<obj.size();i++)
            {
                String a,b,c,d,e;
                a=obj.get(i).show_link();
                b=obj.get(i).show_Product_id();
                c=obj.get(i).show_Product_name();
                d=obj.get(i).show_product_description();
                e=obj.get(i).show_product_images();

                extra_firebase object = new extra_firebase();

                object.setDescription(d);
                object.setProduct_id(b);
                object.setProduct_name(c);
                object.setUrl(a);
                object.setImages(e);

                mdatabase.child(b).setValue(object);

            }

        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }
}
