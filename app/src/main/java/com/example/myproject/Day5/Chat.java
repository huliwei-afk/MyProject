package com.example.myproject.Day5;

public class Chat {

    private String name;
    private int imageId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public Chat(String name, int imageId) {
        this.name = name;
        this.imageId = imageId;
    }
}
