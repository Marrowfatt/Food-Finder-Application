package com.example.jack.foodfinder;

// Acts as custom data type; Recipe to store recipes in a more logical format
public class Recipe {
    private String title;
    private String f2f_url;
    private String publisher;
    private String source_url;
    private String image_url;
    private String publisher_url;

    public Recipe(){}

    public Recipe(String title, String f2f_url, String publisher, String source_url, String image_url, String publisher_url){
        this.title = title;
        this.f2f_url = f2f_url;
        this.publisher = publisher;
        this.source_url = source_url;
        this.image_url = image_url;
        this.publisher_url = publisher_url;
    }

    //Standard get/set functions for all strings

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getF2f_url() {
        return f2f_url;
    }

    public void setF2f_url(String f2f_url) {
        this.f2f_url = f2f_url;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String Publisher) {
        this.publisher = Publisher;
    }

    public String getSource_url() {
        return source_url;
    }

    public void setSource_url(String source_url) {
        this.source_url = source_url;
    }

    public String getImage_url(){
        return image_url;
    }

    public void setImage_url(String image_url){
        this.image_url = image_url;
    }

    public String getPublisher_url(){
        return publisher_url;
    }

    public void setPublisher_url(String publisher_url){
        this.publisher_url = publisher_url;
    }

}