package com.example.tongtu.ui.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;


import androidx.annotation.NonNull;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tongtu.R;
import com.example.tongtu.base.FragmentBase;
import com.example.tongtu.filelist.FileList;
import com.example.tongtu.filerecycle.FileRecycle;
import com.example.tongtu.utils.LoadMoreAdapter;
import com.example.tongtu.filepost.FileAdapter;
import com.example.tongtu.filepost.FilePost;
import com.example.tongtu.folderlist.FolderList;
import com.example.tongtu.mvp.PersonMsgPre;
import com.example.tongtu.mvp.PersonMsgView;
import com.example.tongtu.utils.EndlessRecyclerOnScrollListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

//文件分组界面
public class HomeFragment extends FragmentBase<PersonMsgView, PersonMsgPre>implements PersonMsgView {

    private HomeViewModel homeViewModel;
    //    private FragmentHBinding binding;
    private List<FilePost> file_post_list = new ArrayList<>();
    private SharedPreferences pref;
    private String token;
    private FileAdapter adapter;
    private LoadMoreAdapter loadMoreAdapter;

    @SuppressLint("HandlerLeak")
    Handler handler;

    {
        handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case 1:
                        file_post_list.clear();
                        JSONArray jsonArray = (JSONArray)msg.obj;
                        try {
                            for(int i = 0;i<jsonArray.length();i++){
                                file_post_list.add(
                                        new FilePost(jsonArray.getString(i),"有几个文件","20:12",R.drawable.folder)
                                );
                            }
                        }catch (JSONException e){
                            e.printStackTrace();
                        }

                        adapter.notifyDataSetChanged();
                        break;
                    default:
                        break;
                }
            }
        };
    }


    @Override
    protected int initLayout() {
        return R.layout.fragment_h;
    }

    @Override
    protected void initView(View view) {
        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.recycler_view);
       // recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new FileAdapter(file_post_list,this.context);
        loadMoreAdapter = new LoadMoreAdapter(adapter);
        recyclerView.setAdapter(adapter);

//        fFileAdapter = new FFileAdapter(file_post_list,this.context);
//        recyclerView.setAdapter(fFileAdapter);
//
//        recyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener() {
//            @Override
//            public void onLoadMore() {
//                loadMoreAdapter.setLoadState(loadMoreAdapter.LOADING);
//
//                if(file_post_list.size() < 50){
//                    Message message = new Message();
//                    message.what = 2;
//                    handler.sendMessage(message);
//                    loadMoreAdapter.setLoadState(loadMoreAdapter.LOADING_COMPLETE);
//
//
//                }else{
//                    loadMoreAdapter.setLoadState(loadMoreAdapter.LOADING_END);
//                }
//            }
//        });

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
      getPresenter().get_Folder(token);
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



//


//    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View root = inflater.inflate(R.layout.fragment_h,container,false);
//
//        init_file();
//
//        RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.recycler_view);
//       // recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));
//        LinearLayoutManager layoutManager = new LinearLayoutManager(root.getContext());
//        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        recyclerView.setLayoutManager(layoutManager);
//        FileAdapter adapter = new FileAdapter(file_post_list);
//        recyclerView.setAdapter(adapter);
//
//        return root;
//    }

//
//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
////        binding = null;
//    }
}