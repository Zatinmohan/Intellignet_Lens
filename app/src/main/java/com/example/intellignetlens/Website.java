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
    Data data = new Data();
    MainData mainData = new MainData();

    ArrayList<MainData>sub_cat = new ArrayList<MainData>();
    ArrayList<Data>ob = new ArrayList<Data>();

    @Override
    protected Void doInBackground(Void... voids) {
        try{
            sub_cat = mainData.get_sub_category_links();

            int x = sub_cat.size();
            for(int i=0;i<sub_cat.size();i++)
            {
                    String url = sub_cat.get(i).getLinks();
                    Connection connection =Jsoup.connect(url)
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
                String str = "div#product-holder-"+count;                                                       //used a counter wise listing.

                for(int j=2;j<div_child.size();j++)                                                             //first and second link is not important
                {
                    Elements div = div_child.select(str);
                    if(div.size()==0)                                                                           //if no division tag is found, then cut the loop.
                        break;

                    Elements link = div.select("a");
                    Element text_name = link.select("div").get(2);
                    Elements Name = text_name.select("div.product-inner");

                    String product_id = Name.select("div.hlt-e").text();
                    String product_name = Name.select("div.hlt-f").text();
                    String text = link.attr("href");

                    //------------------------------------------------------------------------------
                    //                          Image

                    Element pic_division = link.select("div").get(1);
                    Elements pic_inner = pic_division.select("div.product-inner");
                    Elements img_tag = pic_inner.select("img");
                    String image = img_tag.attr("src").toString();
                    image = "https:"+image;

                    //------------------------------------------------------------------------------

                    text = "https:"+text;                                                                       //link of the detailed product page.
                    //data.add_Data(text,product_id,product_name);
                    ob.add(new Data(image,text,product_id,product_name,null));                       //description is null for now.

                    str = str.substring(0,19);
                    count++;
                    str+=count;
                }
            }

            data.save_items(ob);                                                                     //save the list.
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
