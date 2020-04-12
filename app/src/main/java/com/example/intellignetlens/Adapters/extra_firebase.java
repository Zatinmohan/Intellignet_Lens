package com.example.intellignetlens.Adapters;

import java.util.ArrayList;
import java.util.List;

public class extra_firebase {

    public String url;
    public String product_name;
    public String product_id;
    public String description;
    public String images;
//    public static List<extra_firebase>list = new ArrayList<>();
//    public static int pos;

    public extra_firebase(){}

    public extra_firebase(String product_id,String product_name,String images,String description){
        this.product_id = product_id;
        this.product_name = product_name;
        this.images = images;
        this.description = description;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

//    public void saveList(List<extra_firebase>list,int pos){
//        this.list=list;
//        this.pos=pos;
//    }
//
//    public List<extra_firebase> getList(){
//        return list;
//    }
//
//    public void clearList(){
//        this.list.clear();
//        this.pos=-1;
//    }
//    public int returnPos(){
//        return pos;
//    }
}
