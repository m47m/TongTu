package com.example.tongtu.mydevice;

public class Mydevice {
    private String device_name;
    private String device_alias;
    private String device_time;
    private int device_id;
    private String device_type;


    public Mydevice(int id, String name ,String alias,String time,String type){
      this.device_id = id;
      this.device_name = name;
      this.device_alias = alias;
      this.device_time = time;
      this.device_type = type;
    }

    public Mydevice() {
    }

    public String getDevice_name() {
        return device_name;
    }
    public String getDevice_alias(){return  device_alias;}
    public String getDevice_time(){return  device_time;}
    public String getDevice_type(){return device_type;}
    public int getDevice_id(){return  device_id;}


}
