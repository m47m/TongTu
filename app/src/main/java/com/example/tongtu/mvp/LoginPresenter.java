package com.example.tongtu.mvp;

import android.util.Log;

import com.example.tongtu.base.BasePresenter;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class LoginPresenter extends BasePresenter<LoginView> {

    private LoginModel login_model;

    public LoginPresenter() {
        this.login_model = new LoginModel();
    }
//   注册
    public void register(String email, String username, String password) throws JSONException {
        this.login_model.register(email, username, password, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d("1111", "fail register" + email);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                Log.d("1111", "success register" + email);
                try{
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    Log.d("1111",jsonObject.toString());
                    String register_code = jsonObject.getString("code");
                    Log.d("1111",register_code);
                    if(getView() != null) {
                        getView().onregister_result(register_code);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
//  检验邮箱
    public void check_email(String email){
        this.login_model.check_email(email, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d("1111", "faildddM" + email);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                Log.d("1111", "successM" + email);

                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    String check_email_code = jsonObject.getString("code");
                    Log.d("1111", check_email_code);
                    if (check_email_code.equals("1") ) {
                        Log.d("1111", "failP");
                        if (getView() != null) {
                            getView().oncheck_email("1");
                        }
                    } else if (check_email_code.equals("0")) {
                        Log.d("1111", "successP");
                        if (getView() != null) {
                            getView().oncheck_email("0");
                        }
                    } else {

                    }

                } catch (Exception e) {
                    e.printStackTrace();

                }
            }
        });
    }
//  检验用户名
    public void check_username(String username) {
        this.login_model.check_username(username, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d("1111", "faildddM" + username);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                Log.d("1111", "successM" + username);

                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    String check_username_code = jsonObject.getString("code");
                    Log.d("1111", check_username_code);
                    if (check_username_code.equals("1") ) {
                        Log.d("1111", "failP");
                        if (getView() != null) {
                            getView().oncheck_username("1");
                        }
                    } else if (check_username_code.equals("0")) {
                        Log.d("1111", "successP");
                        if (getView() != null) {
                            getView().oncheck_username("0");
                        }
                    } else {

                    }

                } catch (Exception e) {
                    e.printStackTrace();

                }
            }
        });



    }
//  登录
    public void login(String username, String password) throws JSONException {

        this.login_model.login(username, password, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d("1111","fail login");
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                Log.d("1111","success login");
                JSONObject jsonObject = new JSONObject();
                try {
                    String login_code = jsonObject.getString("code");
                    Log.d("1111",login_code);
                    if(getView() != null){
                        if(login_code.equals("0")){
//                            登录成功 存token
                        }else if(login_code.equals("1")){
//                            登录失败
                        }else if(login_code.equals("2")){
//                            邮箱未验证
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

    }

}
