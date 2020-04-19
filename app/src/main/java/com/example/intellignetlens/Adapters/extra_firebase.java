package com.example.intellignetlens.Adapters;

public class extra_firebase {

    public String url;
    public String product_name;
    public String product_id;
    public String description;
    public String images;

    public extra_firebase(){}

    public extra_firebase(String product_id,String product_name,String images,String description,String url){
        this.product_id = product_id;
        this.product_name = product_name;
        this.images = images;
        this.description = description;
        this.url = url;
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
}
