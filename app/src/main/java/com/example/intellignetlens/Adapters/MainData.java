package com.example.intellignetlens.Adapters;

import java.util.ArrayList;

public class MainData {
    private String name;                                                                            //Name of the category / subcategory
    private String links;                                                                           // Links for the categories / subcategories

    private static ArrayList<MainData> category_links = new ArrayList<MainData>();
    private static ArrayList<MainData>sub_category_links = new ArrayList<MainData>();

    public MainData(){}

    public MainData(String name,String links){
        this.name=name;
        this.links=links;
    }

    public void save_category_links(ArrayList<MainData>category_links){
        this.category_links = category_links;
    }

    public void save_sub_category_links(ArrayList<MainData>sub_category_links){
        this.sub_category_links = sub_category_links;
    }

    public ArrayList<MainData> get_category_links(){return category_links;}

    public ArrayList<MainData> get_sub_category_links(){return sub_category_links;}

    public String getLinks(){return links;}

    public String getName(){return name;}
}
