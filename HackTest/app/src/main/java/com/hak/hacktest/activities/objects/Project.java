package com.hak.hacktest.activities.objects;

import java.io.Serializable;



public class Project implements Serializable {
    private String id;
    private String user_id;
    private String user_name;
    private String path;
    private String category;
    private String description;
    private String price;


    public Project(String id, String user_id , String user_name, String path, String category, String description, String price) {
        this.id = id;
        this.user_name = user_name;
        this.path = path;
        this.category = category;
        this.description = description;
        this.price = price;
        this.user_id = user_id;
    }

    public Project() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
