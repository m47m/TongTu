package com.example.tongtu.ui.dashboard;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tongtu.MainActivity;
import com.example.tongtu.R;
import com.example.tongtu.base.BaseFragment;
import com.example.tongtu.base.FragmentBase;
import com.example.tongtu.filelist.FileList;
import com.example.tongtu.filepost.FileAdapter;
import com.example.tongtu.filepost.FilePost;
import com.example.tongtu.filerecycle.FileRecycle;
import com.example.tongtu.filerecycle.FileRecycleAdapter;
import com.example.tongtu.folderlist.FolderList;
import com.example.tongtu.mvp.PersonMsgPre;
import com.example.tongtu.mvp.PersonMsgView;
import com.example.tongtu.utils.EndlessRecyclerOnScrollListener;
import com.example.tongtu.utils.LoadMoreAdapter;
import com.example.tongtu.utils.MyItemTouchHelperCallback;

import org.json.JSONArray;
import org.json.JSONObject;

import java.nio.file.attribute.UserDefinedFileAttributeView;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;


//回收站界面
public class DashboardFragment extends FragmentBase<PersonMsgView, PersonMsgPre> implements PersonMsgView {

    private List<FileRecycle> file_recycle_list = new ArrayList<>();
    private SharedPreferences pref;
    private LoadMoreAdapter loadMoreAdapter;
    private String token;
    private int LoadPage;
    private boolean EndofPage = false;

    private final int LOADSTART = 1;
    private final int LOADMORE = 2;
    private final int LOADEND = 3;


    @SuppressLint("HandlerLeak")
    Handler handler;
    {
        handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);

                switch (msg.what){
                    case LOADSTART:
                        file_recycle_list.clear();
                        List<FileRecycle> temp = (List<FileRecycle>)msg.obj;
                        for (int i =0;i<temp.size();i++){
                            file_recycle_list.add(temp.get(i));
                        }
                        break;
                    case LOADMORE:
                        List<FileRecycle> temp2 = (List<FileRecycle>)msg.obj;
                        for (int i =0;i<temp2.size();i++){
                            file_recycle_list.add(temp2.get(i));
                        }
                        loadMoreAdapter.notifyDataSetChanged();
                        break;
                    case LOADEND:
                        EndofPage = true;
                        Log.d("testDash","loadend");
                        loadMoreAdapter.setLoadState(loadMoreAdapter.LOADING_END);
                    default:
                        break;
                }
                loadMoreAdapter.notifyDataSetChanged();
            }
        };
    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_d;
    }

    @Override
    protected void initView(View view) {
        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.recycler_view);
        // recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        FileRecycleAdapter adapter = new FileRecycleAdapter(file_recycle_list);
//        recyclerView.setAdapter(adapter);
//        recyclerView.scrollToPosition(adapter.getItemCount()-1);

        loadMoreAdapter = new LoadMoreAdapter(adapter);
        recyclerView.setAdapter(loadMoreAdapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new MyItemTouchHelperCallback(adapter));
        itemTouchHelper.attachToRecyclerView(recyclerView);

        recyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener() {
            @Override
            public void onLoadMore() {
                loadMoreAdapter.setLoadState(loadMoreAdapter.LOADING);
//                if(file_list_list.size() < 15){
                if(!EndofPage){
                    LoadPage++;
                    getPresenter().LoadMoreBinFile(LoadPage,token);
                    loadMoreAdapter.setLoadState(loadMoreAdapter.LOADING_COMPLETE);
                    Log.d("testDash" ,"loadmore"+String.valueOf(LoadPage));
                }
                else{
                    loadMoreAdapter.setLoadState(loadMoreAdapter.LOADING_END);
                }
            }
        });


        pref = view.getContext().getSharedPreferences("login_message",MODE_PRIVATE);
        token = pref.getString("token","");
    }

    @Override
    protected void initData(Context context) {
        init_file();
    }

    @Override
    public PersonMsgPre create_presenter() {
        return new PersonMsgPre();
    }

    @Override
    public PersonMsgView create_view() {
        return this;
    }

    private void init_file(){
        if(getPresenter() != null){
            Log.d("testDash","init");
            getPresenter().get_BinFile(token);
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

    }

    @Override
    public void get_folder_file_result(List<FolderList> folderLists) {

    }

    @Override
    public void loadmore_folder_file_reuslt(String code, List<FileList> fileLists) {

    }

    @Override
    public void get_bin_file_result(List<FileRecycle> fileRecycleList) {
        Message message = new Message();
        message.what = LOADSTART;
        message.obj = file_recycle_list;
        handler.sendMessage(message);
    }

    @Override
    public void loadmore_bin_file_result(String code, List<FileRecycle> fileRecycleList) {
        Message message = new Message();
        if(code.equals("1")){
            message.what = LOADEND;
            handler.sendMessage(message);
        }else if(code.equals("0")){
            message.what = LOADMORE;
            message.obj = fileRecycleList;
            handler.sendMessage(message);
        }
    }
}