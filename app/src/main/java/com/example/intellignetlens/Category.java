package com.example.intellignetlens;

import android.os.AsyncTask;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class Category extends AsyncTask<Void,Void,Void> {

    String url = "https://www.boschtools.com/us/en/boschtools-ocs/power-tools-22064-c/";
    int statuscode;

    MainData mainData = new MainData();
    ArrayList<MainData>category = new ArrayList<MainData>();

    @Override
    protected Void doInBackground(Void... voids) {
        try{

            Connection connection = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Windows NT 6.3; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.101 Safari/537.36")
                    .timeout(10000);

            Document document = connection.get();
            statuscode = connection.response().statusCode();

            Elements body = document.select("body.category");
            Elements div1 = body.select("div#page");
            Element section1 = div1.select("section").get(7);                                       //7th division tag we need.
            Element div2 = section1.select("div").get(0);
            Elements links = div2.select("a");
           for(int i=0;i<links.size();i++)
            {
                Element every_link = links.get(i);                                                  //fetching the link of every category
                String l = every_link.attr("href").toString();                          //Links for the category
                l = "https:"+l;
                String name = every_link.getElementsByAttribute("href").text();                 //names of category

                category.add(new MainData(name,l));
            }
           mainData.save_category_links(category);

        }catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
