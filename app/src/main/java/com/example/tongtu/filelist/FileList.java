package com.example.tongtu.filelist;

public class FileList {
    private String file_id;
    private String file_name;
    private int file_class;
    private String file_size_cloud;
    private String file_time;
    private String file_source ;
    private String file_folder;


    public FileList(String file_id,String file_name,int file_class,String file_size_cloud,String file_time,String file_source,String file_folder){
       this.file_id = file_id;
        this.file_name = file_name;
        this.file_class = file_class;
        this.file_time = file_time;
        this.file_size_cloud = file_size_cloud;
        this.file_source = file_source;
        this.file_folder = file_folder;

    }
    public String getFile_id(){return  this.file_id;}
    public  int getFile_class(){
        return this.file_class;
    }
    public  String getFile_name(){
        return this.file_name;
    }
    public  String getFile_size_cloud(){
        return this.file_size_cloud;
    }
    public String getFile_time(){
        return this.file_time;
    }
    public String getFile_source(){
        return  this.file_source;
    }
    public String getFile_folder(){return  this.file_folder;}

}
