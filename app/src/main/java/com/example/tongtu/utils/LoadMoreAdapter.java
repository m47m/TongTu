package com.example.tongtu.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tongtu.R;
import com.example.tongtu.base.BaseActivity;
import com.example.tongtu.filepost.FilePost;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class LoadMoreAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<FilePost> file_post_list;
    private Context context;
    private RecyclerView.Adapter adapter;

    // 普通布局
    private final int TYPE_ITEM = 1;
    // 脚布局
    private final int TYPE_FOOTER = 2;
    // 当前加载状态，默认为加载完成
    private int loadState = 2;
    // 正在加载
    public final int LOADING = 1;
    // 加载完成
    public final int LOADING_COMPLETE = 2;
    // 加载到底
    public final int LOADING_END = 3;


    public LoadMoreAdapter(RecyclerView.Adapter adapter){
        this.adapter = adapter;
    }

    static class FileViewHolder extends RecyclerView.ViewHolder{
        ImageView fileImage;
        TextView fileName;
        TextView fileDeviceNumber;
        TextView fileTime;
        public FileViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            fileImage = (ImageView)itemView.findViewById(R.id.file_image);
            fileName = (TextView)itemView.findViewById(R.id.file_name);
            fileName.getPaint().setFakeBoldText(true);
            fileDeviceNumber = (TextView)itemView.findViewById(R.id.file_device_number);
            fileTime = (TextView)itemView.findViewById(R.id.file_time);
        }
    }

    static class FootViewHolder extends RecyclerView.ViewHolder {

        TextView text_loading;
        TextView text_end;

        public FootViewHolder(View itemView) {
            super(itemView);
//            pbLoading = (ProgressBar) itemView.findViewById(R.id.pb_loading);
//            tvLoading = (TextView) itemView.findViewById(R.id.tv_loading);
//            llEnd = (LinearLayout) itemView.findViewById(R.id.ll_end);

            text_loading = (TextView)itemView.findViewById(R.id.text_loading);
            text_end = (TextView)itemView.findViewById(R.id.text_end);
        }
    }
    public LoadMoreAdapter(List<FilePost>file_post_list, Context f){
        this.file_post_list = file_post_list;
        this.context = f;
    }

    @Override
    public int getItemViewType(int position) {
        if(position+1 == getItemCount()){
            //尾布局
            return TYPE_FOOTER;
        }else {
            //普通布局
            return TYPE_ITEM;
        }
    }

    @NonNull
    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        if(viewType == TYPE_ITEM){
           return adapter.onCreateViewHolder(parent,viewType);
        }else if(viewType == TYPE_FOOTER){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_refresh_footer,parent,false);
            return new FootViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof FootViewHolder){
            //脚布局
            FootViewHolder footViewHolder = (FootViewHolder)holder;
            switch (loadState){
                case LOADING:
                    footViewHolder.text_loading.setVisibility(View.VISIBLE);
                    footViewHolder.text_end.setVisibility(View.GONE);
                    break;
                case LOADING_COMPLETE:
                    footViewHolder.text_loading.setVisibility(View.INVISIBLE);
                    footViewHolder.text_end.setVisibility(View.GONE);
                    break;
                case LOADING_END:
                    footViewHolder.text_loading.setVisibility(View.GONE);
                    footViewHolder.text_end.setVisibility(View.VISIBLE);
                    break;
                default:
                    break;
            }
        }else {
            adapter.onBindViewHolder(holder,position);
        }
    }


    @Override
    public int getItemCount() {
        return adapter.getItemCount()+1;
    }


    public void setLoadState(int loadState){
        this.loadState = loadState;
        notifyDataSetChanged();
    }
}
