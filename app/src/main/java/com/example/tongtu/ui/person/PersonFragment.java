package com.example.tongtu.ui.person;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.StatFs;
import android.text.format.Formatter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tongtu.MainActivity;
import com.example.tongtu.R;
import com.example.tongtu.base.BaseFragment;
import com.example.tongtu.base.FragmentBase;
import com.example.tongtu.databinding.FragmentPBinding;
import com.example.tongtu.filelist.FileList;
import com.example.tongtu.filepost.FileAdapter;
import com.example.tongtu.filepost.FilePost;
import com.example.tongtu.filerecycle.FileRecycle;
import com.example.tongtu.folderlist.FolderList;
import com.example.tongtu.mvp.PersonMsg;
import com.example.tongtu.mvp.PersonMsgPre;
import com.example.tongtu.mvp.PersonMsgView;
import com.example.tongtu.mydevice.Mydevice;
import com.example.tongtu.pselect.Pselect;
import com.example.tongtu.pselect.PselectAdapter;
import com.example.tongtu.ui.person.PersonViewModel;
import com.example.tongtu.utils.DeviceUuidFactory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

//个人中心界面
public class PersonFragment extends FragmentBase<PersonMsgView, PersonMsgPre>implements PersonMsgView {

    private List<Pselect> p_select_list = new ArrayList<>();
    private TextView text_device_name;
    private TextView text_device_storage;
    private TextView text_username;
    //private CardView card_file_fill;

    private SharedPreferences pref;
    private String token;


    @Override
    protected int initLayout() {
        return R.layout.fragment_p;
    }

    @SuppressLint("HandlerLeak")
    Handler handler;

    {
        handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                JSONObject jsonObject = (JSONObject)msg.obj;
                try {
                    text_username.setText(jsonObject.getString("username"));
                    text_device_storage.setText(jsonObject.get("usedStorage")+"kb"+"/"+jsonObject.getString("maxStorage")+"kb");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void initView(View view) {
        text_username = (TextView)view.findViewById(R.id.text_username);
        text_device_name = (TextView)view.findViewById(R.id.text_device_name);
        text_device_name.setText(android.os.Build.BRAND+" "+android.os.Build.MODEL);
        text_device_storage = (TextView)view.findViewById(R.id.text_device_storage);
        text_device_storage.setText("0kb/0kb");

        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.recycler_view);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        //FileAdapter adapter = new FileAdapter(file_post_list);
        PselectAdapter adapter = new PselectAdapter(p_select_list,this.getContext());
        recyclerView.setAdapter(adapter);

        pref = view.getContext().getSharedPreferences("login_message",MODE_PRIVATE);
        token = pref.getString("token","");
        if(getPresenter() != null){
            getPresenter().get_UserMsg(token);

        }else {
            Log.d("error11","null");
        }

    }

    @Override
    protected void initData(Context context) {

        Pselect p_select_setting = new Pselect("设置");
        Pselect p_select_use = new Pselect("我的设备");
        Pselect p_select_update = new Pselect("检查更新");
        Pselect p_select_document = new Pselect("软件说明");
//        Pselect p_select_log_out = new Pselect("退出登录");

        p_select_list.add(p_select_setting);
        p_select_list.add(p_select_use);
        p_select_list.add(p_select_update);
        p_select_list.add(p_select_document);
//        p_select_list.add(p_select_log_out);



    }

    @Override
    public PersonMsgPre create_presenter() {
        return new PersonMsgPre();
    }

    @Override
    public PersonMsgView create_view() {
        return this;
    }


    @Override
    public void get_usermsg_result(JSONObject jsonObject) {
        Message message = new Message();
        message.what = 1;
        message.obj = jsonObject;
        handler.sendMessage(message);
    }

    @Override
    public void get_folder_result(JSONArray jsonArray) {

    }

    @Override
    public void get_file_result(List<FileList> fileLists) {

    }

    @Override
    public void get_folder_file_result(List<FolderList> folderLists) {

    }

    @Override
    public void loadmore_folder_file_reuslt(String code, List<FileList> fileLists) {

    }

    @Override
    public void get_bin_file_result(List<FileRecycle> fileRecycleList) {

    }

    @Override
    public void loadmore_bin_file_result(String code, List<FileRecycle> fileRecycleList) {

    }

}
