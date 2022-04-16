package com.example.tongtu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import com.example.tongtu.base.BaseActivity;
import com.example.tongtu.filelist.FileList;
import com.example.tongtu.filepost.FileAdapter;
import com.example.tongtu.filepost.FilePost;
import com.example.tongtu.filerecycle.FileRecycle;
import com.example.tongtu.folderlist.FolderList;
import com.example.tongtu.folderlist.FolderListAdapter;
import com.example.tongtu.mvp.PersonMsg;
import com.example.tongtu.mvp.PersonMsgPre;
import com.example.tongtu.mvp.PersonMsgView;
import com.example.tongtu.mydevice.Mydevice;
import com.example.tongtu.mydevice.MydeviceAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

//文件夹内部文件列表
public class FolderActivity extends BaseActivity<PersonMsgView, PersonMsgPre>implements PersonMsgView {
    private TextView textView ;
    private SharedPreferences pref;
    private String token;
    public FolderListAdapter adapter;
    private List<FolderList> folder_list = new ArrayList<>();


    @SuppressLint("HandlerLeak")
    Handler handler;

    {
        handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case 1:
                        folder_list.clear();
                        List<FolderList>temp =(List<FolderList>)msg.obj;
                        for(int i = 0;i<temp.size();i++){
                            folder_list.add(temp.get(i));
                        }
                        break;
                }
                adapter.notifyDataSetChanged();
            }
        };
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_folder);
        textView = (TextView)findViewById(R.id.folder_name);
        Intent intent = getIntent();
        String folder_name = intent.getStringExtra("folder_name");
        textView.setText(folder_name);

        pref = this.getSharedPreferences("login_message",MODE_PRIVATE);
        token = pref.getString("token","");

        initdata();

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        //recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        //FileAdapter adapter = new FileAdapter(file_post_list);
        adapter = new FolderListAdapter(folder_list,this);
        recyclerView.setAdapter(adapter);

        if(getPresenter() != null){
            getPresenter().get_FolderFile(token,folder_name);
        }

    }

    @Override
    public PersonMsgPre create_presenter() {
        return new PersonMsgPre();
    }

    @Override
    public PersonMsgView create_view() {
        return this;
    }

    public void initdata(){
        folder_list.add(new FolderList("test","test","test",R.drawable.document_type_new));
    }

    @Override
    public void get_usermsg_result(JSONObject jsonObject) {

    }

    @Override
    public void get_folder_result(JSONArray jsonArray) {

    }

    @Override
    public void get_file_result(List<FileList> fileLists) {

    }

    @Override
    public void get_folder_file_result(List<FolderList> folderLists) {
        Message message = new Message();
        message.what = 1;
        message.obj = folderLists;
        handler.sendMessage(message);
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


    public void back_home(View view) {
        finish();
    }
}