package com.example.intellignetlens.Adapters;

import java.util.ArrayList;

public class Data{
      public String pics;                                                                           //Image URL
      public String links;                                                                          //Page Links
      public String product_id;                                                                     //Product ID
      public String product_name;                                                                   //Product Name
      public String description=null;                                                               //Product Description
      String desp1,desp2,desp3;                                                                     //Comparison Point
      public static ArrayList<Data>items = new ArrayList<Data>();                                      //List to save those products

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
