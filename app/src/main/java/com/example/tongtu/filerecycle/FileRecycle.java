package com.example.tongtu.filerecycle;

public class FileRecycle {

    private String file_name;
    private String file_overdue;
    private int imageId;
    private String file_size;

    public FileRecycle(String name ,String overdue,String size,int imageId){
        this.file_name = name;
        this.imageId = imageId;
       this.file_overdue = overdue;
       this.file_size = size;
    }

    public FileRecycle() {
    }

    public int getImageId(){
        return this.imageId;
    }
    public String getName(){
        return this.file_name;
    }
    public String getFileoverdue(){return this.file_overdue;}
    public String getFilesize(){return  this.file_size;}
}
