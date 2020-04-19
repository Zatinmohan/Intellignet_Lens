package com.example.intellignetlens.Adapters;

import java.util.ArrayList;

public class Data{
      public String pics;
      public String links;
      public String product_id;
      public String product_name;
      public String description=null;
      String tag;
      public static ArrayList<Data>items = new ArrayList<Data>();

    public Data(){ }


    public Data(String pics,String links, String product_id, String product_name,String description){
        this.pics = pics;
        this.links = links;
        this.product_id = product_id;
        this.product_name = product_name;
        this.description=description;
    }
//
//    public void setLinks(String links) {
//        this.links = links;
//    }
//
//    public void setProduct_id(String product_id) {
//        this.product_id = product_id;
//    }
//
//    public void setProduct_name(String product_name) {
//        this.product_name = product_name;
//    }
//
//    public void setDescription(String description) {
//        this.description = description;
//    }

    public void save_items(ArrayList<Data>arrya_list){ this.items=arrya_list;}

    public ArrayList<Data> get_items(){
        return items;
    }

    public String show_link(){
        return links;
    }

    public String show_Product_name(){return product_name;}

    public String show_Product_id(){return product_id;}

    public String show_product_description(){return description;}

    public String show_product_images(){return pics;}
}
