package com.example.tongtu;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.Ringtone;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
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

import androidx.annotation.NonNull;
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


    private static final int USERNAME_ERROR_CHECK = 1;
    private static final int EMAIL_ERROR_CHECK = 2;
    private static final int REGISTER_RESULT_0 = 3;
    private static final int REGISTER_RESULT_1 = 4;
    private static final int REGISTER_RESULT_2 = 5;

    @SuppressLint("HandlerLeak") Handler handler;
    {
        handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                Toast toast = Toast.makeText(RegisterActivity.this, "", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP, 0, 200);
                switch (msg.what) {
                    case USERNAME_ERROR_CHECK:
                        toast.setText("??????????????????????????????");
                        toast.show();
                        break;
                    case EMAIL_ERROR_CHECK:
                        toast.setText("???????????????????????????");
                        toast.show();
                        break;
                    case REGISTER_RESULT_0:
                        toast.setText("??????????????????????????????????????????!");
                        toast.show();
                        break;
                    case REGISTER_RESULT_1:
                        toast.setText("?????????????????????????????????");
                        toast.show();
                        break;
                    case REGISTER_RESULT_2:
                        toast.setText("????????????");
                        toast.show();
                        break;
                }
            }
        };
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        setupUI(((ViewGroup) findViewById(android.R.id.content)).getChildAt(0));
//      ????????????
        username = (EditText) findViewById(R.id.username);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        checkpassword = (EditText) findViewById(R.id.checkpassword);

//        edittext
        username_check = false;
        email_check = false;

//      ??????????????????????????????
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
                    email.setError("??????????????????");
                }
            }
        });
//      ?????????????????????????????????
        username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!username.getText().toString().equals("")) {
                        getPresenter().check_username(username.getText().toString());
                    }
            }
        });
//      ???????????????????????????
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
                    checkpassword.setError("?????????????????????");
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
//  ????????????
    public void on_register(View v) throws JSONException {
        if(password.getText().toString().equals(checkpassword.getText().toString())){
            if(this.username_check && this.email_check ){
                getPresenter().register(email.getText().toString(),username.getText().toString(),password.getText().toString());
            }else {
                Toast toast = Toast.makeText(RegisterActivity.this, "", Toast.LENGTH_SHORT);
                toast.setText("????????????????????????");
                toast.setGravity(Gravity.TOP,0, 10);
                toast.show();
            }
        }else{
            Toast toast = Toast.makeText(RegisterActivity.this, "?????????????????????", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP,0, 0);
            toast.show();

        }

    }

    public void back_login(View v){
       Intent Intent_login = new Intent();

       setResult(RESULT_OK,Intent_login);
       finish();

    }

//  ????????????
    public void on_login(View v) {
        Intent intent_login = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent_login);
        finish();
    }

    @Override
    public void on_login_result(String result,String token,String securityToken,String accessKeySecret ,String accessKeyId,String expiration) {

    }
//  ???????????????
    @Override
    public void on_check_username(String result) {
        if(result.equals("1")){
//            ??????????????????
            this.username_check = false;
            Message msg = new Message();
            msg.what = USERNAME_ERROR_CHECK;
            handler.sendMessage(msg);
            username.setError("??????????????????????????????");

        }else if(result.equals("0")){
//            ???????????????
            this.username_check = true;
        }

    }
//  ????????????
    @Override
    public void on_check_email(String result) {
        if(result.equals("1")){
//            ???????????????
            this.email_check = false;
            Message msg = new Message();
            msg.what=EMAIL_ERROR_CHECK;
            handler.sendMessage(msg);
            email.setError("???????????????????????????");

        }else if (result.equals("0")){
//            ????????????
            this.email_check = true;
        }
    }
//  ????????????
    @Override
    public void on_register_result(String result) {

        Message msg = new Message();
        if(result.equals("0")){
            msg.what = REGISTER_RESULT_0;
        }else if(result.equals("1")){
            msg.what = REGISTER_RESULT_1;
        }else if(result.equals("2")){
            msg.what = REGISTER_RESULT_2;
        }
        handler.sendMessage(msg);

    }

    @Override
    public void on_add_result(String code, String data) {

    }

}
