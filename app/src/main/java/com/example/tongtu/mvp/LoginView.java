package com.example.tongtu.mvp;

import com.example.tongtu.base.BaseView;

import org.json.JSONException;

public interface LoginView  extends BaseView {
    void on_login_result(String result,String token,String securityToken,String accessKeySecret ,String accessKeyId,String expiration) throws JSONException;
    void on_check_username(String result);
    void on_check_email(String result);
    void on_register_result(String result);
    void on_add_result(String code,String data);
}
