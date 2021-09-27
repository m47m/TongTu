package com.example.tongtu;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.service.autofill.RegexValidator;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tongtu.base.BaseActivity;
import com.example.tongtu.base.BasePresenter;
import com.example.tongtu.base.BaseView;
import com.example.tongtu.mvp.LoginPresenter;
import com.example.tongtu.mvp.LoginView;

import org.json.JSONException;

import java.util.concurrent.locks.ReentrantLock;

public class RegisterActivity extends BaseActivity<LoginView, LoginPresenter> implements LoginView {
    private EditText email;
    private EditText username;
    private EditText password;
    private EditText checkpassword;

    private boolean username_check;
    private boolean email_check;
    private boolean password_check;

    private String result_register_check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        setupUI(((ViewGroup) findViewById(android.R.id.content)).getChildAt(0));
//      组件注册
        username = (EditText) findViewById(R.id.username);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        checkpassword = (EditText) findViewById(R.id.checkpassword);

//        edittext
        username_check = false;
        email_check = false;
        password_check = false;

//      邮箱输入框的实时监听
        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String email_ = email.getText().toString();
                if(email_ !="" && email_.matches("[A-Za-z\\d]+([-_.][A-Za-z\\d]+)*@([A-Za-z\\d]+[-.])+[A-Za-z\\d]{2,4}")){
                    getPresenter().check_email(email.getText().toString());
                }else{
                    email.setError("邮箱格式有误");
                }
            }
        });
//      用户名输入框的实时监听
        username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (username.getText().toString() != "") {
                        Log.d("1111", username.getText().toString());
                        getPresenter().check_username(username.getText().toString());
                    }
            }
        });
//      验证密码的实时监听
        checkpassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d("1111",checkpassword.getText().toString());
                String checkpassword_ = checkpassword.getText().toString();
                String password_ = password.getText().toString();
                if(checkpassword_.equals(password_)){

                }else {
                    checkpassword.setError("两次密码不一致");
                }

            }
        });


    }

    @Override
    public LoginPresenter create_presenter() {
        return new LoginPresenter();
    }

    @Override
    public LoginView create_view() {
        return this;
    }
//  注册按钮
    public void on_register(View v) throws JSONException {
        Log.d("1111", "on register");
        if(password.getText().toString().equals(checkpassword.getText().toString())){
            if(this.username_check && this.email_check ){
                getPresenter().register(email.getText().toString(),username.getText().toString(),password.getText().toString());
            }else {
                Toast toast = Toast.makeText(RegisterActivity.this, "", Toast.LENGTH_SHORT);
                toast.setText("用户名或邮箱有误");
                toast.setGravity(Gravity.TOP,0, 10);
                toast.show();
            }
        }else{
            Toast toast = Toast.makeText(RegisterActivity.this, "两次密码不一致", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP,0, 0);
            toast.show();

        }

    }
//  登录按钮
    public void on_login(View v) {
        Intent intent_login = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent_login);
    }

    @Override
    public void onlogin_result(String result) {

    }
//  检验用户名
    @Override
    public void oncheck_username(String result) {
        if(result.equals("1")){
//            用户名不可用
            username.setError("用户名已被使用请更改");
            this.username_check = false;
        }else if(result.equals("0")){
//            用户名可用
            this.username_check = true;
        }
    }
//  检验邮箱
    @Override
    public void oncheck_email(String result) {
        if(result.equals("1")){
//            邮箱不可用
            email.setError("邮箱已被使用请更改");
            this.email_check = false;
        }else if (result.equals("0")){
//            邮箱可用
            this.email_check = true;
        }
    }
//  注册结果
    @Override
    public void onregister_result(String result) {
        Looper.prepare();
        Toast toast = Toast.makeText(RegisterActivity.this,result,Toast.LENGTH_SHORT);
        if(result.equals("0")){
            toast.setText("注册成功，请前往邮箱进行验证!");
            toast.setGravity(Gravity.TOP,0, 0);
            toast.show();
            Intent intent_login = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent_login);
        }else if(result.equals("1")){
            toast.setText("邮件未发送成功，请稍后");
            toast.setGravity(Gravity.TOP,0, 0);
            toast.show();
        }else{
            toast.setText("注册失败");
            toast.setGravity(Gravity.TOP,0, 0);
            toast.show();
        }
        Looper.loop();
    }
}
