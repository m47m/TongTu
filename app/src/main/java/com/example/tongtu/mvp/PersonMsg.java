package com.example.tongtu.mvp;

import android.net.Uri;

import org.json.JSONObject;

import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class PersonMsg {
    public static final String URL_api = "http://api.tongtu.xyz";
    private static MediaType JSON = MediaType.get("application/json; charset=utf-8");
    private static OkHttpClient client = new OkHttpClient();
    private JSONObject jsonObject = new JSONObject();

    public void get_UserMsg(String token, Callback callback){
        String URL_get_UserMsg = URL_api+"/user";
        Request request = new Request.Builder()
                .url(URL_get_UserMsg)
                .get()
                .addHeader("token",token)
                .build();
        client.newCall(request).enqueue(callback);
    }

    public void get_Folder(String token,Callback callback){
        String URL_get_Folder = URL_api+"/oss/file/folders";
        Request request = new Request.Builder()
                .url(URL_get_Folder)
                .get()
                .addHeader("token",token)
                .build();
        client.newCall(request).enqueue(callback);
    }

    public void get_file(String token,Callback callback){
        String URL_get_File = URL_api +"/oss/file/list?size=7&page=0";
        Request request = new Request.Builder()
                .url(URL_get_File)
                .get()
                .addHeader("token",token)
                .build();
        client.newCall(request).enqueue(callback);
    }

    public void get_FolderFile(String folder,String token,Callback callback){
        String URL_get_folder_file = URL_api+"/oss/file/list/folder?size=10&page=0&folder="+folder;
        Request request = new Request.Builder()
                .url(URL_get_folder_file)
                .get()
                .addHeader("token",token)
                .build();
        client.newCall(request).enqueue(callback);
    }

    public void LoadMoreFile(int page ,String token,Callback callback){
        String URL_get_File = URL_api +"/oss/file/list?size=7&page="+String.valueOf(page);
        Request request = new Request.Builder()
                .url(URL_get_File)
                .get()
                .addHeader("token",token)
                .build();
        client.newCall(request).enqueue(callback);
    }


    public void get_BinFile(String token,Callback callback){

        String URL_get_bin_file = URL_api+"/oss/file/recycle?size=15&page=0";
        Request request = new Request.Builder()
                .url(URL_get_bin_file)
                .get()
                .addHeader("token",token)
                .build();
        client.newCall(request).enqueue(callback);
    }

    public void LoadMoreBinFile(int page,String token,Callback callback){
        String URLBinFile = URL_api+"/oss/file/recycle?size=15&page="+String.valueOf(page);
        Request request = new Request.Builder()
                .url(URLBinFile)
                .get()
                .addHeader("token",token)
                .build();
        client.newCall(request).enqueue(callback);
    }

    public void DeleteFile(String fileID,String token, Callback callback){
        String URL_DELETE_FILE = URL_api+"";
        Request request = new Request.Builder()
                .url(URL_DELETE_FILE)
                .get()
                .addHeader("token",token)
                .build();
        client.newCall(request).enqueue(callback);
    }

    public void RestoreFile(String fileID,String token, Callback callback){
        String URL_RESTORE_FILE = URL_api+"";
        Request request = new Request.Builder()
                .url(URL_RESTORE_FILE)
                .get()
                .addHeader("token",token)
                .build();
        client.newCall(request).enqueue(callback);
    }

    public void CPDeleteFile(String fileID,String token, Callback callback){
        String URL_CPDELETE_FILE = URL_api+"";
        Request request = new Request.Builder()
                .url(URL_CPDELETE_FILE)
                .get()
                .addHeader("token",token)
                .build();
        client.newCall(request).enqueue(callback);
    }

}
