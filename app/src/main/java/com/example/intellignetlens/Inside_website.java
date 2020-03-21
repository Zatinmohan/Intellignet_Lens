package com.example.intellignetlens;

import android.os.AsyncTask;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class Inside_website extends AsyncTask<Void,Void,Void> {

    int statuscode;
    static ArrayList<Data>obj = new ArrayList<Data>();
    @Override
    protected Void doInBackground(Void... voids) {

        try
        {
            int x = obj.size();
            for(int i=0;i<obj.size();i++){
                String url = obj.get(i).show_link().toString();
                Connection connection = Jsoup.connect(url)
                        .userAgent("Mozilla/5.0 (Windows NT 6.3; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.101 Safari/537.36")
                        .timeout(10000);

                Document document = connection.get();
                statuscode = connection.response().statusCode();

                Elements body = document.select("body.detail");
                Elements div1 = body.select("div#page");

                //------------------------------------------------------
                //                  Title
                //String product_id = NAMES.get(i*2-1);
                //String product_name = NAMES.get(i*2);;
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
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }
}
