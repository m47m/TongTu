package com.example.tongtu.filepost;

public class FilePost {
    private String file_name;
    private String file_device_number;
    private int imageId;
    private String file_time;


    public FilePost(String name ,String number,String time,int imageId){
        this.file_name = name;
        this.imageId = imageId;
        this.file_device_number = number;
        this.file_time = time;

    }

    public FilePost() {
    }

    public int getImageId(){
        return this.imageId;
    }
    public String getName(){
        return this.file_name;
    }

    public String getFile_device_number(){return this.file_device_number;}

    public String getFile_time(){
        return this.file_time;
    }


}
