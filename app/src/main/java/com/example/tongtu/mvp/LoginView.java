package com.example.tongtu.mvp;

import com.example.tongtu.base.BaseView;

public interface LoginView  extends BaseView {
    void onlogin_result(String result);
    void oncheck_username(String result);
    void oncheck_email(String result);
    void onregister_result(String result);
}
