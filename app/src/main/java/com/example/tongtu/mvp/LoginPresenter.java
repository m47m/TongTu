package com.example.tongtu.mvp;

import android.content.SharedPreferences;
import android.util.Log;

import com.example.tongtu.base.BasePresenter;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ResourceBundle;

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
                        getView().on_register_result(register_code);
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
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    String check_email_code = jsonObject.getString("code");

                    if (check_email_code.equals("1") ) {
                        if (getView() != null) {
                            getView().on_check_email("1");
                        }
                    } else if (check_email_code.equals("0")) {
                        if (getView() != null) {
                            getView().on_check_email("0");
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

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    String check_username_code = jsonObject.getString("code");
                    Log.d("1111", check_username_code);
                    if (check_username_code.equals("1") ) {

                        if (getView() != null) {
                            getView().on_check_username("1");
                        }
                    } else if (check_username_code.equals("0")) {

                        if (getView() != null) {
                            getView().on_check_username("0");
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
    public void login(String username, String password ,String uuid) throws JSONException {

        this.login_model.login(username, password, uuid,new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {


                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    String login_code = jsonObject.getString("code");
                    Log.d("login_code",login_code);

                    if(getView() != null) {
                        if (login_code.equals("0")|| login_code.equals("4")) {
                            JSONObject json_data = jsonObject.getJSONObject("data");
                            Log.d("login_code",json_data.getString("token"));
                            getView().on_login_result(login_code
                                    , json_data.getString("token")
                                    , "securityToken"
                                    , "accessKeySecret"
                                    , "accessKeyId"
                                    , "expiration");

                        } else {
                            getView().on_login_result(login_code,"","","","","");
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

    }
//  新增设备
    public void add_divice(String token,String name,String uuid) throws JSONException {
        this.login_model.add_divice(token, name, uuid, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    if(getView() != null){
                        Log.d("testLoginActivity add UUID",jsonObject.getString("data"));
                        getView().on_add_result(jsonObject.getString("code"),jsonObject.getString("data"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
