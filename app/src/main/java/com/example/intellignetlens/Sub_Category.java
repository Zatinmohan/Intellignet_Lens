package com.example.intellignetlens;

import android.os.AsyncTask;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class Sub_Category extends AsyncTask<Void,Void,Void> {

    MainData mainData = new MainData();
    ArrayList<MainData>sub_cat = new ArrayList<MainData>();
    ArrayList<MainData>category = new ArrayList<MainData>();

    int statuscode;

    @Override
    protected Void doInBackground(Void... voids) {
        category = mainData.get_category_links();

        try{
            for(int i=0;i<category.size();i++){

                if(i!=0 && i!=6 && i!=7 && i!=9 && i!=14 && i!=17 && i!=18 && i!=19 && i!=23 && i!=24 && i!=25){                        //These are those pages that don't have sub category page.
                    String url = category.get(i).getLinks();
                    Connection connection = Jsoup.connect(url)
                            .userAgent("Mozilla/5.0 (Windows NT 6.3; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.101 Safari/537.36")
                            .timeout(10000);

                    Document document = connection.get();
                    statuscode = connection.response().statusCode();

                    Elements body = document.select("body.category");
                    Elements div1 = body.select("div#page");
                    Elements section = div1.select("section#section-productcategory");
                    Element div2 = section.select("div").get(0);
                    Elements links = div2.select("a");

                    for(int j=0;j<links.size();j++){
                        Element each_link = links.get(j);
                        String l = each_link.attr("href").toString();
                        l = "https:"+l;
                        String name = each_link.getElementsByAttribute("href").text();

                        sub_cat.add(new MainData(name,l));
                    }
                }

                else{
                    String name = category.get(i).getName();
                    String link = category.get(i).getLinks();

                    sub_cat.add(new MainData(name,link));
                }
            }
            mainData.save_sub_category_links(sub_cat);
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }
}
