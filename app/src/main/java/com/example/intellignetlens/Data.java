package com.example.intellignetlens;

import java.util.ArrayList;

public class Data{
    static String links;
    static String product_id;
    static String product_name;

    static String description;
    static String tag;

    //public Data(){}

    public Data(String links, String product_id, String product_name){
        this.links = links;
        this.product_id = product_id;
        this.product_name = product_name;
    }

    public String show_link(){
        return links;
    }
}
