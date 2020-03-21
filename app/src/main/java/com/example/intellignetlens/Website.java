package com.example.intellignetlens;

import android.os.AsyncTask;
import android.widget.TextView;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.StringReader;
import java.nio.file.attribute.UserPrincipalLookupService;
import java.util.ArrayList;
import java.util.List;


public class Website extends AsyncTask<Void,Void,Void> {

    int statuscode;
    ArrayList<Data>ob = new ArrayList<Data>();

    //List<List<String>> fullstr = new ArrayList<List<String>>();
    //ArrayList<String> onlystr = new ArrayList<String>();
    //ArrayList<String> names = new ArrayList<String>();

    //StringBuilder stringBuilder = new StringBuilder();
    @Override
    protected Void doInBackground(Void... voids) {
        try{
            Connection connection =Jsoup.connect("https://www.boschtools.com/us/en/boschtools-ocs/new-products-33327-c/")
                    .userAgent("Mozilla/5.0 (Windows NT 6.3; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.101 Safari/537.36")
                    .timeout(10000);

            Document document = connection.get();
            statuscode = connection.response().statusCode();

            Elements body = document.select("body.leaf-category");
            Elements div_page = body.select("div#page");
            Elements sections1 = div_page.select("section#section-products");
            Elements form = sections1.select("form#compareForm");
            Elements div_child = form.select("div");

            int count=1;
            String str = "div#product-holder-"+count;

            for(int i=2;i<div_child.size();i++)
            {
                Elements div = div_child.select(str);
                if(div.size()==0)
                    break;

                    Elements link = div.select("a");
                    Element text_name = link.select("div").get(2);
                    Elements Name = text_name.select("div.product-inner");

                    String product_id = Name.select("div.hlt-e").text();
                    String product_name = Name.select("div.hlt-f").text();
                    String text = link.attr("href");

                    text = "https:"+text;

                    ob.add(new Data(text,product_id,product_name));
//                    onlystr.add(text);
//                    names.add(product_id);
//                    names.add(product_name);

                    str = str.substring(0,19);
                    count++;
                    str+=count;
            }

            //obj.addition(onlystr,names);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }
}
