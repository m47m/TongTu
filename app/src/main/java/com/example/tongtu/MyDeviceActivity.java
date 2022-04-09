package com.example.tongtu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.tongtu.base.BaseActivity;
import com.example.tongtu.mvp.DeviceMsgPre;
import com.example.tongtu.mvp.DeviceMsgView;
import com.example.tongtu.mydevice.Mydevice;
import com.example.tongtu.mydevice.MydeviceAdapter;

import org.json.JSONArray;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MyDeviceActivity extends BaseActivity<DeviceMsgView, DeviceMsgPre> implements DeviceMsgView {

    private List<Mydevice> my_device_list = new ArrayList<>();
    private SharedPreferences pref;
    private String token;
    public  MydeviceAdapter adapter;

    @SuppressLint("HandlerLeak")
    Handler handler;

    {
        handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                my_device_list.clear();
                List<Mydevice> device_list_ = (List<Mydevice>)msg.obj;
                for(int i = 0;i<device_list_.size();i++){
                    my_device_list.add(device_list_.get(i));
                }

                adapter.notifyDataSetChanged();
            }
        };
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_device);

        pref = this.getSharedPreferences("login_message",MODE_PRIVATE);
        token = pref.getString("token","");

        initdata();

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        //recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        //FileAdapter adapter = new FileAdapter(file_post_list);

        adapter = new MydeviceAdapter(my_device_list,this);
        recyclerView.setAdapter(adapter);

        try {
            getPresenter().getData(token);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //重新获取数据的逻辑，此处根据自己的要求回去
        try {
            getPresenter().getData(token);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //显示信息的界面
    }


    @Override
    public DeviceMsgPre create_presenter() {
        return new DeviceMsgPre();
    }

    @Override
    public DeviceMsgView create_view() {
        return this;
    }

    //返回上一级
    public void back_home(View v){
        finish();
    }

    //初始化数据
    public void initdata()  {
        my_device_list.add(new Mydevice(1,"name","alias","time","type"));
    }

    @Override
    public void get_device_result(List<Mydevice> device_list) {
        Message message = new Message();
        message.what = 1;
        message.obj = device_list;
        handler.sendMessage(message);
    }

    @Override
    public void modify_alias_result(int result) {

    }
}

//new Callback() {
//
//
//@Override
//public void onFailure(@NotNull Call call, @NotNull IOException e) {
//        Log.d("getDevice","get failure");
//        try {
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("result",1);
//
//        } catch (JSONException jsonException) {
//        jsonException.printStackTrace();
//        }
//        }
//@Override
//public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
//        Log.d("getDevice","get response");
//        try{
//        JSONObject jsonObject = new JSONObject(response.body().string());
//        jsonObject.put("result",0);
//        Log.d("getDevice",jsonObject.toString());
//        JSONArray jsonArray = jsonObject.getJSONArray("data");
//        JSONObject jsonObject1 = jsonArray.getJSONObject(0);
//        Log.d("getDevice",jsonObject1.getString("name"));
//
//
//
//        } catch (JSONException e) {
//        e.printStackTrace();
//        }
//        }
//        }