package com.example.tongtu.filepost;

public class FilePost {
    private String file_name;
    private int imageId;

    public FilePost(String name ,int imageId){
        this.file_name = name;
        this.imageId = imageId;
    }

    public int getImageId(){
        return this.imageId;
    }
    public String getName(){
        return this.file_name;
    }
}
