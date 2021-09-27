package com.example.tongtu;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.TimeFormatException;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.content.Context;
import android.os.IBinder;
import android.view.inputmethod.InputMethodManager;
import android.view.View.OnTouchListener;
import android.app.Activity;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.tongtu.base.BaseActivity;
import com.example.tongtu.mvp.LoginPresenter;
import com.example.tongtu.mvp.LoginView;

import org.json.JSONException;

public class LoginActivity extends BaseActivity<LoginView,LoginPresenter> implements LoginView {
////    登录注册按钮
//    private Button login;
//    private Button register;
////    账号密码输入
    private EditText username;
    private EditText password;
//
//    private ConstraintLayout layout_login;
//
//    private LoginPresenter login_presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setupUI(((ViewGroup) findViewById(android.R.id.content)).getChildAt(0));

        username = (EditText)findViewById(R.id.username);
        password = (EditText)findViewById(R.id.password);


    }

    public void on_login(View v) throws JSONException {
        getPresenter().login(username.getText().toString(),password.getText().toString());
    }

    public void on_register(View v){
        Intent intent_register = new Intent(LoginActivity.this,RegisterActivity.class);
        startActivity(intent_register);
    }


    @Override
    public LoginPresenter create_presenter() {
        return new LoginPresenter();
    }

    @Override
    public LoginView create_view() {
        return this;
    }

    @Override
    public void onlogin_result(String result) {
        Toast.makeText(LoginActivity.this,"成功link"+result,Toast.LENGTH_SHORT).show();
        Intent intent_login = new Intent(LoginActivity.this,MainActivity.class);
        startActivity(intent_login);
        finish();
    }


    @Override
    public void oncheck_username(String result) {

    }
    @Override
    public void oncheck_email(String result) {

    }
    @Override
    public void onregister_result(String result) {

    }

}
