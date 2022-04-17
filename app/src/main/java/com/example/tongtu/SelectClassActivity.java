package com.example.tongtu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;

import com.example.tongtu.base.BaseActivity;
import com.example.tongtu.filelist.FileList;
import com.example.tongtu.filepost.FileAdapter;
import com.example.tongtu.filepost.FilePost;
import com.example.tongtu.filerecycle.FileRecycle;
import com.example.tongtu.folderlist.FolderList;
import com.example.tongtu.mvp.PersonMsgPre;
import com.example.tongtu.mvp.PersonMsgView;
import com.example.tongtu.selectfolderlist.selectfolderAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SelectClassActivity extends BaseActivity<PersonMsgView, PersonMsgPre>implements PersonMsgView {


    private Intent intent;
    private SharedPreferences pref;
    private String token;

    private List<String> mData = new ArrayList<>();
    private selectfolderAdapter selectfolderAdapter;

    private String mFolder = "only";

    //刷新列表
    @SuppressLint("HandlerLeak")
    Handler handler;

    {
        handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                mData.clear();
                JSONArray jsonArray = (JSONArray)msg.obj;
                try {
                    for(int i = 0;i<jsonArray.length();i++){
                       mData.add(jsonArray.getString(i));
                    }

                    mFolder = mData.get(0);
                }catch (JSONException e){
                    e.printStackTrace();
                }
                selectfolderAdapter.notifyDataSetChanged();
            }
        };
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_class);

        intent = getIntent();
        init_file();

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        // recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        //recyclerView.setLayoutManager(new GridLayoutManager(this,3));
//        adapter = new FileAdapter(file_post_list,SelectClassActivity.this);
        selectfolderAdapter = new selectfolderAdapter(mData,this);

        recyclerView.setAdapter(selectfolderAdapter);
        recyclerView.scrollToPosition(selectfolderAdapter.getItemCount()-1);

        pref = this.getSharedPreferences("login_message",MODE_PRIVATE);
        token = pref.getString("token","");

        getPresenter().get_Folder(token);



        selectfolderAdapter.setDefSelect(0);
        selectfolderAdapter.setOnItemListener(new selectfolderAdapter.OnItemListener() {
            @Override
            public void onClick(View v, int pos) {
                Log.d("testSelectClass pos" ,String.valueOf(pos));
                mFolder = mData.get(pos);
                Log.d("testSelectClass folder select" ,mFolder);
                selectfolderAdapter.setDefSelect(pos);
            }
        });



    }

    @Override
    public PersonMsgPre create_presenter() {
        return new PersonMsgPre();
    }

    @Override
    public PersonMsgView create_view() {
        return this;
    }


    public void back(View view) {
        intent.putExtra("file_class",mFolder);
        this.setResult(RESULT_OK,intent);
        finish();
    }

    public void confirm(View view) {
        intent.putExtra("file_class",mFolder);
        this.setResult(RESULT_OK,intent);
        finish();
    }

    private void init_file(){
        for(int i = 0;i<6;i++){
            mData.add("文件夹"+String.valueOf(i));
        }
    }

    @Override
    public void get_usermsg_result(JSONObject jsonObject) {

    }

    @Override
    public void get_folder_result(JSONArray jsonArray) {
        Message message = new Message();
        message.obj = jsonArray;
        message.what = 1;
        handler.sendMessage(message);
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

    @Override
    public void delete_file_result(String code) {

    }

    @Override
    public void restore_file_result(String code) {

    }

    @Override
    public void cpl_delete_file_result(String code) {

    }

}