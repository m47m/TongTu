package com.example.tongtu.mvp;

import com.example.tongtu.base.BaseView;
import com.example.tongtu.mydevice.Mydevice;

import org.json.JSONArray;

import java.util.List;

public interface DeviceMsgView extends BaseView {
    void get_device_result(List<Mydevice> device_list);
    void modify_alias_result(int result);
}
