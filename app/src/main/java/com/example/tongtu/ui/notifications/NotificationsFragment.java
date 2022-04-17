package com.example.tongtu.ui.notifications;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tongtu.FolderActivity;
import com.example.tongtu.R;
//      import com.example.tongtu.databinding.FragmentNBinding;
import com.example.tongtu.base.FragmentBase;
import com.example.tongtu.filelist.FileList;
import com.example.tongtu.filelist.FileListAdapter;
import com.example.tongtu.filerecycle.FileRecycle;
import com.example.tongtu.filerecycle.FileRecycleAdapter;
import com.example.tongtu.utils.LoadMoreAdapter;
import com.example.tongtu.folderlist.FolderList;
import com.example.tongtu.mvp.PersonMsgPre;
import com.example.tongtu.mvp.PersonMsgView;
import com.example.tongtu.utils.EndlessRecyclerOnScrollListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

//文件传输界面
public class NotificationsFragment extends FragmentBase<PersonMsgView, PersonMsgPre>implements PersonMsgView {

    private List<FileList> file_list_list = new ArrayList<>();
    private FileListAdapter adapter;
    private LoadMoreAdapter loadMoreAdapter;
    private SharedPreferences pref;
    private String token;
    private int LoadPage;
    private boolean EndofPage = false;
    
    private final int LOADSTART = 1;
    private final int LOADMORE = 2;
    private final int LOADEND = 3;
    private final int DELETE_FILE_SUCCESS = 4;
    private int DeleteFilePos = 0;

    @SuppressLint("HandlerLeak")
    Handler handler;
    {
        handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);

                switch (msg.what){
                    case LOADSTART:
                       file_list_list.clear();
                       List<FileList> temp = (List<FileList>)msg.obj;
                       for (int i =0;i<temp.size();i++){
                           file_list_list.add(temp.get(i));
                       }
                       break;
                    case LOADMORE:
                        List<FileList> temp2 = (List<FileList>)msg.obj;
                        for (int i =0;i<temp2.size();i++){
                            file_list_list.add(temp2.get(i));
                        }
                        loadMoreAdapter.notifyDataSetChanged();
                        break;
                    case LOADEND:
                        EndofPage = true;
                        loadMoreAdapter.setLoadState(loadMoreAdapter.LOADING_END);
                    case DELETE_FILE_SUCCESS:
                        file_list_list.remove(DeleteFilePos);
                        Toast.makeText(getContext(), "文件删除至回收站", Toast.LENGTH_SHORT).show();
                    default:
                        break;
                }
                loadMoreAdapter.notifyDataSetChanged();
            }
        };
    }


    @Override
    protected int initLayout() {
        return R.layout.fragment_n;
    }

    @Override
    protected void initView(View view) {
        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.recycler_view);
        // recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new FileListAdapter(file_list_list,this.getContext());
//        recyclerView.setAdapter(adapter);

        loadMoreAdapter = new LoadMoreAdapter(adapter);
        recyclerView.setAdapter(loadMoreAdapter);

        recyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener() {
            @Override
            public void onLoadMore() {
                loadMoreAdapter.setLoadState(loadMoreAdapter.LOADING);
//                if(file_list_list.size() < 15){
                if(!EndofPage){
                    LoadPage++;
                    getPresenter().LoadMoreFile(LoadPage,token);
                    loadMoreAdapter.setLoadState(loadMoreAdapter.LOADING_COMPLETE);
                    Log.d("testNoti" ,"loadmore"+String.valueOf(LoadPage));
                }
                else{
                    loadMoreAdapter.setLoadState(loadMoreAdapter.LOADING_END);
                }
            }
        });

        adapter.setOnItemClickListener(new FileListAdapter.OnItemClickListener() {
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
                                Toast.makeText(view.getContext(), "还原", Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.removeItem:
                                //Toast.makeText(view.getContext(), "删除", Toast.LENGTH_SHORT).show();
                                DeleteFilePos = pos;
                                delete_file_result("0");
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });

        pref = view.getContext().getSharedPreferences("login_message",MODE_PRIVATE);
        token = pref.getString("token","");
    }

    @Override
    protected void initData(Context context) {
        LoadPage = 0;
        initFileList();
    }

    @Override
    public PersonMsgPre create_presenter() {
        return new PersonMsgPre();
    }

    @Override
    public PersonMsgView create_view() {
        return this;
    }

    public void initFileList(){
        if(getPresenter() != null){
            getPresenter().get_File(token);
        }
    }


    @Override
    public void get_usermsg_result(JSONObject jsonObject) {

    }

    @Override
    public void get_folder_result(JSONArray jsonArray) {

    }

    @Override
    public void get_file_result(List<FileList> fileLists) {
        Message message = new Message();
        message.what = LOADSTART;
        Log.d("filelist",fileLists.get(1).getFile_name());
        message.obj = fileLists;
        handler.sendMessage(message);
    }

    @Override
    public void get_folder_file_result(List<FolderList> folderLists) {

    }

    @Override
    public void loadmore_folder_file_reuslt(String code,List<FileList> fileLists) {
        Message message = new Message();
        if(code.equals("1")){
            message.what = LOADEND;
            handler.sendMessage(message);
        }else if(code.equals("0")){
            message.what = LOADMORE;
            message.obj = fileLists;
            handler.sendMessage(message);
        }
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
}