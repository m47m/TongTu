package com.example.tongtu.mvp;

import android.util.Log;

import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class FileMsg {
    public static final String URL_api = "http://api.tongtu.xyz";
    private static MediaType JSON = MediaType.get("application/json; charset=utf-8");
    private static OkHttpClient client = new OkHttpClient();

    public void FileTest(String token, String size, String md5, String id, Callback callback){
        String URL_fileTest = URL_api+"/oss/upload?size="+size+"&MD5="+md5+"&id="+id;
        Log.d("testPostActivity",URL_fileTest);
        Request request = new Request.Builder()
                .url(URL_fileTest)
                .get()
                .addHeader("token",token)
                .build();

        client.newCall(request).enqueue(callback);
    }
}
