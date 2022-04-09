package com.example.tongtu.mvp;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.tongtu.LoginActivity;
import com.example.tongtu.base.BasePresenter;
import com.example.tongtu.mydevice.Mydevice;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class DeviceMsgPre extends BasePresenter<DeviceMsgView> {

    private DeviceMsg deviceMsg;

    public DeviceMsgPre() {
        this.deviceMsg = new DeviceMsg();
    }

    public void getData(String token) throws IOException {
        this.deviceMsg.get_device(token, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    List<Mydevice> device_list = new ArrayList<>();


                    for(int i=0;i<  jsonArray.length();i++){
                        device_list.add(new Mydevice(jsonArray.getJSONObject(i).getInt("id")
                                , jsonArray.getJSONObject(i).getString("name")
                                ,jsonArray.getJSONObject(i).getString("alias")
                                ,jsonArray.getJSONObject(i).getString("lastLoginAt")
                                ,jsonArray.getJSONObject(i).getString("type")));
                    }
//                    device_list.add(new Mydevice(jsonArray.getJSONObject(0).getInt("id")
//                                , jsonArray.getJSONObject(0).getString("name")
//                                ,jsonArray.getJSONObject(0).getString("alias")
//                                ,jsonArray.getJSONObject(0).getString("lastLoginAt")
//                                ,jsonArray.getJSONObject(0).getString("type")));
                    if(getView()!=null){
                        getView().get_device_result(device_list);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void modifyAlias(int selected_id,String modify_alias,String token){
        this.deviceMsg.modify_alias(modify_alias,selected_id, token, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                if(getView() != null){
                    getView().modify_alias_result(1);
                }
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if(getView() != null){
                    getView().modify_alias_result(0);
                }
            }
        });
    }

}
