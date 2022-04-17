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
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tongtu.base.BaseActivity;
import com.example.tongtu.filelist.FileList;
import com.example.tongtu.filepost.FileAdapter;
import com.example.tongtu.filepost.FilePost;
import com.example.tongtu.filerecycle.FileRecycle;
import com.example.tongtu.filerecycle.FileRecycleAdapter;
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
    private final int DELETE_FILE_SUCCESS = 4;

    private  int DeleteFilePos = 0;

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
                    case DELETE_FILE_SUCCESS:
                        folder_list.remove(DeleteFilePos);
                        Toast.makeText(FolderActivity.this, "文件删除至回收站", Toast.LENGTH_SHORT).show();
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

        adapter.setOnItemClickListener(new FolderListAdapter.OnItemClickListener() {
            @Override
            public void onItemLongClick(View view, final int pos) {
                PopupMenu popupMenu = new PopupMenu(view.getContext(),view);
                popupMenu.getMenuInflater().inflate(R.menu.file_item_menu,popupMenu.getMenu());

                //弹出式菜单的菜单项点击事件
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.restoreItem:

                                break;
                            case R.id.removeItem:
                                DeleteFilePos = pos;
                               //Toast.makeText(view.getContext(), "删除"+folder_list.get(pos).getFile_id(), Toast.LENGTH_SHORT).show();
                              //getPresenter().DeleteFile(folder_list.get(pos).getFile_id(),token);
                              //delete_file_result("0");
                        }
                        return false;
                    }
                });

                popupMenu.setGravity(Gravity.END);
                popupMenu.show();
            }
        });

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
        folder_list.add(new FolderList("11","test","test","test",R.drawable.document_type_new,"test"));
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

    @Override
    public void delete_file_result(String code) {
        Message message = new Message();
        message.what = DELETE_FILE_SUCCESS;
        handler.sendMessage(message);
    }

    @Override
    public void restore_file_result(String code) {

    }

    @Override
    public void cpl_delete_file_result(String code) {

    }


    public void back_home(View view) {
        finish();
    }
}