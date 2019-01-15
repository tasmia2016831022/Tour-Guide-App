package com.example.dell.tourguide1;

public class normal_post {
    private String name,area,post,imgUri,type;

    public normal_post(String name, String area, String post, String imgUri) {
        this.name = name;
        this.area = area;
        this.post = post;
        this.type = imgUri;
    }

    public normal_post(String name, String area, String post, String imgUri, String type) {
        this.name = name;
        this.area = area;
        this.post = post;
        this.imgUri = imgUri;
        this.type = type;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public normal_post() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getImgUri() {
        return imgUri;
    }

    public void setImgUri(String imgUri) {
        this.imgUri = imgUri;
    }
}
