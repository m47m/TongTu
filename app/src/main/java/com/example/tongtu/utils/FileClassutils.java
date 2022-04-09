package com.example.tongtu.utils;

import com.example.tongtu.R;

import java.util.HashMap;
import java.util.Map;

public class FileClassutils {
    private Map<String,Integer> file_class_maps = new HashMap<>();

    public FileClassutils(){
        file_class_maps.put("video/x-msvideo",R.drawable.document_type_avi);
        file_class_maps.put("text/css",R.drawable.document_type_css);
        file_class_maps.put("application/msword",R.drawable.document_type_doc);
        file_class_maps.put("application/vnd.openxmlformats-officedocument.wordprocessingml.document",R.drawable.document_type_doc);
        file_class_maps.put("image/gif",R.drawable.document_type_gif);
        file_class_maps.put("text/html",R.drawable.document_type_html);
        file_class_maps.put( "image/jpeg",R.drawable.document_type_jpeg);
        file_class_maps.put( "audio/x-mpeg",R.drawable.document_type_mp3);
        file_class_maps.put("application/pdf",R.drawable.document_type_pdf);
        file_class_maps.put( "image/png",R.drawable.document_type_png);
        file_class_maps.put( "application/octet-stream",R.drawable.document_type_php);
        file_class_maps.put( "application/vnd.ms-powerpoint",R.drawable.document_type_ppt);
        file_class_maps.put( "application/vnd.openxmlformats-officedocument.presentationml.presentation",R.drawable.document_type_ppt);
        file_class_maps.put( "image/x-photoshop",R.drawable.document_type_psd);
        file_class_maps.put( "application/rar",R.drawable.document_type_rar);
        file_class_maps.put( "text/plain",R.drawable.document_type_txt);
        file_class_maps.put( "application/vnd.ms-excel",R.drawable.document_type_xls);
        file_class_maps.put( "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",R.drawable.document_type_xls);
        file_class_maps.put( "application/x-zip-compressed",R.drawable.document_type_zip);
    }
    public int toclass(String file_type){
        int default_class = R.drawable.document_type_new;
        file_class_maps.get("file_type");
        if(file_class_maps.containsKey(file_type)){
            //存在
            return  file_class_maps.get(file_type);
        }else{
            return default_class;
        }
    }




}
