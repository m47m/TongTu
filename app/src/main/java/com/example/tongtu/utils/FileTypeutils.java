package com.example.tongtu.utils;

import android.util.Log;

import com.example.tongtu.R;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class FileTypeutils {
    private Map<String,Integer> file_type_maps = new HashMap<>();
    private static final int TYPE_AUDIO = 1;
    private static final int TYPE_IMAGE = 2;
    private static final int TYPE_OTHER = 3;
    private static final int TYPE_TEXT = 4;
    private static final int TYPE_VEDIO = 5;

    public FileTypeutils(){
        file_type_maps.put("video/x-msvideo", TYPE_VEDIO);
        file_type_maps.put("text/css",TYPE_TEXT);
        file_type_maps.put("application/msword",TYPE_TEXT);
        file_type_maps.put("application/vnd.openxmlformats-officedocument.wordprocessingml.document",TYPE_TEXT);
        file_type_maps.put("image/gif",TYPE_IMAGE);
        file_type_maps.put("text/html",TYPE_TEXT);
        file_type_maps.put( "image/jpeg",TYPE_IMAGE);
        file_type_maps.put( "audio/x-mpeg",TYPE_AUDIO);
        file_type_maps.put("application/pdf",TYPE_TEXT);
        file_type_maps.put( "image/png",TYPE_IMAGE);
        file_type_maps.put( "application/octet-stream",TYPE_OTHER);
        file_type_maps.put( "application/vnd.ms-powerpoint",TYPE_TEXT);
        file_type_maps.put( "application/vnd.openxmlformats-officedocument.presentationml.presentation",TYPE_TEXT);
        file_type_maps.put( "image/x-photoshop",TYPE_OTHER);
        file_type_maps.put( "application/rar",TYPE_OTHER);
        file_type_maps.put( "text/plain",TYPE_TEXT);
        file_type_maps.put( "application/vnd.ms-excel",TYPE_TEXT);
        file_type_maps.put( "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",TYPE_TEXT);
        file_type_maps.put( "application/x-zip-compressed",TYPE_OTHER);
    }

    public int totype(String file_type){
        int default_class = TYPE_OTHER;
        file_type_maps.get("file_type");
        if(file_type_maps.containsKey(file_type)){
            //存在
            return  file_type_maps.get(file_type);
        }else{
            return default_class;
        }
    }

    // split截取后缀名
    public String lastName(String name) {
        String filename = name;
        // split用的是正则，所以需要用 //. 来做分隔符
        String[] split = filename.split("\\.");
        //注意判断截取后的数组长度，数组最后一个元素是后缀名
        if (split.length > 1) {
            return split[split.length - 1];
        } else {
            return "";
        }
    }
}
