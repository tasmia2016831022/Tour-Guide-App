package com.example.dell.tourguide2;

public class PostItem {
    private String area;
    private String imageUrl;
    private String name;
    private String type;
    private String description;
    private String usename; ///(poster)

    public PostItem() {

    }

    public PostItem(String area, String imageUrl, String name, String type, String description, String usename) {
        this.area = area;
        this.imageUrl = imageUrl;
        this.name = name;
        this.type = type;
        this.description = description;
        this.usename = usename;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUsename() {
        return usename;
    }

    public void setUsename(String usename) {
        this.usename = usename;
    }
}
