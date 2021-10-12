package com.example.tongtu;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.util.TimeFormatException;
import android.view.Gravity;
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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.tongtu.base.BaseActivity;
import com.example.tongtu.mvp.LoginPresenter;
import com.example.tongtu.mvp.LoginView;

import org.json.JSONException;
import org.litepal.annotation.Column;

public class LoginActivity extends BaseActivity<LoginView, LoginPresenter> implements LoginView {
    ////    登录注册按钮
//    private Button login;
//    private Button register;
////    账号密码输入
    private EditText username;
    private EditText password;

    private static final int LOGIN_RESULT_0 = 0;
    private static final int LOGIN_RESULT_1 = 1;
    private static final int LOGIN_RESULT_2 = 2;
    private static final int LOGIN_RESULT_3 = 3;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    @SuppressLint("HandlerLeak")
    Handler handler;

    {
        handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                Toast toast = Toast.makeText(LoginActivity.this, "", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP, 0, 200);
                switch (msg.what) {
                    case LOGIN_RESULT_0:
                        toast.setText("登录成功");
                        toast.show();
                        break;
                    case LOGIN_RESULT_1:
                        toast.setText("登录失败，请检验用户名或密码");
                        toast.show();
                        break;
                    case LOGIN_RESULT_2:
                        toast.setText("邮箱未验证");
                        toast.show();
                        break;
                    case LOGIN_RESULT_3:
                        toast.setText("未连接到数据库");
                        toast.show();
                        break;
                }
            }
        };
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setupUI(((ViewGroup) findViewById(android.R.id.content)).getChildAt(0));

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);

        pref = PreferenceManager.getDefaultSharedPreferences(this);



    }

    public void on_login(View v) throws JSONException {
        getPresenter().login(username.getText().toString(), password.getText().toString());
    }

    public void on_register(View v) {
        Intent intent_register = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent_register);
        finish();
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
    public void on_login_result(String result ,String token,String securityToken,String accessKeySecret ,String accessKeyId,String expiration) {
        Message msg = new Message();
        msg.what = Integer.parseInt(result);
        handler.sendMessage(msg);

//        this.token = token;
//        this.securityToken = securityToken;
//        this.accessKeySecret = accessKeySecret;
//        this.accessKeyId = accessKeyId;
//        this.expiration = expiration;
        if(result.equals("0")){
            editor = pref.edit();
            editor.putString("username",username.getText().toString());
            editor.putString("token",token);
            editor.putString("securityToken",securityToken);
            editor.putString("accessKeySecret",accessKeySecret);
            editor.putString("accessKeyId",accessKeyId);
            editor.putString("expiration",expiration);
            editor.apply();

            Intent intemt_main = new Intent(LoginActivity.this,MainActivity.class);
            startActivity(intemt_main);
            finish();
        }



    }


    @Override
    public void on_check_username(String result) {

    }

    @Override
    public void on_check_email(String result) {

    }

    @Override
    public void on_register_result(String result) {

    }

}
