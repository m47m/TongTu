package com.example.tongtu.folderlist;

public class FolderList {

    private String file_id;
    private String file_name;
    private String file_device_number;
    private int imageId;
    private String file_time;
    private String  file_folder;

    public FolderList(String file_id,String name ,String number,String time,int imageId,String file_folder){
        this.file_id = file_id;
        this.file_name = name;
        this.imageId = imageId;
        this.file_device_number = number;
        this.file_time = time;
        this.file_folder = file_folder;
    }

    public FolderList() {
    }

    public String getFile_id(){return this.file_id;}
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
    public String getFile_folder(){return  this.file_folder;}
}
