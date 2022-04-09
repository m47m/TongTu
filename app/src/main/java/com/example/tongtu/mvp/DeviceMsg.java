package com.example.tongtu.mvp;


import android.util.Log;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DeviceMsg {
    public static final String URL_api = "http://api.tongtu.xyz";
    private static MediaType JSON = MediaType.get("application/json; charset=utf-8");
    private static OkHttpClient client = new OkHttpClient();
    private JSONObject jsonObject = new JSONObject();

    public void get_device(String token_, Callback callback) throws IOException {
        String URL_get_device = URL_api+"/user/device/list";
        Request request = new Request.Builder()
                .url(URL_get_device)
                .get()
                .addHeader("token",token_)
                .build();

      client.newCall(request).enqueue(callback);

    }

    public void modify_alias(String modify_alias,int selected_id ,String token, Callback callback){
        String URL_modify_alias = URL_api +"/user/device/"+String.valueOf(selected_id)+"/"+modify_alias;
        Log.d("modify_alias",URL_modify_alias);

        Request request = new Request.Builder()
                .url(URL_modify_alias)
                .get()
                .addHeader("token",token)
                .build();

        client.newCall(request).enqueue(callback);
    }

}
