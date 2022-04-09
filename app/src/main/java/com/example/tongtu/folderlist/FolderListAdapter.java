package com.example.tongtu.folderlist;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tongtu.FileDownload;
import com.example.tongtu.FolderActivity;
import com.example.tongtu.R;
import com.example.tongtu.filepost.FileAdapter;
import com.example.tongtu.filepost.FilePost;

import org.jetbrains.annotations.NotNull;

import java.util.List;

//选择文件分组
public class FolderListAdapter extends RecyclerView.Adapter<FolderListAdapter.ViewHolder> {
    private List<FolderList> folder_list;
    private Context context;

    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView fileImage;
        TextView fileName;
        TextView fileDeviceNumber;
        TextView fileTime;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            fileImage = (ImageView)itemView.findViewById(R.id.file_image);
            fileName = (TextView)itemView.findViewById(R.id.file_name);
            fileName.getPaint().setFakeBoldText(true);
            fileDeviceNumber = (TextView)itemView.findViewById(R.id.file_device_number);
            fileTime = (TextView)itemView.findViewById(R.id.file_time);
        }
    }

    public FolderListAdapter(List<FolderList>file_post_list, Context f){
        this.folder_list = file_post_list;
        this.context = f;
    }


    @NonNull
    @NotNull
    @Override
    public FolderListAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.file_post,parent,false);
        final FolderListAdapter.ViewHolder holder = new FolderListAdapter.ViewHolder(view);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(holder.itemView.getContext(),"点击了"+folder_list.get(holder.getAdapterPosition()).getName(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, FileDownload.class);
                intent.putExtra("file_name",folder_list.get(holder.getAdapterPosition()).getName());
                context.startActivity(intent);
            }
        });


        return holder;

    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull FolderListAdapter.ViewHolder holder, int position) {
        FolderList  folderList = folder_list.get(position);
        holder.fileImage.setImageResource(folderList.getImageId());
        holder.fileName.setText(folderList.getName());
        holder.fileDeviceNumber.setText(folderList.getFile_device_number());
        holder.fileTime.setText(folderList.getFile_time());

    }

    @Override
    public int getItemCount() {
        return this.folder_list.size();
    }

}
