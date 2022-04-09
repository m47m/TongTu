package com.example.tongtu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tongtu.base.BaseActivity;
import com.example.tongtu.mvp.DeviceMsgPre;
import com.example.tongtu.mvp.DeviceMsgView;
import com.example.tongtu.mydevice.Mydevice;

import java.util.List;

public class ChangeDeviceActivity extends BaseActivity<DeviceMsgView, DeviceMsgPre> implements DeviceMsgView {
    public int selected_id;
    public String modify_alias;
    private EditText edit_device_alias;
    public String token;
    private SharedPreferences pref;

    @SuppressLint("HandlerLeak")
    Handler handler;

    {
        handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                Toast toast = Toast.makeText(ChangeDeviceActivity.this, "", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP, 0, 200);
                switch (msg.what) {
                    case 0:
                        toast.setText("修改成功，修改后别名为 "+modify_alias);
                        toast.show();
                        break;
                    case 1:
                        toast.setText("修改失败，请检查别名");
                        toast.show();
                        break;

                }
            }
        };
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_device);

        pref = this.getSharedPreferences("login_message",MODE_PRIVATE);
        token = pref.getString("token","");
        edit_device_alias = (EditText)findViewById(R.id.edit_device_alias);
        Intent _intent = getIntent();
        if(_intent != null){
            selected_id = _intent.getIntExtra("selected_id",-1);
        }else{
            selected_id = -2;
        }
       // Log.d("change",String.valueOf(selected_id));
    }

    @Override
    public DeviceMsgPre create_presenter() {
        return new DeviceMsgPre();
    }

    @Override
    public DeviceMsgView create_view() {
        return this;
    }


    public void back(View v){
        finish();
    }

    public void modify(View view) {
        this.modify_alias = edit_device_alias.getText().toString();
        getPresenter().modifyAlias(selected_id,modify_alias,token);
    }

    public void del(View view) {
    }

    @Override
    public void get_device_result(List<Mydevice> device_list) {

    }

    @Override
    public void modify_alias_result(int result) {
        Message msg = new Message();
        msg.what = result;
        handler.sendMessage(msg);
    }
}