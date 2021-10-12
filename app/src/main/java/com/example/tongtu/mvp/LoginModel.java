package com.example.tongtu.mvp;

import android.util.Log;

import com.example.tongtu.RegisterActivity;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginModel {

    public static final String URL_api = "http://api.tongtu.xyz";
    private static MediaType JSON = MediaType.get("application/json; charset=utf-8");
    private static OkHttpClient client = new OkHttpClient();
//  登录
    public void login(String username ,String password,Callback callback) throws JSONException {
        String URL_login = URL_api +"/user/login"+"?username="+username+"&password="+password;
        JSONObject json_data = new JSONObject();
        json_data.put("username",username);
        json_data.put("password",password);
        //进行网络链接，数据传输访问
        RequestBody body = RequestBody.create(json_data.toString(),JSON);
        Request request = new Request.Builder()
                .url(URL_login)
                .post(body)
                .build();
        Log.d("1111",URL_login);
        Call call = client.newCall(request);
        call.enqueue(callback);
    }
//  注册
    public void register(String email,String username,String password,Callback callback) throws JSONException {
        String URL_register = URL_api +"/user/register";
        JSONObject json_data = new JSONObject();
        json_data.put("username",username);
        json_data.put("password",password);
        json_data.put("email",email);
        RequestBody body = RequestBody.create(json_data.toString(),JSON);
        Request request = new Request.Builder()
                .url(URL_register)
                .post(body)
                .build();

        Call call = client.newCall(request);
        call.enqueue(callback);
    }
//  验证邮箱
    public void check_email(String email,Callback callback){
        String URL_check_email = URL_api +"/user/check/email?email="+email;
        Request request = new Request.Builder()
                .url(URL_check_email)
                .get()
                .build();

        Call call = client.newCall(request);

        call.enqueue(callback);
    }
//  验证用户名
    public void check_username(String username,Callback callback) {

        String URL_check_username = URL_api+"/user/check/username?username="+username;

        Request request = new Request.Builder()
                .url(URL_check_username)
                .get()
                .build();

        Call call = client.newCall(request);
        call.enqueue(callback);

    }

}
