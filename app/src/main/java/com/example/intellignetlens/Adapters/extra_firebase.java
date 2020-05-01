package com.example.intellignetlens.Adapters;

public class extra_firebase {

    public String url;
    public String product_name;                                                                     //Product Name
    public String product_id;                                                                       //Product ID
    public String description;                                                                      //Description
    public String images;                                                                           //Image URL

    public String desp1,desp2,desp3;                                                                //comparision points

    public extra_firebase(){}

    public void addition(String product_name, String images,String desp1, String desp2, String desp3){
        this.images = images;
        this.product_name = product_name;
        this.desp1 = desp1;
        this.desp2 = desp2;
        this.desp3 = desp3;
    }
    public extra_firebase(String product_id,String product_name,String images,String description,String url,String desp1, String desp2, String desp3){
        this.product_id = product_id;
        this.product_name = product_name;
        this.images = images;
        this.description = description;
        this.url = url;
        this.desp1=desp1;
        this.desp2 = desp2;
        this.desp3 = desp3;
    }

    public void setDesp1(String desp1){this.desp1 = desp1;}
    public void setDesp2(String desp2){this.desp2 = desp2;}
    public void setDesp3(String desp3){this.desp3 = desp3;}

    public String getDesp1(){return desp1;}
    public String getDesp2(){return desp2;}
    public String getDesp3(){return desp3;}

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
}
