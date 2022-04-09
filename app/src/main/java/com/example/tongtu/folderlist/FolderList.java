package com.example.tongtu.folderlist;

public class FolderList {

    private String file_name;
    private String file_device_number;
    private int imageId;
    private String file_time;

    public FolderList(String name ,String number,String time,int imageId){
        this.file_name = name;
        this.imageId = imageId;
        this.file_device_number = number;
        this.file_time = time;
    }

    public FolderList() {
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
