package com.example.intellignetlens;

import java.util.ArrayList;

public class Data{
     String links;
     String product_id;
     String product_name;


    String description=null;
    static String tag;
    static ArrayList<Data>array_list = new ArrayList<Data>();

    public Data(){
        //ArrayList<Data>array_list = new ArrayList<Data>();
    }


    public Data(String links, String product_id, String product_name,String description){
        this.links = links;
        this.product_id = product_id;
        this.product_name = product_name;
        this.description=description;
    }

    public void setDescription(String description){
        this.description=description;
    }

    public void save_list(ArrayList<Data>arrya_list){
        //this.array_list=new ArrayList<Data>(arrya_list);
        this.array_list=arrya_list;}

    public ArrayList<Data> get_list(){
        return array_list;
    }

    public String show_link(){
        return links;
    }

    public String show_Product_name(){return product_name;}

    public String show_Product_id(){return product_id;}
}
